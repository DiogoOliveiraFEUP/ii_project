import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {

        List<XML_Request> xml_requests = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        UDP_Listener udp_listener = new UDP_Listener(xml_requests);
        udp_listener.start();

        XML_Processor xml_processor = new XML_Processor(xml_requests, orders);
        xml_processor.start();

    }
}
