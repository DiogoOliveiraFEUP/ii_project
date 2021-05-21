import Order.*;
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

    final List<Transformation_Order> transfOrders;
    final List<Unloading_Order> unldOrders;

    OPC_UA_Connection conn;

    String wo1EmptyNode = "|var|CODESYS Control Win V3 x64.Application.Factory_Right.Wo1.Empty.x";
    String wo1PieceNode = "|var|CODESYS Control Win V3 x64.Application.WHs.Wo1PieceIn";
    ManagedSubscription subscriptionWo1;
    String wi1EndNode   = "|var|CODESYS Control Win V3 x64.Application";
    ManagedSubscription subscriptionWi1;

    String wo2EmptyNode = "|var|CODESYS Control Win V3 x64.Application.Factory_Left.Wo2.Empty.x";
    String wo2PieceNode = "|var|CODESYS Control Win V3 x64.Application.WHs.Wo2PieceIn";
    ManagedSubscription subscriptionWo2;
    String wi2EndNode   = "|var|CODESYS Control Win V3 x64.Application";
    ManagedSubscription subscriptionWi2;


    public PLC_Manager(List<Transformation_Order> transfOrders, List<Unloading_Order> unldOrders) {

        singleton = this;

        this.transfOrders = transfOrders;
        this.unldOrders = unldOrders;

        conn = new OPC_UA_Connection();

        try {
            subscriptionWo1 = ManagedSubscription.create(conn.getClient());
            subscriptionWi1 = ManagedSubscription.create(conn.getClient());
            subscriptionWo2 = ManagedSubscription.create(conn.getClient());
            subscriptionWi2 = ManagedSubscription.create(conn.getClient());
        } catch (UaException e) {
            e.printStackTrace();
        }

        ManagedDataItem dataItem;
        try {

            dataItem = subscriptionWo1.createDataItem(new NodeId(4, wo1EmptyNode));
            if (!dataItem.getStatusCode().isGood()) {
                throw new RuntimeException("uh oh!");
            }
            dataItem = subscriptionWo2.createDataItem(new NodeId(4, wo2EmptyNode));
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

        /* Avisa MES que Wo2 ficou livre */
        subscriptionWo2.addChangeListener(new ManagedSubscription.ChangeListener() {
            @Override
            public void onDataReceived(List<ManagedDataItem> dataItems, List<DataValue> dataValues) {
                int i = 0;
                for (ManagedDataItem item : dataItems) {
                    if(item.getNodeId().getIdentifier().equals(wo2EmptyNode)){
                        boolean wo2State = (boolean) dataValues.get(i).getValue().getValue();
                        System.out.println("Wo2" + wo2State);
                        if(wo2State){
                            evalWo2();
                        }
                    }
                    i++;
                }
            }
        });

    }


    public void evalWo1() {

        boolean wo1State = (boolean) PLC_Manager.getInstance().conn.getValue(wo1EmptyNode);
        System.out.println(wo1State);
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
                        System.out.println(aux.getPath());
                        if (aux.getStatus() == Order.Status.READY && aux.getPath().contains("Wo1")) {
                            transf = aux;
                            break;
                        }
                        System.out.println(aux.getStatus());
                    }
                    if (transf != null) {
                        transf.setStatus(Order.Status.RUNNING);
                        conn.setValue(wo1PieceNode, transf.getPath().replace("Wo1:",""));
                        //updateDB
                    }
                }

                if (transf == null) {

                    /* check for unloading orders */
                    synchronized (unldOrders) {
                        for (Unloading_Order aux : unldOrders) {
                            if (aux.getStatus() == Order.Status.READY && aux.getPath().contains("Wo1")) {
                                unld = aux;
                            }
                        }
                        if (unld != null) {
                            unld.setStatus(Order.Status.RUNNING);
                            conn.setValue(wo1PieceNode, unld.getPath());
                            //updateDB
                        }
                    }
                }
            }
        }
    }

    public void evalWo2() {
        boolean wo2State = (boolean) PLC_Manager.getInstance().conn.getValue(wo2EmptyNode);
        System.out.println(wo2State);
        if (wo2State) {

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
                        System.out.println(aux.getPath());
                        if (aux.getStatus() == Order.Status.READY && aux.getPath().contains("Wo2")) {
                            transf = aux;
                            break;
                        }
                        System.out.println(aux.getStatus());
                    }
                    if (transf != null) {
                        transf.setStatus(Order.Status.RUNNING);
                        conn.setValue(wo2PieceNode, transf.getPath().replace("Wo2:",""));
                        //updateDB
                    }
                }

                if (transf == null) {

                    /* check for unloading orders */
                    synchronized (unldOrders) {
                        for (Unloading_Order aux : unldOrders) {
                            if (aux.getStatus() == Order.Status.READY && aux.getPath().contains("Wo2")) {
                                unld = aux;
                            }
                        }
                        if (unld != null) {
                            unld.setStatus(Order.Status.RUNNING);
                            conn.setValue(wo2PieceNode, unld.getPath());
                            //updateDB
                        }
                    }
                }
            }
        }
    }
}