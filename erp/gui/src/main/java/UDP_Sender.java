import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UDP_Sender {

    private DatagramSocket socket;
    private byte[] buf;

    public UDP_Sender(int port){
        try{
            socket = new DatagramSocket(port);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void send(String message, InetAddress address, int port){
        buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf,buf.length,address,port);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
