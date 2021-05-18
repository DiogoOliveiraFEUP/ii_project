import Order.Transformation_Order;
import Order.Unloading_Order;
import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedDataItem;
import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedSubscription;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

import java.util.List;

public class PLC_Manager {

    private static PLC_Manager singleton = null;

    public static PLC_Manager getInstance()
    {
        return singleton;
    }

    List<Transformation_Order> transfOrders;
    List<Unloading_Order> unldOrders;

    OPC_UA_Connection conn;

    String wo1EmptyNode = "|var|CODESYS Control Win V3 x64.Application.Factory_Right.Empty.x";
    String wo1PieceNode = "|var|CODESYS Control Win V3 x64.Application.WHs.Wo1PieceIn";
    ManagedSubscription subscriptionWo1;
    String wi1EndNode   = "|var|CODESYS Control Win V3 x64.Application";
    ManagedSubscription subscriptionWi1;

    public PLC_Manager(List<Transformation_Order> transfOrders, List<Unloading_Order> unldOrders) {

        singleton = this;

        this.transfOrders = transfOrders;
        this.unldOrders = unldOrders;

        conn = new OPC_UA_Connection();

        try {
            subscriptionWo1 = ManagedSubscription.create(conn.getClient());
            subscriptionWi1 = ManagedSubscription.create(conn.getClient());
        } catch (UaException e) {
            e.printStackTrace();
        }

        ManagedDataItem dataItem;
        try {

            dataItem = subscriptionWo1.createDataItem(new NodeId(4, wo1EmptyNode));
            if (!dataItem.getStatusCode().isGood()) {
                throw new RuntimeException("uh oh!");
            }

        } catch (UaException e) {
            e.printStackTrace();
        }

        /* Avisa MES que Wo1 ficou livre */
        subscriptionWo1.addChangeListener(new ManagedSubscription.ChangeListener() {
            @Override
            public void onDataReceived(List<ManagedDataItem> dataItems, List<DataValue> dataValues) {
                int i = 0;
                for (ManagedDataItem item : dataItems) {
                    if(item.getNodeId().getIdentifier().equals(wo1EmptyNode)){
                        boolean wo1State = (boolean) dataValues.get(i).getValue().getValue();
                        System.out.println("Wo1" + wo1State);
                        if(wo1State){
                            evalWo1();
                        }
                    }
                    i++;
                }
            }
        });

        /* Avisa MES que Wi1 concluiu um caminho */
        subscriptionWo1.addChangeListener(new ManagedSubscription.ChangeListener() {
            @Override
            public void onDataReceived(List<ManagedDataItem> dataItems, List<DataValue> dataValues) {
                int i = 0;
                for (ManagedDataItem item : dataItems) {
                    if(item.getNodeId().getIdentifier().equals(wi1EndNode)){
                        /* deal with order completed */

                        /*
                            ...
                        */
                    }
                    i++;
                }
            }
        });

    }


    public void evalWo1() {
        boolean wo1State = (boolean) conn.getValue(wo1EmptyNode);

        if (wo1State) {

            Unloading_Order unld = null;
            /* check prioritary unloading orders */

            /*
                    ...
            */

            if(unld == null){

                Transformation_Order transf = null;
                /* check next transformation order for wo1 */
                synchronized (transfOrders) {
                    for (Transformation_Order aux : transfOrders) {
                        if (aux.getStatus() == Order.Order.Status.READY && aux.getPath().contains("Wo1")) {
                            transf = aux;
                            break;
                        }
                        System.out.println(aux.getStatus());
                    }
                    if (transf != null) {
                        transf.setStatus(Order.Order.Status.RUNNING);
                        conn.setValue(wo1PieceNode, transf.getPath());
                        conn.setValue(wo1PieceNode, "");
                        //updateDB
                    }
                }

                if (transf == null) {

                    /* check for unloading orders */
                    synchronized (unldOrders) {
                        for (Unloading_Order aux : unldOrders) {
                            if (aux.getStatus() == Order.Order.Status.READY && aux.getPath().contains("Wo1")) {
                                unld = aux;
                            }
                        }
                        if (unld != null) {
                            unld.setStatus(Order.Order.Status.RUNNING);
                            conn.setValue(wo1PieceNode, unld.getPath());
                            conn.setValue(wo1PieceNode, "");
                            //updateDB
                        }
                    }
                }
            }
        }
    }

    public void evalWo2() {


    }
}