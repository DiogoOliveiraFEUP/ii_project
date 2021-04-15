import java.util.List;

public class XML_Processor extends Thread{

    private XML_Parser xml_parser = new XML_Parser();
    private List<XML_Request> xml_requests;
    private List<Order> orders;

    public XML_Processor(List<XML_Request> xml_requests, List<Order> orders) {
        this.xml_requests = xml_requests;
        this.orders = orders;
    }

    @Override
    public void run(){

        while(true){
            synchronized (xml_requests){
                if(!xml_requests.isEmpty()){
                    xml_parser.parse(xml_requests.get(0),orders);
                    xml_requests.remove(0);
                    System.out.println(xml_requests.size());
                }
            }
        }
    }
}
