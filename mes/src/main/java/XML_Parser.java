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
                                    transfOrders.add(new Transformation_Order(from,to,number,time,maxDelay,penalty));
                                }
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
                            }
                        }
                    }
                    scheduler.schedule(transfOrders,unldOrders);
                    updateDB(transfOrders,unldOrders);
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

        return "<Order_Schedule>\n" +
                "<Order Number=\"nnn\">\n" +
                "<Transform From=\"Px\" To=\"Py\" Quantity=\"XX\" Quantity1=\"X1\" Quantity2=\"X2\"\n" +
                "Quantity3=\"X3\" Time=\"TT\" Time1=\"T1\" MaxDelay=\"DD\" Penalty=\"PP\" Start=\"ST\"\n" +
                "End=\"ET\" PenaltyIncurred=\"PI\"/>\n" +
                "</Order>\n" +
                "</Order_Schedule>";
    }

    private String getStoresXML() {

        return "<Current_Stores>\n" +
                "<WorkPiece type=\"Px\" quantity=\"XX\"/>\n" +
                "<WorkPiece type=\"Px\" quantity=\"XX\"/>\n" +
                "</Current_Stores>";
    }

    private void updateDB(List<Transformation_Order> transfOrders, List<Unloading_Order> unldOrders){
        /* Connect and Update DB */

        /*
        (new Database_Connection()).query(
                "INSERT INTO transforders (MainID,FinalType,InitType,TotalQuantity) " +
                "VALUES (124,'P1','P5',5)");
        */

        String res = (new Database_Connection()).query("Select * from transforders");
        //System.out.println(res);
    }


}
