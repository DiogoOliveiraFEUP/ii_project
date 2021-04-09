import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

        List<String> xml_requests = new ArrayList<>();

        UDP_Listener udp_listener = new UDP_Listener();
        udp_listener.run(xml_requests);

        List<Order> orders = new ArrayList<>();

        XML_Processor xml_processor = new XML_Processor();
        xml_processor.run(xml_requests, orders);



    }
}
