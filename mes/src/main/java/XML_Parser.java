import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class XML_Parser {

    Scheduler scheduler = new Scheduler();

    public void parse(String request, List<Order> orders) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(
                    request.getBytes(StandardCharsets.UTF_8));
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
                                int from = Integer.parseInt(e2.getAttribute("From"));
                                int to = Integer.parseInt(e2.getAttribute("To"));
                                int quantity = Integer.parseInt(e2.getAttribute("Quantity"));
                                int time = Integer.parseInt(e2.getAttribute("Time"));
                                int maxDelay = Integer.parseInt(e2.getAttribute("MaxDelay"));
                                int penalty = Integer.parseInt(e2.getAttribute("Penalty"));
                                System.out.println("Transform _ From:" + from + " To:" + to + " Quantity:" + quantity
                                        + " Time:" + time + " MaxDelay:" + maxDelay + " Penalty:" + penalty);

                                /* Do Something - Order Transform */
                                for(int i = 0; i < quantity; i++){
                                    orders.add(new Transformation_Order(from,to,number,time,maxDelay,penalty));
                                }
                            }
                            else if(e1.getElementsByTagName("Unload").getLength()>0){
                                e2 = (Element) e1.getElementsByTagName("Unload").item(0);
                                int type = Integer.parseInt(e2.getAttribute("Type"));
                                int dest = Integer.parseInt(e2.getAttribute("Destination"));
                                int quantity = Integer.parseInt(e2.getAttribute("Quantity"));
                                System.out.println("Unload _" + " Type:" + type + " Destination:" + dest + " Quantity:" + quantity);

                                /* Do Something - Order Unload */
                                for(int i = 0; i < quantity; i++){
                                    // TIME, DELAY, PENALTY ???????????????????
                                    orders.add(new Unloading_Order(type,number,0,1000,0));
                                }
                            }
                        }
                    }
                    scheduler.schedule(orders);
                    updateDB(orders);
                }

                nList = doc.getElementsByTagName("Request_Stores");

                if(nList.getLength()>0){
                    for(int temp = 0; temp < nList.getLength(); temp++){
                        Node nNode = nList.item(temp);
                        System.out.println("Current Element : " + nNode.getNodeName());

                        /* Do Something - Request_Stores */
                    }
                }

                nList = doc.getElementsByTagName("Request_Orders");

                if(nList.getLength()>0){
                    for(int temp = 0; temp < nList.getLength(); temp++){
                        Node nNode = nList.item(temp);
                        System.out.println("Current Element : " + nNode.getNodeName());

                        /* Do Something - Request_Orders */
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateDB(List<Order> orders){
        /* Connect and Update DB */

    }


}
