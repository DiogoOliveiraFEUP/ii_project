import org.eclipse.milo.opcua.sdk.client.*;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.nodes.UaNode;
import org.eclipse.milo.opcua.stack.client.UaStackClient;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.transport.TransportProfile;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class OPC_UA_Connection {

    OpcUaClient client = OpcUaClient.create(
            "opc.tcp://localhost:4840/",
            endpoints ->
                    endpoints.stream()
                            .filter(e -> e.getSecurityPolicyUri().equals(SecurityPolicy.None.getUri()))
                            .findFirst(),
            configBuilder ->
                    configBuilder.build()
    );
    public OPC_UA_Connection() throws UaException, ExecutionException, InterruptedException {
        client.connect().get();
        AddressSpace addressSpace = client.getAddressSpace();

        UaNode serverNode = addressSpace.getNode(Identifiers.Server);

        List<? extends UaNode> nodes = addressSpace.browseNodes(serverNode);

        for (UaNode node :nodes
             ) {
            System.out.println(node.getDisplayName());
        }
    }
}
