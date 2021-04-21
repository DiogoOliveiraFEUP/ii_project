import org.eclipse.milo.opcua.stack.core.UaException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Application {
    public static void main(String[] args){

        System.out.println("Hello World!");

        /*OPC_UA_Connection conn = null;

        try {
            conn = new OPC_UA_Connection();
            conn.setValue("bool_var",true);
        } catch (Exception e) {
            //e.printStackTrace();
        }*/

        List<XML_Request> xml_requests = new ArrayList<>();
        List<Transformation_Order> transfOrders = new ArrayList<>();
        List<Unloading_Order> unldOrders = new ArrayList<>();

        UDP_Listener udp_listener = new UDP_Listener(xml_requests);
        udp_listener.start();

        XML_Processor xml_processor = new XML_Processor(xml_requests, transfOrders, unldOrders);
        xml_processor.start();

        //SYNCHRONIZE ORDERS!!!!!

    }
}
