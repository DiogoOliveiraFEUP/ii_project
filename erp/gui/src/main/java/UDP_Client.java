import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDP_Client {

    private DatagramSocket socket;

    private byte[] buf = new byte[65508];

    public UDP_Client(int port){
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
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

    public String listen() {

        DatagramPacket packet = new DatagramPacket(buf,buf.length);
        System.out.println("Waiting...");
        try {
            socket.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Received...");

        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        String received = new String(packet.getData(),0,packet.getLength());
        System.out.println(packet.getLength());
        System.out.println(received);
        return received;
    }

}
