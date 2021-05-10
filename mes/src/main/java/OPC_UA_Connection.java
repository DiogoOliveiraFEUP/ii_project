import org.eclipse.milo.opcua.sdk.client.*;
import org.eclipse.milo.opcua.sdk.client.nodes.UaNode;
import org.eclipse.milo.opcua.sdk.client.nodes.UaVariableNode;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import java.io.FileInputStream;
import java.util.Properties;

public class OPC_UA_Connection {

    private OpcUaClient client;
    AddressSpace addressSpace;

    public OPC_UA_Connection(){

        Properties opc_ua_props = new Properties();
        FileInputStream in;
        System.out.println(System.getProperty("user.dir"));

        try {

            in = new FileInputStream("src\\main\\config\\OPC_UA_Connection.properties");
            opc_ua_props.load(in);
            in.close();

            client = OpcUaClient.create(
                    "opc.tcp://" + opc_ua_props.getProperty("url") + ":" + opc_ua_props.getProperty("port") + "/",
                    endpoints ->
                            endpoints.stream()
                                    .filter(e -> e.getSecurityPolicyUri().equals(SecurityPolicy.None.getUri()))
                                    .findFirst(),
                    configBuilder ->
                            configBuilder.build()
            );
            client.connect().get();
            addressSpace = client.getAddressSpace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OpcUaClient getClient(){
        return this.client;
    }

    public void setValue(String node, Object value){
        UaVariableNode nodeToChange = null;
        try {
            nodeToChange = (UaVariableNode) addressSpace.getNode(new NodeId(4,"|var|CODESYS Control Win V3 x64.Application."+ node));
            nodeToChange.writeValue(new Variant(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getValue(String node){
        UaVariableNode nodeToRead;
        try {
            nodeToRead = (UaVariableNode) addressSpace.getNode(new NodeId(4,node));
            return nodeToRead.readValue().getValue().getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

