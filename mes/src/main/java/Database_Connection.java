import java.io.StringWriter;
import java.sql.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class Database_Connection {

    String URL = "jdbc:mariadb://localhost/";
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


}