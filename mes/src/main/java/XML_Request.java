import java.net.InetAddress;

public class XML_Request {

    private String request;
    private InetAddress address;
    private int port;

    public XML_Request(String request, InetAddress address, int port) {
        this.request = request;
        this.address = address;
        this.port = port;
    }


    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
