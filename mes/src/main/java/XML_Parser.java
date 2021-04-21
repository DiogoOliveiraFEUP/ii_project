import jdk.swing.interop.SwingInterOpUtils;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class XML_Parser {

    Scheduler scheduler = new Scheduler();
    UDP_Sender udp = new UDP_Sender();

    public void parse(XML_Request request, List<Transformation_Order> transfOrders, List<Unloading_Order> unldOrders) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(
                    request.getRequest().getBytes(StandardCharsets.UTF_8));
            Document doc = builder.parse(input);

            Element root = doc.getDocumentElement();
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + root.getNodeName());
            System.out.println("----------------------------");

            if(root.getNodeName().equals("ORDERS")){

                NodeList nList;
                nList = doc.getElementsByTagName("Order");

                if(nList.getLength()>0){
                    for (int temp = 0; temp < nList.getLength(); temp++) {
                        Node nNode = nList.item(temp);
                        System.out.println("Current Element : " + nNode.getNodeName());

                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element e1 = (Element) nNode;
                            int number = Integer.parseInt(e1.getAttribute("Number"));
                            System.out.println("Order no: " + number);

                            Element e2;
                            if(e1.getElementsByTagName("Transform").getLength()>0){
                                e2 = (Element) e1.getElementsByTagName("Transform").item(0);
                                String from = e2.getAttribute("From");
                                String to = e2.getAttribute("To");
                                int quantity = Integer.parseInt(e2.getAttribute("Quantity"));
                                int time = Integer.parseInt(e2.getAttribute("Time"));
                                int maxDelay = Integer.parseInt(e2.getAttribute("MaxDelay"));
                                int penalty = Integer.parseInt(e2.getAttribute("Penalty"));
                                System.out.println("Transform _ From:" + from + " To:" + to + " Quantity:" + quantity
                                        + " Time:" + time + " MaxDelay:" + maxDelay + " Penalty:" + penalty);

                                /* Do Something - Order Transform */
                                for(int i = 0; i < quantity; i++){
                                    transfOrders.add(new Transformation_Order(number,i+1, from,to, time,maxDelay,penalty));
                                }

                                scheduler.schedule(transfOrders,unldOrders);
                                updateDB(transfOrders);
                            }
                            else if(e1.getElementsByTagName("Unload").getLength()>0){
                                e2 = (Element) e1.getElementsByTagName("Unload").item(0);
                                String type = e2.getAttribute("Type");
                                String dest = e2.getAttribute("Destination");
                                int quantity = Integer.parseInt(e2.getAttribute("Quantity"));
                                System.out.println("Unload _" + " Type:" + type + " Destination:" + dest + " Quantity:" + quantity);

                                /* Do Something - Order Unload */
                                for(int i = 0; i < quantity; i++){
                                    unldOrders.add(new Unloading_Order(type,dest,number));
                                }
                                scheduler.schedule(transfOrders,unldOrders);
                            }
                        }
                    }
                }

                nList = doc.getElementsByTagName("Request_Stores");

                if(nList.getLength()>0){
                    for(int temp = 0; temp < nList.getLength(); temp++){
                        Node nNode = nList.item(temp);
                        System.out.println("Current Element : " + nNode.getNodeName());

                        /* Do Something - Request_Stores */
                        udp.send(getStoresXML(),request.getAddress(),request.getPort());
                        // System.out.println("Sent " + getStoresXML() + " to IP " + request.getAddress() + " to Port " + request.getPort());

                    }
                }

                nList = doc.getElementsByTagName("Request_Orders");

                if(nList.getLength()>0){
                    for(int temp = 0; temp < nList.getLength(); temp++){
                        Node nNode = nList.item(temp);
                        System.out.println("Current Element : " + nNode.getNodeName());

                        /* Do Something - Request_Orders */
                        udp.send(getOrdersXML(),request.getAddress(),request.getPort());
                        //System.out.println("Sent " + getOrdersXML() + " to IP " + request.getAddress() + " to Port " + request.getPort());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getOrdersXML() {

        /* Get information from MES or DB?? */
        String res = (new Database_Connection()).query("SELECT * FROM transforders;");

        return "<Order_Schedule>\n" +
                "<Order Number=\"nnn\">\n" +
                "<Transform From=\"Px\" To=\"Py\" Quantity=\"XX\" Quantity1=\"X1\" Quantity2=\"X2\"\n" +
                "Quantity3=\"X3\" Time=\"TT\" Time1=\"T1\" MaxDelay=\"DD\" Penalty=\"PP\" Start=\"ST\"\n" +
                "End=\"ET\" PenaltyIncurred=\"PI\"/>\n" +
                "</Order>\n" +
                "</Order_Schedule>";
    }

    private String getStoresXML() {

        /* Get information from MES or DB?? */
        /* DB is always up to date with stocks info?? */

        String res = (new Database_Connection()).query("SELECT * FROM stocks;");
        //System.out.println(res);

        StringBuilder sb = new StringBuilder();
        sb.append("<Current_Stores>\n");

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(
                    res.getBytes(StandardCharsets.UTF_8));
            Document doc = builder.parse(input);

            Element root = doc.getDocumentElement();
            doc.getDocumentElement().normalize();

            NodeList nList;
            nList = doc.getElementsByTagName("Row");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                Element e1 = (Element) nNode;

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    sb.append("<WorkPiece type=\"");
                    sb.append(e1.getElementsByTagName("BlockType").item(0).getTextContent());
                    sb.append("\" quantity=\"");
                    sb.append(e1.getElementsByTagName("Quantity").item(0).getTextContent());
                    sb.append("\"/>\n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        sb.append("</Current_Stores>");
        //System.out.println(sb.toString());

        return sb.toString();
    }

    private void updateDB(List<Transformation_Order> transfOrders){

        StringBuilder sb = new StringBuilder();

        List<Integer> ids = Transformation_Order.getMainIDs(transfOrders);

        for(Integer id : ids){
            List<Transformation_Order> orders = Transformation_Order.getOrdersByMainID(transfOrders,id);
            int total = 0;
            int finished = 0;
            int running = 0;
            long startTime = Long.MAX_VALUE;
            long endTime = Long.MIN_VALUE;

            for(Transformation_Order order : orders){
                total++;
                if(order.getStatus() == Order.Status.COMPLETED){
                    finished++;
                }
                else if(order.getStatus() == Order.Status.RUNNING){
                    running++;
                }
                if(order.getStartTime() < startTime){
                    startTime=order.getStartTime();
                }
                if(order.getEndTime() > endTime){
                    endTime= order.getEndTime();
                }
            }

            sb.append("REPLACE INTO transforders VALUES ("
                    + id + ","
                    + "'" + orders.get(0).getInitBlockType() + "',"
                    + "'" + orders.get(0).getFinalBlockType() + "',"
                    + total + ","
                    + finished + ","
                    + running + ","
                    + orders.get(0).getInputTime() + ","
                    + orders.get(0).getRealInputTime() + ","
                    + orders.get(0).getMaxDelay() + ","
                    + orders.get(0).getPenalty() + ","
                    + startTime + ","
                    + endTime + ");\n");
        }

        /*
        Transformation Order DB
            + Main_ID: int
            + BlockType_Initial: int
            + BlockType_Final: int
            + Total_Quantity: int
            + Finished_Quantity: int
            + Running_Quantity: int
            + Send_Time: int
            + Arrival_Time: int
            + MaxDelay: int
            + Penalty: int
            + Start_Time: int
            + End_Time: int
        */

        String res = (new Database_Connection()).query(sb.toString());
        //System.out.println(res);
    }
}