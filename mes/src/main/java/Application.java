import org.eclipse.milo.opcua.stack.core.UaException;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Application {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

        try {
            new OPC_UA_Connection();
        } catch (UaException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        List<String> xml_requests = new ArrayList<>();

        UDP_Listener udp_listener = new UDP_Listener();
        udp_listener.run(xml_requests);

        List<Order> orders = new ArrayList<>();

        XML_Processor xml_processor = new XML_Processor();
        xml_processor.run(xml_requests, orders);



    }
}
