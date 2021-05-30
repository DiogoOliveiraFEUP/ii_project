import Order.Order;
import Order.Transformation_Order;
import Order.Unloading_Order;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.crypto.Data;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.ArrayList;
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

            if(root.getNodeName().equals("ORDERS")){

                NodeList nList;
                nList = doc.getElementsByTagName("Order");

                if(nList.getLength()>0){
                    for (int temp = 0; temp < nList.getLength(); temp++) {
                        Node nNode = nList.item(temp);
                        //System.out.println("Current Element : " + nNode.getNodeName());

                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element e1 = (Element) nNode;
                            int number = Integer.parseInt(e1.getAttribute("Number"));
                            //System.out.println("Order no: " + number);

                            Element e2;
                            if(e1.getElementsByTagName("Transform").getLength()>0){
                                e2 = (Element) e1.getElementsByTagName("Transform").item(0);
                                String from = e2.getAttribute("From");
                                String to = e2.getAttribute("To");
                                int quantity = Integer.parseInt(e2.getAttribute("Quantity"));
                                int time = Integer.parseInt(e2.getAttribute("Time"));
                                int maxDelay = Integer.parseInt(e2.getAttribute("MaxDelay"));
                                int penalty = Integer.parseInt(e2.getAttribute("Penalty"));

                                /* Do Something - Order Transform */
                                for(int i = 1; i <= quantity; i++){
                                    addOrder(transfOrders,number,i,from,to,time,maxDelay,penalty);
                                }

                                scheduler.schedule(transfOrders);
                                PLC_Manager.getInstance().evalWo1();
                                PLC_Manager.getInstance().evalWo2();
                                Database_Connection.updateTOrders(transfOrders);
                            }
                            else if(e1.getElementsByTagName("Unload").getLength()>0){
                                e2 = (Element) e1.getElementsByTagName("Unload").item(0);
                                String type = e2.getAttribute("Type");
                                String dest = e2.getAttribute("Destination");
                                int quantity = Integer.parseInt(e2.getAttribute("Quantity"));
                                System.out.println("Unload _" + " Type:" + type + " Destination:" + dest + " Quantity:" + quantity);

                                /* Do Something - Order Unload */
                                for(int i = 1; i <= quantity; i++){
                                    if(i == 1)
                                        unldOrders.add(new Unloading_Order(type,dest,number,i,true));
                                    else
                                        unldOrders.add(new Unloading_Order(type,dest,number,i,false));
                                }
                                PLC_Manager.getInstance().evalWo1();
                                Database_Connection.updateUOrders(unldOrders);
                            }
                        }
                    }
                }

                nList = doc.getElementsByTagName("Request_Stores");

                if(nList.getLength()>0){
                    for(int temp = 0; temp < nList.getLength(); temp++){
                        Node nNode = nList.item(temp);

                        udp.send(getStoresXML(),request.getAddress(),request.getPort());
                        System.out.println("Sent " + getStoresXML() + " to IP " + request.getAddress() + " to Port " + request.getPort());

                    }
                }

                nList = doc.getElementsByTagName("Request_Orders");

                if(nList.getLength()>0){
                    for(int temp = 0; temp < nList.getLength(); temp++){
                        Node nNode = nList.item(temp);
                        //System.out.println("Current Element : " + nNode.getNodeName());
                        /* Do Something - Request_Orders */
                        udp.send(getOrdersXML(),request.getAddress(),request.getPort());
                        System.out.println("Sent " + getOrdersXML() + " to IP " + request.getAddress() + " to Port " + request.getPort());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addOrder(List<Transformation_Order> transforders, int mainID, int ID, String from, String to, int time, int maxDelay, int penalty){

        if(from.equals("P1")){
            if(to.equals("P2") || to.equals("P3") || to.equals("P4") || to.equals("P5")){
                transforders.add(new Transformation_Order(mainID,ID,1,from,to,time,maxDelay,penalty));
            }
            else if(to.equals("P6") || to.equals("P7") || to.equals("P8") || to.equals("P9")) {
                transforders.add(new Transformation_Order(mainID, ID, 1, from, "P5", time, maxDelay, penalty));
                transforders.add(new Transformation_Order(mainID, ID, 2, "P5", to, time, maxDelay, penalty));
            }
        }
        if(from.equals("P2")){
            if(to.equals("P3") || to.equals("P4") || to.equals("P5") || to.equals("P6") || to.equals("P9")){
                transforders.add(new Transformation_Order(mainID,ID,1,from,to,time,maxDelay,penalty));
            }
            else if(to.equals("P7") || to.equals("P8")) {
                transforders.add(new Transformation_Order(mainID, ID, 1, from, "P6", time, maxDelay, penalty));
                transforders.add(new Transformation_Order(mainID, ID, 2, "P6", to, time, maxDelay, penalty));
            }
        }
        if(from.equals("P3")){
            if(to.equals("P4") || to.equals("P5") || to.equals("P6") || to.equals("P7") || to.equals("P8") || to.equals("P9")){
                transforders.add(new Transformation_Order(mainID,ID,1,from,to,time,maxDelay,penalty));
            }
        }
        if(from.equals("P4")){
            if(to.equals("P5") || to.equals("P6") || to.equals("P7") || to.equals("P8") || to.equals("P9")){
                transforders.add(new Transformation_Order(mainID,ID,1,from,to,time,maxDelay,penalty));
            }
        }
        if(from.equals("P5")){
            if(to.equals("P6") || to.equals("P7") || to.equals("P8") || to.equals("P9")){
                transforders.add(new Transformation_Order(mainID,ID,1,from,to,time,maxDelay,penalty));
            }
        }
        if(from.equals("P6")){
            if(to.equals("P7") || to.equals("P8")){
                transforders.add(new Transformation_Order(mainID,ID,1,from,to,time,maxDelay,penalty));
            }
        }
    }

    private String getOrdersXML() {

        List<Transformation_Order> transfOrders = new ArrayList<>();
        Database_Connection.getTOrdersAll(transfOrders);

        StringBuilder sb = new StringBuilder();
        sb.append("<Order_Schedule>\n");


        List<Integer> mainIDs = Transformation_Order.getMainIDs(transfOrders);

        for(Integer mainID : mainIDs){

            List<Transformation_Order> orders = Transformation_Order.getOrdersByMainID(transfOrders,mainID);
            int from = 10;
            int to = 0;
            int total = 0;
            int finished = 0;
            int running = 0;
            long startTime = Long.MAX_VALUE;
            long endTime = Long.MIN_VALUE;
            Order.Status status;

            List<Integer> IDs = Transformation_Order.getIDs(orders);

            for(Integer ID : IDs) {
                List<Transformation_Order> subOrders = Transformation_Order.getOrdersByID(orders, ID);

                total++;
                status = Order.Status.READY;

                for (Transformation_Order subOrder : subOrders) {
                    if (subOrder.getStatus() == Order.Status.RUNNING) {
                        status = Order.Status.RUNNING;
                    } else if (status != Order.Status.RUNNING && subOrder.getStatus() == Order.Status.COMPLETED) {
                        status = Order.Status.COMPLETED;
                    }
                    if (subOrder.getStartTime() < startTime) {
                        startTime = subOrder.getStartTime();
                    }
                    if (subOrder.getEndTime() > endTime) {
                        endTime = subOrder.getEndTime();

                    }
                    if(Integer.parseInt(subOrder.getInitBlockType().substring(1))<from) {
                        from = Integer.parseInt(subOrder.getInitBlockType().substring(1));
                    }
                    if(Integer.parseInt(subOrder.getFinalBlockType().substring(1))>to) {
                        to = Integer.parseInt(subOrder.getFinalBlockType().substring(1));
                    }
                }

                if(status == Order.Status.COMPLETED){
                    finished++;
                }
                else if(status == Order.Status.RUNNING){
                    running++;
                }
            }

            int penaltyIncurred = (((int) (endTime-orders.get(0).getRealInputTime()) / 50 + 1) * orders.get(0).getPenalty());

            sb.append("<Order Number=\"" + mainID + "\">\n");
            sb.append("<Transform From=\"P" + from + "\" To=\"P" + to + "\" ");
            sb.append("Quantity=\"" + total + "\" ");
            sb.append("Quantity1=\"" + finished + "\" ");
            sb.append("Quantity2=\"" + running + "\" ");
            sb.append("Quantity3=\"" + (total-finished-running) + "\" ");
            sb.append("Time=\"" + orders.get(0).getInputTime() + "\" ");
            sb.append("Time1=\"" + orders.get(0).getInputTime() + "\" ");
            sb.append("MaxDelay=\"" + orders.get(0).getMaxDelay() + "\" ");
            sb.append("Penalty=\"" + orders.get(0).getPenalty() + "\" ");
            sb.append("Start=\"" + (startTime-orders.get(0).getRealInputTime()) + "\" ");
            sb.append("End=\"" + (endTime-orders.get(0).getRealInputTime()) + "\" ");
            sb.append("PenaltyIncurred=\"" + penaltyIncurred + "\"/>\n");
            sb.append("</Order>\n");

        }

        sb.append("</Order_Schedule>\n");
        //System.out.println(sb.toString());
        return sb.toString();
    }

    private String getStoresXML() {

        String res = (new Database_Connection()).query("SELECT * FROM stocks;");

        StringBuilder sb = new StringBuilder();
        sb.append("<Current_Stores>\n");

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(
                    res.getBytes(StandardCharsets.UTF_8));
            Document doc = builder.parse(input);

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

        return sb.toString();
    }
}