import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

public class UDP_Listener extends Thread{

    private DatagramSocket ds = new DatagramSocket(1234);
    private byte[] buf = new byte[65535];

    private List<String> xml_requests;

    public UDP_Listener(List<String> xml_requests) throws SocketException {
        this.xml_requests = xml_requests;
    }

    @Override
    public void run() {

        while(true){
            DatagramPacket packet = new DatagramPacket(buf,buf.length);
            System.out.println("Waiting...");
            try {
                ds.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Received...");

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String received = new String(packet.getData(),0,packet.getLength());

            //System.out.println(received);

            synchronized (xml_requests){
                xml_requests.add(received);
            }
        }
    }

}
