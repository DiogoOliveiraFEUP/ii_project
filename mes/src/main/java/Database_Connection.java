import java.io.StringWriter;
import java.sql.*;
import java.util.List;

import Order.Order;
import Order.Transformation_Order;
import Order.Unloading_Order;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Database_Connection {

    String URL = "jdbc:mariadb://localhost/factory?allowMultiQueries=true";
    String user = "root";
    String password = "root";

    public String query(String query){

        String result = "";

        try {

            Connection conn = DriverManager.getConnection(URL,user,password);
            Statement stmt = conn.createStatement();

            stmt.executeQuery("USE factory");
            ResultSet rs = stmt.executeQuery(query);

            result = ResultSet2String(rs);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public ResultSet query2(String query){

        ResultSet rs = null;

        try {

            Connection conn = DriverManager.getConnection(URL,user,password);
            Statement stmt = conn.createStatement();

            stmt.executeQuery("USE factory");
            rs = stmt.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }

    public String ResultSet2String(ResultSet rs){

        String result = "";

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element results = doc.createElement("Results");
            doc.appendChild(results);

            ResultSetMetaData rsmd = rs.getMetaData();
            int colCount = rsmd.getColumnCount();

            while (rs.next()) {
                Element row = doc.createElement("Row");
                results.appendChild(row);
                for (int i = 1; i <= colCount; i++) {
                    String columnName = rsmd.getColumnName(i);
                    Object value = rs.getObject(i);
                    Element node = doc.createElement(columnName);
                    if(value != null) node.appendChild(doc.createTextNode(value.toString()));
                    else {
                        node.appendChild(doc.createTextNode(""));
                    }
                    row.appendChild(node);
                }
            }

            DOMSource domSource = new DOMSource(doc);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            StringWriter sw = new StringWriter();
            StreamResult sr = new StreamResult(sw);
            transformer.transform(domSource, sr);

            result = sw.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void getTOrders(List<Transformation_Order> transforders){

        String query = "SELECT * FROM transforders2;";

        ResultSet rset = (new Database_Connection()).query2(query);

        int rowCount = 0;
        try {
            while (rset.next()) {   // Repeatedly process each row

                if (rset.getInt("Status") != 4) {
                    int mainID = rset.getInt("MainID");
                    int ID = rset.getInt("ID");
                    int subID = rset.getInt("SubID");
                    int status = rset.getInt("Status");
                    String from = rset.getString("InitType");
                    String to = rset.getString("FinalType");
                    int inputTime = rset.getInt("InputTime");
                    int realInputTime = rset.getInt("RealInputTime");
                    int maxDelay = rset.getInt("MaxDelay");
                    int penalty = rset.getInt("Penalty");
                    int startTime = rset.getInt("StartTime");
                    int endTime = rset.getInt("EndTime");

                    Transformation_Order order = new Transformation_Order(mainID, ID, subID, from, to, inputTime, maxDelay, penalty, realInputTime);
                    order.setStartTime(startTime);
                    transforders.add(order);
                }
                rowCount++;
            }
            System.out.println("Total Transformation Orders from DB = " + rowCount);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void getUOrders(List<Unloading_Order> unldorders){

        String query = "SELECT * FROM unldorders;";

        ResultSet rset = (new Database_Connection()).query2(query);

        int rowCount = 0;
        try {
            while (rset.next()) {   // Repeatedly process each row

                if (rset.getInt("Status") != 4) {
                    int mainID = rset.getInt("MainID");
                    int ID = rset.getInt("ID");
                    String blockType = rset.getString("BlockType");
                    String dest = rset.getString("Destination");
                    boolean priority = rset.getBoolean("Priority");

                    Unloading_Order order = new Unloading_Order(blockType,dest,mainID,ID,priority);
                    unldorders.add(order);
                }
                rowCount++;
            }
            System.out.println("Total Unloading Orders from DB = " + rowCount);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void updateTOrders(List<Transformation_Order> transfOrders){

        StringBuilder sb = new StringBuilder();

        for(Transformation_Order order : transfOrders){

            Order.Status status = order.getStatus();
            int status_int = 0;
            if(status == Order.Status.NEW)
                status_int = 1;
            else if(status == Order.Status.READY)
                status_int = 2;
            else if(status == Order.Status.RUNNING)
                status_int = 3;
            else if(status == Order.Status.COMPLETED)
                status_int = 4;

            sb.append("REPLACE INTO transforders2 VALUES ("
                    + order.getMainID() + ","
                    + order.getID() + ","
                    + order.getSubID() + ","
                    + status_int + ","
                    + "'" + order.getInitBlockType() + "',"
                    + "'" + order.getFinalBlockType() + "',"
                    + order.getInputTime() + ","
                    + order.getRealInputTime() + ","
                    + order.getMaxDelay() + ","
                    + order.getPenalty() + ","
                    + order.getStartTime() + ","
                    + order.getEndTime() + ");\n");
        }
        String res = (new Database_Connection()).query(sb.toString());
        //System.out.println(res);
    }

    public static void updateUOrders(List<Unloading_Order> unldOrders){

        StringBuilder sb = new StringBuilder();

        for(Unloading_Order order : unldOrders){

            Order.Status status = order.getStatus();
            int status_int = 0;
            if(status == Order.Status.NEW)
                status_int = 1;
            else if(status == Order.Status.READY)
                status_int = 2;
            else if(status == Order.Status.RUNNING)
                status_int = 3;
            else if(status == Order.Status.COMPLETED)
                status_int = 4;

            sb.append("REPLACE INTO unldorders VALUES ("
                    + order.getMainID() + ","
                    + order.getID() + ","
                    + status_int + ","
                    + "'" + order.getBlockType() + "',"
                    + "'" + order.getDestination() + "',"
                    + order.isPriority() + ");\n");
        }
        String res = (new Database_Connection()).query(sb.toString());
        //System.out.println(res);
    }





    public static void updateDB(List<Transformation_Order> transfOrders){

        StringBuilder sb = new StringBuilder();

        List<Integer> mainIDs = Transformation_Order.getMainIDs(transfOrders);

        for(Integer mainID : mainIDs){
            List<Transformation_Order> orders = Transformation_Order.getOrdersByMainID(transfOrders,mainID);
            int total = 0;
            int finished = 0;
            int running = 0;
            long startTime = Long.MAX_VALUE;
            long endTime = Long.MIN_VALUE;

            Order.Status status;
            List<Integer> IDs = Transformation_Order.getIDs(orders);

            for(Integer ID : IDs){
                List<Transformation_Order> subOrders = Transformation_Order.getOrdersByID(orders,ID);

                total++;
                status = Order.Status.READY;

                for(Transformation_Order subOrder : subOrders){
                    if(subOrder.getStatus() == Order.Status.RUNNING){
                        status = Order.Status.RUNNING;
                    }
                    else if(status != Order.Status.RUNNING && subOrder.getStatus() == Order.Status.COMPLETED){
                        status = Order.Status.COMPLETED;
                    }
                    if(subOrder.getStartTime() < startTime){
                        startTime = subOrder.getStartTime();
                    }
                    if(subOrder.getEndTime() > endTime){
                        endTime = subOrder.getEndTime();
                    }
                }

                if(status == Order.Status.COMPLETED){
                    finished++;
                }
                else if(status == Order.Status.RUNNING){
                    running++;
                }
            }

            sb.append("REPLACE INTO transforders VALUES ("
                    + mainID + ","
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