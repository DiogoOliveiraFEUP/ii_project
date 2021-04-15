import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    GUI gui;

    public void refresh(){
        gui.refresh();
    }
    Application(){
        ArrayList packet_list = new ArrayList<String>();
        gui = new GUI(packet_list);
        System.out.println("Please input a port to listen to\n");
        Scanner kb_scanner = new Scanner(System.in);
        int port = kb_scanner.nextInt();
        try {
            Thread udp_listener = new UDP_Listener(port,packet_list, this);
            udp_listener.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while(true){
            byte next_c = (byte) kb_scanner.next().charAt(0);
            if(next_c == 'O'){
                for(Object packet: packet_list){
                    System.out.println(packet);
                }
            }else if(next_c == 'U'){
                    gui.refresh();
            }else if(next_c == 'C') {

            }
        }



    }

    public static void main(String[] args) {
        new Application();
    }
}
