import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

public class UDP_Listener {

    private DatagramSocket ds;

    private byte[] buf = new byte[65535];

    public UDP_Listener(){
        try {
            ds = new DatagramSocket(4321);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public String listen() {

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

        ds.close();

        return received;
    }
}

