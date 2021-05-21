import Order.Order;
import Order.Transformation_Order;
import Order.Unloading_Order;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
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
                                PLC_Manager.getInstance().evalWo1();
                                PLC_Manager.getInstance().evalWo2();
                                Database_Connection.updateDB(transfOrders);
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
                                PLC_Manager.getInstance().evalWo1();
                                PLC_Manager.getInstance().evalWo2();
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
        //System.out.println(res);

        StringBuilder sb = new StringBuilder();
        sb.append("<Order_Schedule>\n");

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
                    sb.append("<Order Number=\"");
                    sb.append(e1.getElementsByTagName("MainID").item(0).getTextContent());
                    sb.append("\">\n<Transform From=\"");
                    sb.append(e1.getElementsByTagName("InitType").item(0).getTextContent());
                    sb.append("\" To=\"");
                    sb.append(e1.getElementsByTagName("FinalType").item(0).getTextContent());
                    sb.append("\" Quantity=\"");
                    int x = Integer.parseInt(e1.getElementsByTagName("TotalQuantity").item(0).getTextContent());
                    sb.append(x);
                    sb.append("\" Quantity1=\"");
                    int x1 = Integer.parseInt(e1.getElementsByTagName("FinQuantity").item(0).getTextContent());
                    sb.append(x1);
                    sb.append("\" Quantity2=\"");
                    int x2 = Integer.parseInt(e1.getElementsByTagName("RunQuantity").item(0).getTextContent());
                    sb.append(x2);
                    sb.append("\" Quantity3=\"");
                    sb.append(x-x1-x2);
                    sb.append("\" Time=\"");
                    long InputTime = Long.parseLong(e1.getElementsByTagName("InputTime").item(0).getTextContent());
                    sb.append(InputTime);
                    sb.append("\" Time1=\"");
                    sb.append(e1.getElementsByTagName("RealInputTime").item(0).getTextContent());
                    sb.append("\" MaxDelay=\"");
                    int MaxDelay = Integer.parseInt(e1.getElementsByTagName("MaxDelay").item(0).getTextContent());
                    sb.append(MaxDelay);
                    sb.append("\" Penalty=\"");
                    int penalty = Integer.parseInt(e1.getElementsByTagName("Penalty").item(0).getTextContent());
                    sb.append(penalty);
                    sb.append("\" Start=\"");
                    sb.append(e1.getElementsByTagName("StartTime").item(0).getTextContent());
                    sb.append("\" End=\"");
                    long EndTime = Long.parseLong(e1.getElementsByTagName("EndTime").item(0).getTextContent());
                    sb.append(EndTime);
                    sb.append("\" PenaltyIncurred=\"");
                    long delta = (EndTime-InputTime-MaxDelay);
                    if(delta>0) delta = (delta/50 + 1);
                    else delta = 0;
                    sb.append(delta*penalty);
                    sb.append("\"/>\n</Order>\n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        sb.append("</Order_Schedule>\n");
        System.out.println(sb.toString());

        return sb.toString();

       /* return "<Order_Schedule>\n" +
                "<Order Number=\"nnn\">\n" +
                "<Transform From=\"Px\" To=\"Py\" Quantity=\"XX\" Quantity1=\"X1\" Quantity2=\"X2\"\n" +
                "Quantity3=\"X3\" Time=\"TT\" Time1=\"T1\" MaxDelay=\"DD\" Penalty=\"PP\" Start=\"ST\"\n" +
                "End=\"ET\" PenaltyIncurred=\"PI\"/>\n" +
                "</Order>\n" +
                "</Order_Schedule>";
*/
        /*
            nnn – número de ordem
            Px – tipo de peça de origem
            Py – tipo de peça final
            XX – quantidade total a produzir
            X1 – quantidade já produzidas
            X2 – quantidade em produção
            X3 – quantidade por produzir
            TT – instante no qual a ordem é enviada ao MES (em segundos)
            T1 – instante no qual a ordem efectivamente chegou ao MES (em segundos)
            DD – atraso máximo (em segundos) para terminar de executar esta ordem
            (a contar a partir do instante de envio da ordem)
            PP – penalidade (numero de 0 a 1000) a incorrer por cada dia de atraso na ‘entrega
            da encomenda.
            ST – tempo (em segundos) em que a ordem iniciou a transformação (caso já tenha
            iniciado) ou tempo para o qual está previsto o seu inicio (caso ainda não tenha
            iniciado)
            ET – tempo (em segundos) em que a ordem terminou a transformação (caso já tenha
            terminado) ou tempo para o qual está previsto o seu fim (caso ainda não tenha
            terminado)
            PI – penalidade incurrida (caso já tenha terminado) ou
        */
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
        //System.out.println(sb.toString());

        return sb.toString();
    }
}