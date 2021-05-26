import GUI.GUI;
import Order.*;
import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedDataItem;
import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedSubscription;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

import java.time.Instant;
import java.util.List;

public class PLC_Manager {

    private static PLC_Manager singleton = null;

    public static PLC_Manager getInstance()
    {
        return singleton;
    }

    final List<Transformation_Order> transfOrders;
    final List<Unloading_Order> unldOrders;

    private GUI gui;

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

    String O1Full = "|var|CODESYS Control Win V3 x64.Application.Factory_Right.O1.Step2.x";
    String O2Full = "|var|CODESYS Control Win V3 x64.Application.Factory_Right.O2.Step2.x";
    String O3Full = "|var|CODESYS Control Win V3 x64.Application.Factory_Right.O3.Step2.x";
    String O1OrderNode = "|var|CODESYS Control Win V3 x64.Application.WHs.O1OrderID";
    String O2OrderNode = "|var|CODESYS Control Win V3 x64.Application.WHs.O2OrderID";
    String O3OrderNode = "|var|CODESYS Control Win V3 x64.Application.WHs.O3OrderID";
    String O1DoneNode = "|var|CODESYS Control Win V3 x64.Application.WHs.O1Done";
    String O2DoneNode = "|var|CODESYS Control Win V3 x64.Application.WHs.O2Done";
    String O3DoneNode = "|var|CODESYS Control Win V3 x64.Application.WHs.O3Done";
    ManagedSubscription subscriptionO1;
    ManagedSubscription subscriptionO2;
    ManagedSubscription subscriptionO3;

    String stockP1 = "|var|CODESYS Control Win V3 x64.Application.WHs.StockP1";
    String stockP2 = "|var|CODESYS Control Win V3 x64.Application.WHs.StockP2";
    String stockP3 = "|var|CODESYS Control Win V3 x64.Application.WHs.StockP3";
    String stockP4 = "|var|CODESYS Control Win V3 x64.Application.WHs.StockP4";
    String stockP5 = "|var|CODESYS Control Win V3 x64.Application.WHs.StockP5";
    String stockP6 = "|var|CODESYS Control Win V3 x64.Application.WHs.StockP6";
    String stockP7 = "|var|CODESYS Control Win V3 x64.Application.WHs.StockP7";
    String stockP8 = "|var|CODESYS Control Win V3 x64.Application.WHs.StockP8";
    String stockP9 = "|var|CODESYS Control Win V3 x64.Application.WHs.StockP9";
    ManagedSubscription subscriptionStocks;

    public PLC_Manager(List<Transformation_Order> transfOrders, List<Unloading_Order> unldOrders, GUI gui) {

        singleton = this;

        this.transfOrders = transfOrders;
        this.unldOrders = unldOrders;
        this.gui = gui;

        conn = new OPC_UA_Connection();

        try {
            subscriptionWo1 = ManagedSubscription.create(conn.getClient());
            subscriptionWi1 = ManagedSubscription.create(conn.getClient());
            subscriptionWo2 = ManagedSubscription.create(conn.getClient());
            subscriptionWi2 = ManagedSubscription.create(conn.getClient());
            subscriptionO1 = ManagedSubscription.create(conn.getClient());
            subscriptionO2 = ManagedSubscription.create(conn.getClient());
            subscriptionO3 = ManagedSubscription.create(conn.getClient());
            subscriptionStocks = ManagedSubscription.create(conn.getClient());

            subscriptionWo1.createDataItem(new NodeId(4, wo1EmptyNode));
            subscriptionWo2.createDataItem(new NodeId(4, wo2EmptyNode));
            subscriptionWi1.createDataItem(new NodeId(4, wi1OrderNode));
            subscriptionWi2.createDataItem(new NodeId(4, wi2OrderNode));
            subscriptionO1.createDataItem(new NodeId(4, O1OrderNode));
            subscriptionO2.createDataItem(new NodeId(4, O2OrderNode));
            subscriptionO3.createDataItem(new NodeId(4, O3OrderNode));
            subscriptionStocks.createDataItem(new NodeId(4, stockP1));
            subscriptionStocks.createDataItem(new NodeId(4, stockP2));
            subscriptionStocks.createDataItem(new NodeId(4, stockP3));
            subscriptionStocks.createDataItem(new NodeId(4, stockP4));
            subscriptionStocks.createDataItem(new NodeId(4, stockP5));
            subscriptionStocks.createDataItem(new NodeId(4, stockP6));
            subscriptionStocks.createDataItem(new NodeId(4, stockP7));
            subscriptionStocks.createDataItem(new NodeId(4, stockP8));
            subscriptionStocks.createDataItem(new NodeId(4, stockP9));
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
                for (ManagedDataItem item : dataItems) {
                    if(item.getNodeId().getIdentifier().equals(wi1OrderNode)){
                        /* deal with order completed */
                        String orderID = (String) dataValues.get(i).getValue().getValue();
                        if(!orderID.equals("null")){
                            System.out.println(orderID);
                            conn.setValue(wi1DoneNode,true);

                            if(orderID.contains("P") && !orderID.equals("P1") && !orderID.equals("P2")) break;

                            String[] str = orderID.split("_");
                            int mainid = Integer.parseInt(str[0]);
                            int id = Integer.parseInt(str[1]);
                            int subid = Integer.parseInt(str[2]);

                            synchronized (transfOrders){
                                Transformation_Order order = Transformation_Order.getOrderByMainID_ID_SubID(transfOrders,mainid,id,subid);
                                order.setStatus(Order.Status.COMPLETED);
                                order.setEndTime(Instant.now().getEpochSecond());
                                Database_Connection.updateTOrders(transfOrders);
                            }
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

        /* Avisa MES que Wi2 concluiu um caminho */
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

                            String[] str = orderID.split("_");
                            int mainid = Integer.parseInt(str[0]);
                            int id = Integer.parseInt(str[1]);
                            int subid = Integer.parseInt(str[2]);

                            synchronized (transfOrders){
                                Transformation_Order order = Transformation_Order.getOrderByMainID_ID_SubID(transfOrders,mainid,id,subid);
                                order.setStatus(Order.Status.COMPLETED);
                                order.setEndTime(Instant.now().getEpochSecond());
                                Database_Connection.updateTOrders(transfOrders);
                            }
                        }
                    }
                    i++;
                }
            }
        });

        /* Avisa MES que O1 concluiu um caminho */
        subscriptionO1.addChangeListener(new ManagedSubscription.ChangeListener() {
            @Override
            public void onDataReceived(List<ManagedDataItem> dataItems, List<DataValue> dataValues) {
                int i = 0;
                for (ManagedDataItem item : dataItems) {
                    if(item.getNodeId().getIdentifier().equals(O1OrderNode)){
                        /* deal with order completed */
                        String orderID = (String) dataValues.get(i).getValue().getValue();
                        if(!orderID.equals("null")){
                            System.out.println(orderID);
                            conn.setValue(O1DoneNode,true);

                            String[] str = orderID.split("_");
                            int mainid = Integer.parseInt(str[0]);
                            int id = Integer.parseInt(str[1]);
                            int subid = Integer.parseInt(str[2]);

                            synchronized (unldOrders){
                                Unloading_Order order = Unloading_Order.getOrderByMainID_ID_SubID(unldOrders,mainid,id,subid);
                                order.setStatus(Order.Status.COMPLETED);
                                incUnldQuant(1,Integer.parseInt(order.getBlockType().substring(1)),1);
                                Database_Connection.updateUOrders(unldOrders);
                            }
                        }
                    }
                    i++;
                }
            }
        });

        /* Avisa MES que O2 concluiu um caminho */
        subscriptionO2.addChangeListener(new ManagedSubscription.ChangeListener() {
            @Override
            public void onDataReceived(List<ManagedDataItem> dataItems, List<DataValue> dataValues) {
                int i = 0;
                for (ManagedDataItem item : dataItems) {
                    if(item.getNodeId().getIdentifier().equals(O2OrderNode)){
                        /* deal with order completed */
                        String orderID = (String) dataValues.get(i).getValue().getValue();
                        if(!orderID.equals("null")){
                            System.out.println(orderID);
                            conn.setValue(O2DoneNode,true);

                            String[] str = orderID.split("_");
                            int mainid = Integer.parseInt(str[0]);
                            int id = Integer.parseInt(str[1]);
                            int subid = Integer.parseInt(str[2]);

                            synchronized (unldOrders){
                                Unloading_Order order = Unloading_Order.getOrderByMainID_ID_SubID(unldOrders,mainid,id,subid);
                                order.setStatus(Order.Status.COMPLETED);
                                incUnldQuant(2,Integer.parseInt(order.getBlockType().substring(1)),1);
                                Database_Connection.updateUOrders(unldOrders);
                            }
                        }
                    }
                    i++;
                }
            }
        });

        /* Avisa MES que O3 concluiu um caminho */
        subscriptionO3.addChangeListener(new ManagedSubscription.ChangeListener() {
            @Override
            public void onDataReceived(List<ManagedDataItem> dataItems, List<DataValue> dataValues) {
                int i = 0;
                for (ManagedDataItem item : dataItems) {
                    if(item.getNodeId().getIdentifier().equals(O3OrderNode)){
                        /* deal with order completed */
                        String orderID = (String) dataValues.get(i).getValue().getValue();
                        if(!orderID.equals("null")){
                            System.out.println(orderID);
                            conn.setValue(O3DoneNode,true);

                            String[] str = orderID.split("_");
                            int mainid = Integer.parseInt(str[0]);
                            int id = Integer.parseInt(str[1]);
                            int subid = Integer.parseInt(str[2]);

                            synchronized (unldOrders){
                                Unloading_Order order = Unloading_Order.getOrderByMainID_ID_SubID(unldOrders,mainid,id,subid);
                                order.setStatus(Order.Status.COMPLETED);
                                incUnldQuant(3,Integer.parseInt(order.getBlockType().substring(1)),1);
                                Database_Connection.updateUOrders(unldOrders);
                            }
                        }
                    }
                    i++;
                }
            }
        });

        subscriptionStocks.addChangeListener(new ManagedSubscription.ChangeListener() {
            @Override
            public void onDataReceived(List<ManagedDataItem> dataItems, List<DataValue> dataValues) {
                updateStocks();
            }
        });
    }

    public void evalWo1() {

        boolean wo1State = (boolean) PLC_Manager.getInstance().conn.getValue(wo1EmptyNode);

        if (wo1State) {

            Unloading_Order unld = null;
            /* check prioritary unloading orders */

            synchronized (unldOrders) {
                for (Unloading_Order aux : unldOrders) {
                    if (aux.getStatus() == Order.Status.READY && aux.getPath().contains("Wo1") && aux.isPriority()) {
                        unld = aux;
                    }
                }
                if (unld != null) {
                    if(unld.getPath().contains("O1") && !((boolean) conn.getValue(O1Full)) ||
                            unld.getPath().contains("O2") && !((boolean) conn.getValue(O2Full)) ||
                                unld.getPath().contains("O3") && !((boolean) conn.getValue(O3Full))) {

                        unld.setStatus(Order.Status.RUNNING);

                        String path = unld.getPath().replace("Wo1:","") + "?P=" + unld.getBlockType().substring(1)
                                + "?ID=" + unld.getMainID() + "_" + unld.getID() + "_" + unld.getSubID();

                        System.out.println(path);

                        conn.setValue(wo1PieceNode, path);
                        Database_Connection.updateUOrders(unldOrders);
                    }
                }
            }

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
                        transf.setStartTime(Instant.now().getEpochSecond());

                        String path = transf.getPath().replace("Wo1:","") + "?P=" + transf.getInitBlockType().substring(1)
                                + "?ID=" + transf.getMainID() + "_" + transf.getID() + "_" + transf.getSubID();

                        System.out.println(path);

                        conn.setValue(wo1PieceNode, path);
                        Database_Connection.updateTOrders(transfOrders);
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
                            if(unld.getPath().contains("O1") && !((boolean) conn.getValue(O1Full)) ||
                                    unld.getPath().contains("O2") && !((boolean) conn.getValue(O2Full)) ||
                                        unld.getPath().contains("O3") && !((boolean) conn.getValue(O3Full))) {

                                unld.setStatus(Order.Status.RUNNING);

                                String path = unld.getPath().replace("Wo1:", "") + "?P=" + unld.getBlockType().substring(1)
                                        + "?ID=" + unld.getMainID() + "_" + unld.getID() + "_" + unld.getSubID();

                                System.out.println(path);

                                conn.setValue(wo1PieceNode, path);
                                Database_Connection.updateUOrders(unldOrders);
                            }
                        }
                    }
                }
            }
        }
    }

    public void evalWo2() {

        boolean wo2State = (boolean) PLC_Manager.getInstance().conn.getValue(wo2EmptyNode);

        if (wo2State) {
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
                    transf.setStartTime(Instant.now().getEpochSecond());

                    String path = transf.getPath().replace("Wo2:","") + "?P=" + transf.getInitBlockType().substring(1)
                            + "?ID=" + transf.getMainID() + "_" + transf.getID() + "_" + transf.getSubID();

                    System.out.println(path);

                    conn.setValue(wo2PieceNode, path);
                    Database_Connection.updateTOrders(transfOrders);
                }
            }

        }
    }

    public void updateStocks() {

        int P1 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP1)).intValue();
        int P2 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP2)).intValue();
        int P3 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP3)).intValue();
        int P4 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP4)).intValue();
        int P5 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP5)).intValue();
        int P6 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP6)).intValue();
        int P7 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP7)).intValue();
        int P8 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP8)).intValue();
        int P9 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP9)).intValue();

        Database_Connection.updateStocks(P1,P2,P3,P4,P5,P6,P7,P8,P9);
    }

    public void incMacQuant(int machine, int pieceType, int quant){
        Database_Connection.incMacQuant(machine,pieceType,quant);
        gui.machinedTableData.setMachinedParts(machine,pieceType,gui.machinedTableData.getMachinedParts(machine,pieceType)+quant);
    }

    public void incMacTime(int machine, int time){
        Database_Connection.incMacTime(machine,time);
        gui.machinedTableData.setMachinedTime(machine,gui.machinedTableData.getMachinedTime(machine)+time);
    }

    public void incUnldQuant(int roller, int pieceType, int quant){
        Database_Connection.incUnld(roller,pieceType,quant);
        gui.unloadTableData.setUnloadPart(roller,pieceType,gui.unloadTableData.getUnloadPart(roller,pieceType)+quant);
    }

}