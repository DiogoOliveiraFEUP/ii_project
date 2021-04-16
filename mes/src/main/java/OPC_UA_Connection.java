import org.eclipse.milo.opcua.sdk.client.*;
import org.eclipse.milo.opcua.sdk.client.nodes.UaNode;
import org.eclipse.milo.opcua.sdk.core.nodes.VariableNode;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class OPC_UA_Connection {

    OpcUaClient client;

    private final Logger logger = LoggerFactory.getLogger(getClass());
    UaNode serverNode;
    UaNode GVLNode;
    AddressSpace addressSpace;

    public OPC_UA_Connection() throws UaException, ExecutionException, InterruptedException, IOException {

        Properties opc_ua_props = new Properties();
        FileInputStream in = null;
        System.out.println(System.getProperty("user.dir"));


        try {
            in = new FileInputStream("src\\main\\java\\OPC_UA_Connection.properties");
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            throw new FileNotFoundException();
        }
        opc_ua_props.load(in);
        in.close();
        System.out.println(opc_ua_props.getProperty("url"));

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

        serverNode = addressSpace.getNode(Identifiers.Server);
        NodeId GVLId = new NodeId(4,"|var|CODESYS Control Win V3 x64.Application.GVL");

        GVLNode = addressSpace.getNode(GVLId);

    }

    public void setValue(String node, boolean value) throws UaException {
        System.out.println(GVLNode.getNodeId().getIdentifier() +"."+ node);

        UaNode nodeToChange = addressSpace.getNode(new NodeId(4,GVLNode.getNodeId().getIdentifier() +"."+ node));
        client.writeValue(nodeToChange.getNodeId(),new DataValue(new Variant(value),null,null));

    }

    public boolean getBooleanValue(String node) throws UaException, ExecutionException, InterruptedException {
        return (boolean) getValue(node);
    }

    public String getStringValue(String node) throws UaException, ExecutionException, InterruptedException {
        return (String) getValue(node).toString();
    }


    public Object getValue(String node) throws UaException, ExecutionException, InterruptedException {
        return getValue(addressSpace.getNode(new NodeId(4,GVLNode.getNodeId().getIdentifier() +"."+ node)));
    }

    public Object getValue(UaNode vNode) throws ExecutionException, InterruptedException {
        return client.readValue(0.0, TimestampsToReturn.Both,vNode.getNodeId()).get().getValue().getValue();
    }


}

