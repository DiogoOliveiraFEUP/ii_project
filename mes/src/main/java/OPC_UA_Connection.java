import com.google.common.collect.ImmutableList;
import org.eclipse.milo.opcua.sdk.client.*;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.nodes.UaNode;
import org.eclipse.milo.opcua.sdk.core.nodes.Node;
import org.eclipse.milo.opcua.sdk.core.nodes.VariableNode;
import org.eclipse.milo.opcua.stack.client.UaStackClient;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.transport.TransportProfile;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class OPC_UA_Connection {

    OpcUaClient client;

    private final Logger logger = LoggerFactory.getLogger(getClass());


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
        AddressSpace addressSpace = client.getAddressSpace();

        UaNode serverNode = addressSpace.getNode(Identifiers.Server);

        List<? extends UaNode> nodes = addressSpace.browseNodes(serverNode);

        Node plcNode;

        for (UaNode node : nodes) {
            if (node.getDisplayName().equals(opc_ua_props.getProperty("plc_name"))) {
                plcNode = node;
                break;
            }
            ;
        }

        NodeId GVLId = new NodeId(4,"|var|CODESYS Control Win V3 x64.Application.GVL");

        UaNode GVL = addressSpace.getNode(GVLId);

        List<? extends UaNode> vnodes = addressSpace.browseNodes(GVL);

        for(UaNode vnode: vnodes){
            System.out.println(((VariableNode)vnode).getValue().getValue());
        }



    }




}

