import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

public class UDP_Listener {

    private DatagramSocket ds = new DatagramSocket(1234);
    private byte[] buf = new byte[65535];

    public UDP_Listener() throws SocketException {
    }

    public void run(List<String> XML_Requests) throws IOException {

        while(true){
            DatagramPacket packet = new DatagramPacket(buf,buf.length);
            System.out.println("Waiting...");
            ds.receive(packet);
            System.out.println("Received...");

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String received = new String(packet.getData(),0,packet.getLength());

            System.out.println(received);
            XML_Requests.add(received);

        }
    }

}
