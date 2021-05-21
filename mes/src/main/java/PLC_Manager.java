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
    String wi1OrderNode   = "|var|CODESYS Control Win V3 x64.Application.WHs.Wi1OrderID";
    String wi1DoneNode   = "|var|CODESYS Control Win V3 x64.Application.WHs.Wi1Done";
    ManagedSubscription subscriptionWi1;

    String wo2EmptyNode = "|var|CODESYS Control Win V3 x64.Application.Factory_Left.Wo2.Empty.x";
    String wo2PieceNode = "|var|CODESYS Control Win V3 x64.Application.WHs.Wo2PieceIn";
    ManagedSubscription subscriptionWo2;
    String wi2OrderNode   = "|var|CODESYS Control Win V3 x64.Application.WHs.Wi2OrderID";
    String wi2DoneNode   = "|var|CODESYS Control Win V3 x64.Application.WHs.Wi2Done";
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
            dataItem = subscriptionWi1.createDataItem(new NodeId(4, wi1OrderNode));
            if (!dataItem.getStatusCode().isGood()) {
                throw new RuntimeException("uh oh!");
            }
            dataItem = subscriptionWi2.createDataItem(new NodeId(4, wi2OrderNode));
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
                        if(wo1State){
                            evalWo1();
                        }
                    }
                    i++;
                }
            }
        });

        /* Avisa MES que Wi1 concluiu um caminho */
        subscriptionWi1.addChangeListener(new ManagedSubscription.ChangeListener() {
            @Override
            public void onDataReceived(List<ManagedDataItem> dataItems, List<DataValue> dataValues) {
                int i = 0;
                System.out.println("hello");
                for (ManagedDataItem item : dataItems) {
                    if(item.getNodeId().getIdentifier().equals(wi1OrderNode)){
                        /* deal with order completed */
                        String orderID = (String) dataValues.get(i).getValue().getValue();
                        if(!orderID.equals("null")){
                            System.out.println(orderID);
                            conn.setValue(wi1DoneNode,true);
                        }
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
                        if(wo2State){
                            evalWo2();
                        }
                    }
                    i++;
                }
            }
        });

        subscriptionWi2.addChangeListener(new ManagedSubscription.ChangeListener() {
            @Override
            public void onDataReceived(List<ManagedDataItem> dataItems, List<DataValue> dataValues) {
                int i = 0;
                for (ManagedDataItem item : dataItems) {
                    if(item.getNodeId().getIdentifier().equals(wi2OrderNode)){
                        /* deal with order completed */
                        String orderID = (String) dataValues.get(i).getValue().getValue();
                        if(!orderID.equals("null")){
                            System.out.println(orderID);
                            conn.setValue(wi2DoneNode,true);
                        }
                    }
                    i++;
                }
            }
        });
    }


    public void evalWo1() {

        boolean wo1State = (boolean) PLC_Manager.getInstance().conn.getValue(wo1EmptyNode);

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
                        if (aux.getStatus() == Order.Status.READY && aux.getPath().contains("Wo1")) {
                            transf = aux;
                            break;
                        }
                    }
                    if (transf != null) {
                        transf.setStatus(Order.Status.RUNNING);

                        String path = transf.getPath().replace("Wo1:","") + "?P=" + transf.getInitBlockType().substring(1)
                                + "?ID=" + transf.getMainID() + "." + transf.getID() + "." + transf.getSubID();

                        System.out.println(path);

                        conn.setValue(wo1PieceNode, path);
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
                        if (aux.getStatus() == Order.Status.READY && aux.getPath().contains("Wo2")) {
                            transf = aux;
                            break;
                        }
                    }
                    if (transf != null) {
                        transf.setStatus(Order.Status.RUNNING);

                        String path = transf.getPath().replace("Wo2:","") + "?P=" + transf.getInitBlockType().substring(1)
                                + "?ID=" + transf.getMainID() + "." + transf.getID() + "." + transf.getSubID();

                        System.out.println(path);

                        conn.setValue(wo2PieceNode, path);
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