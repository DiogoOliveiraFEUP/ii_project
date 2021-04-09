import java.util.List;

public class XML_Processor {

    XML_Parser xml_parser = new XML_Parser();

    public void run(List<String> xml_requests, List<Order> orders){

        while(true){
            if(!xml_requests.isEmpty()){
                xml_parser.parse(xml_requests.get(0),orders);
                xml_requests.remove(0);
            }
        }
    }
}
