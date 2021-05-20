import Order.Transformation_Order;
import Order.Unloading_Order;

import java.util.List;

public class XML_Processor extends Thread{

    private final XML_Parser xml_parser = new XML_Parser();
    private final List<XML_Request> xml_requests;
    List<Transformation_Order> transfOrders;
    List<Unloading_Order> unldOrders;

    public XML_Processor(List<XML_Request> xml_requests, List<Transformation_Order> transfOrders, List<Unloading_Order> unldOrders) {
        this.xml_requests = xml_requests;
        this.transfOrders = transfOrders;
        this.unldOrders = unldOrders;
    }

    @Override
    public void run(){

        while(true){
            synchronized (xml_requests){
                if(!xml_requests.isEmpty()){
                    xml_parser.parse(xml_requests.get(0),transfOrders,unldOrders);
                    xml_requests.remove(0);
                    System.out.println(xml_requests.size());
                }
            }
        }
    }
}
