import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedDataItem;
import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedSubscription;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

import java.util.List;

public class PLC_Manager extends Thread {

    List<Transformation_Order> transfOrders;
    List<Unloading_Order> unldOrders;

    OPC_UA_Connection conn;

    Boolean wo1State;
    String wo1Identifier = "|var|CODESYS Control Win V3 x64.Application.Wo1_Controller.Empty.x";

    ManagedSubscription subscriptionWo1;
    ManagedSubscription subscriptionWi1;

    public PLC_Manager(List<Transformation_Order> transfOrders, List<Unloading_Order> unldOrders) {
        this.wo1State = false;
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

            dataItem = subscriptionWo1.createDataItem(new NodeId(4, wo1Identifier));
            if (!dataItem.getStatusCode().isGood()) {
                throw new RuntimeException("uh oh!");
            }

        } catch (UaException e) {
            e.printStackTrace();
        }

        subscriptionWo1.addChangeListener(new ManagedSubscription.ChangeListener() {
            @Override
            public void onDataReceived(List<ManagedDataItem> dataItems, List<DataValue> dataValues) {
                int i = 0;
                for (ManagedDataItem item : dataItems) {
                    if(item.getNodeId().getIdentifier().equals(wo1Identifier)){
                        synchronized (wo1State) {
                            wo1State = (boolean) dataValues.get(i).getValue().getValue();
                            System.out.println("Wo1" + wo1State);
                        }
                    }
                    i++;
                }
            }
        });

    }

    @Override
    public void run() {
        System.out.println("run");
        while (true) {
            synchronized (wo1State){
            System.out.println(wo1State);
            if(wo1State){
                Transformation_Order transf = null;
                synchronized (transfOrders){
                    for(Transformation_Order aux : transfOrders){
                        if(aux.getStatus() == Order.Status.READY){
                            transf = aux;
                            break;
                        }
                        System.out.println(aux.getStatus());
                    }
                    if(transf!=null){
                        transf.setStatus(Order.Status.RUNNING);
                        conn.setValue("WHsControl.pieceWo1",transf.getPath());
                        //updateDB
                    }
                }
                if(transf == null){
                    synchronized (unldOrders){
                        Unloading_Order unld = null;
                        for(Unloading_Order aux : unldOrders){
                            if(aux.getStatus() == Order.Status.READY){
                                unld = aux;
                            }
                        }
                        if(unld!=null){
                            unld.setStatus(Order.Status.RUNNING);
                            conn.setValue("WHsControl.pieceWo1",unld.getPath());
                            //updateDB
                        }
                    }
                }
            }}
        }
    }
}