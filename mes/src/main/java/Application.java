import Order.Transformation_Order;
import Order.Unloading_Order;
import GUI.GUI;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args){

        //GUI
        GUI gui = new GUI();

        //SYNCHRONIZE XML_REQUESTS!!!!!
        List<XML_Request> xml_requests = new ArrayList<>();

        //SYNCHRONIZE ORDERS!!!!!
        List<Transformation_Order> transfOrders = new ArrayList<>();
        List<Unloading_Order> unldOrders = new ArrayList<>();

        //GET ORDERS FROM DB
        Database_Connection db = new Database_Connection();
        Database_Connection.getTOrders(transfOrders);
        Database_Connection.getUOrders(unldOrders);
        Database_Connection.getMacQuant(gui);
        Database_Connection.getMacTimeQuant(gui);
        Database_Connection.getUnld(gui);

        UDP_Listener udp_listener = new UDP_Listener(xml_requests);
        udp_listener.start();

        XML_Processor xml_processor = new XML_Processor(xml_requests, transfOrders, unldOrders);
        xml_processor.start();

        PLC_Manager PLC = new PLC_Manager(transfOrders, unldOrders, gui);

        (new Scheduler()).schedule(transfOrders);
        PLC.evalWo1();
        PLC.evalWo2();
        PLC.updateStocks();

    }
}