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
            ds = new DatagramSocket(1234);
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

/*
    Application app;
    private final DatagramSocket socket;
    private final byte[] buf = new byte[2048];
    List packet_list;

    public UDP_Listener(int listening_port, List packet_list, Application app) throws SocketException {
        socket = new DatagramSocket(listening_port);
        this.packet_list=packet_list;
        this.app=app;
    }


    public void run() {
        boolean running = true;

        while (running) {
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String received
                    = new String(packet.getData(), 0, packet.getLength());

            packet_list.add(received);
            app.refresh();
            if (received.equals("end")) {
                running = false;
                continue;
            }


        }
        socket.close();
    }
*/
}

