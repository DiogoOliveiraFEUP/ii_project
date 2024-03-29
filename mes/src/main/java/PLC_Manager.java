import GUI.GUI;
import Order.*;
import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedDataItem;
import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedSubscription;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

import java.time.Instant;
import java.util.Arrays;
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

    String M1_Time = "|var|CODESYS Control Win V3 x64.Application.Stats.M1_Time";
    String M2_Time = "|var|CODESYS Control Win V3 x64.Application.Stats.M2_Time";
    String M3_Time = "|var|CODESYS Control Win V3 x64.Application.Stats.M3_Time";
    String M4_Time = "|var|CODESYS Control Win V3 x64.Application.Stats.M4_Time";
    String M5_Time = "|var|CODESYS Control Win V3 x64.Application.Stats.M5_Time";
    String M6_Time = "|var|CODESYS Control Win V3 x64.Application.Stats.M6_Time";
    String M7_Time = "|var|CODESYS Control Win V3 x64.Application.Stats.M7_Time";
    String M8_Time = "|var|CODESYS Control Win V3 x64.Application.Stats.M8_Time";
    ManagedSubscription subscriptionMxTimes;

    String M1_Pieces = "|var|CODESYS Control Win V3 x64.Application.Stats.M1_Pieces";
    String M2_Pieces = "|var|CODESYS Control Win V3 x64.Application.Stats.M2_Pieces";
    String M3_Pieces = "|var|CODESYS Control Win V3 x64.Application.Stats.M3_Pieces";
    String M4_Pieces = "|var|CODESYS Control Win V3 x64.Application.Stats.M4_Pieces";
    String M5_Pieces = "|var|CODESYS Control Win V3 x64.Application.Stats.M5_Pieces";
    String M6_Pieces = "|var|CODESYS Control Win V3 x64.Application.Stats.M6_Pieces";
    String M7_Pieces = "|var|CODESYS Control Win V3 x64.Application.Stats.M7_Pieces";
    String M8_Pieces = "|var|CODESYS Control Win V3 x64.Application.Stats.M8_Pieces";
    ManagedSubscription subscriptionMxPieces;

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
            subscriptionMxTimes = ManagedSubscription.create(conn.getClient());
            subscriptionMxPieces = ManagedSubscription.create(conn.getClient());

            subscriptionWo1.createDataItem(new NodeId(4, wo1EmptyNode));
            subscriptionWo1.createDataItem(new NodeId(4, O1Full));
            subscriptionWo1.createDataItem(new NodeId(4, O2Full));
            subscriptionWo1.createDataItem(new NodeId(4, O3Full));
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
            subscriptionMxTimes.createDataItem(new NodeId(4,M1_Time));
            subscriptionMxTimes.createDataItem(new NodeId(4,M2_Time));
            subscriptionMxTimes.createDataItem(new NodeId(4,M3_Time));
            subscriptionMxTimes.createDataItem(new NodeId(4,M4_Time));
            subscriptionMxTimes.createDataItem(new NodeId(4,M5_Time));
            subscriptionMxTimes.createDataItem(new NodeId(4,M6_Time));
            subscriptionMxTimes.createDataItem(new NodeId(4,M7_Time));
            subscriptionMxTimes.createDataItem(new NodeId(4,M8_Time));
            subscriptionMxPieces.createDataItem(new NodeId(4,M1_Pieces));
            subscriptionMxPieces.createDataItem(new NodeId(4,M2_Pieces));
            subscriptionMxPieces.createDataItem(new NodeId(4,M3_Pieces));
            subscriptionMxPieces.createDataItem(new NodeId(4,M4_Pieces));
            subscriptionMxPieces.createDataItem(new NodeId(4,M5_Pieces));
            subscriptionMxPieces.createDataItem(new NodeId(4,M6_Pieces));
            subscriptionMxPieces.createDataItem(new NodeId(4,M7_Pieces));
            subscriptionMxPieces.createDataItem(new NodeId(4,M8_Pieces));
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
                    if(item.getNodeId().getIdentifier().equals(O1Full)){
                        boolean o1state = (boolean) dataValues.get(i).getValue().getValue();
                        if(!o1state) {
                            boolean wo1State = (boolean) PLC_Manager.getInstance().conn.getValue(wo1EmptyNode);
                            if(wo1State){
                                evalWo1();
                            }
                        }
                    }
                    if(item.getNodeId().getIdentifier().equals(O2Full)){
                        boolean o2state = (boolean) dataValues.get(i).getValue().getValue();
                        if(!o2state) {
                            boolean wo1State = (boolean) PLC_Manager.getInstance().conn.getValue(wo1EmptyNode);
                            if(wo1State){
                                evalWo1();
                            }
                        }
                    }
                    if(item.getNodeId().getIdentifier().equals(O3Full)){
                        boolean o3state = (boolean) dataValues.get(i).getValue().getValue();
                        if(!o3state) {
                            boolean wo1State = (boolean) PLC_Manager.getInstance().conn.getValue(wo1EmptyNode);
                            if(wo1State){
                                evalWo1();
                            }
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
                            //System.out.println(orderID);
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

                            evalWo1();
                            evalWo2();
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
                        //System.out.println(orderID);
                        if(!orderID.equals("null")){
                            //System.out.println(orderID + ":Done");
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

                            evalWo1();
                            evalWo2();
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
                            //System.out.println(orderID);
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
                            //System.out.println(orderID);
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
                            //System.out.println(orderID);
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

        /* Atualiza Stocks */
        subscriptionStocks.addChangeListener(new ManagedSubscription.ChangeListener() {
            @Override
            public void onDataReceived(List<ManagedDataItem> dataItems, List<DataValue> dataValues) {
                updateStocks();
            }
        });

        /* Atualiza Machine Time Stats */
        subscriptionMxTimes.addChangeListener(new ManagedSubscription.ChangeListener() {
            @Override
            public void onDataReceived(List<ManagedDataItem> dataItems, List<DataValue> dataValues) {
                //System.out.println("here");
                int i = 0;
                for (ManagedDataItem item : dataItems) {
                    if(item.getNodeId().getIdentifier().equals(M1_Time)){
                        short value = (short)dataValues.get(i).getValue().getValue();
                        //System.out.println("1:"+value);
                        if(value>0){
                            incMacTime(1,value);
                            conn.setValue(M1_Time,(short)0);
                        }
                    }
                    if(item.getNodeId().getIdentifier().equals(M2_Time)){
                        short value = (short)dataValues.get(i).getValue().getValue();
                        //System.out.println("2:"+value);
                        if(value>0){
                            incMacTime(2,value);
                            conn.setValue(M2_Time,(short)0);
                        }
                    }
                    if(item.getNodeId().getIdentifier().equals(M3_Time)){
                        short value = (short)dataValues.get(i).getValue().getValue();
                        //System.out.println("3:"+value);
                        if(value>0){
                            incMacTime(3,value);
                            conn.setValue(M3_Time,(short)0);
                        }
                    }
                    if(item.getNodeId().getIdentifier().equals(M4_Time)){
                        short value = (short)dataValues.get(i).getValue().getValue();
                        //System.out.println("4:"+value);
                        if(value>0){
                            incMacTime(4,value);
                            conn.setValue(M4_Time,(short)0);
                        }
                    }
                    if(item.getNodeId().getIdentifier().equals(M5_Time)){
                        short value = (short)dataValues.get(i).getValue().getValue();
                        //System.out.println("5:"+value);
                        if(value>0){
                            incMacTime(5,value);
                            conn.setValue(M5_Time,(short)0);
                        }
                    }
                    if(item.getNodeId().getIdentifier().equals(M6_Time)){
                        short value = (short)dataValues.get(i).getValue().getValue();
                        //System.out.println("6:"+value);
                        if(value>0){
                            incMacTime(6,value);
                            conn.setValue(M6_Time,(short)0);
                        }
                    }
                    if(item.getNodeId().getIdentifier().equals(M7_Time)){
                        short value = (short)dataValues.get(i).getValue().getValue();
                        //System.out.println("7:"+value);
                        if(value>0){
                            incMacTime(7,value);
                            conn.setValue(M7_Time,(short)0);
                        }
                    }
                    if(item.getNodeId().getIdentifier().equals(M8_Time)){
                        short value = (short)dataValues.get(i).getValue().getValue();
                        //System.out.println("8:"+value);
                        if(value>0){
                            incMacTime(8,value);
                            conn.setValue(M8_Time,(short)0);
                        }
                    }
                    i++;
                }
            }
        });

        /* Atualiza Machine Time Stats */
        subscriptionMxPieces.addChangeListener(new ManagedSubscription.ChangeListener() {
            @Override
            public void onDataReceived(List<ManagedDataItem> dataItems, List<DataValue> dataValues) {
                int i = 0;
                for (ManagedDataItem item : dataItems) {
                    if(item.getNodeId().getIdentifier().equals(M1_Pieces)){
                        String aux = dataValues.get(i).getValue().toString();
                        String aux1 = aux.replace("Variant{value=[","").replace(" ","").replace("]}","");
                        String[] aux2 = aux1.split(",");

                        short[] values = new short[6];
                        values[0] = Short.parseShort(aux2[0]);
                        values[1] = Short.parseShort(aux2[1]);
                        values[2] = Short.parseShort(aux2[2]);
                        values[3] = Short.parseShort(aux2[3]);
                        values[4] = Short.parseShort(aux2[4]);
                        values[5] = Short.parseShort(aux2[5]);

                        //System.out.println(Arrays.toString(values));

                        for(int j = 0; j < 6; j++){
                            if(values[j] > 0){
                                incMacQuant(1,j+1,values[j]);
                                values[j] = 0;
                                conn.setValue(M1_Pieces,values);
                            }
                        }
                    }
                    if(item.getNodeId().getIdentifier().equals(M2_Pieces)){

                        String aux = dataValues.get(i).getValue().toString();
                        String aux1 = aux.replace("Variant{value=[","").replace(" ","").replace("]}","");
                        String[] aux2 = aux1.split(",");

                        short[] values = new short[6];
                        values[0] = Short.parseShort(aux2[0]);
                        values[1] = Short.parseShort(aux2[1]);
                        values[2] = Short.parseShort(aux2[2]);
                        values[3] = Short.parseShort(aux2[3]);
                        values[4] = Short.parseShort(aux2[4]);
                        values[5] = Short.parseShort(aux2[5]);

                        //System.out.println(Arrays.toString(values));

                        for(int j = 0; j < 6; j++){
                            if(values[j] > 0){
                                incMacQuant(2,j+1,values[j]);
                                values[j] = 0;
                                conn.setValue(M2_Pieces,values);
                            }
                        }
                    }
                    if(item.getNodeId().getIdentifier().equals(M3_Pieces)){

                        String aux = dataValues.get(i).getValue().toString();
                        String aux1 = aux.replace("Variant{value=[","").replace(" ","").replace("]}","");
                        String[] aux2 = aux1.split(",");

                        short[] values = new short[6];
                        values[0] = Short.parseShort(aux2[0]);
                        values[1] = Short.parseShort(aux2[1]);
                        values[2] = Short.parseShort(aux2[2]);
                        values[3] = Short.parseShort(aux2[3]);
                        values[4] = Short.parseShort(aux2[4]);
                        values[5] = Short.parseShort(aux2[5]);

                        //System.out.println(Arrays.toString(values));

                        for(int j = 0; j < 6; j++){
                            if(values[j] > 0){
                                incMacQuant(3,j+1,values[j]);
                                values[j] = 0;
                                conn.setValue(M3_Pieces,values);
                            }
                        }
                    }
                    if(item.getNodeId().getIdentifier().equals(M4_Pieces)){

                        String aux = dataValues.get(i).getValue().toString();
                        String aux1 = aux.replace("Variant{value=[","").replace(" ","").replace("]}","");
                        String[] aux2 = aux1.split(",");

                        short[] values = new short[6];
                        values[0] = Short.parseShort(aux2[0]);
                        values[1] = Short.parseShort(aux2[1]);
                        values[2] = Short.parseShort(aux2[2]);
                        values[3] = Short.parseShort(aux2[3]);
                        values[4] = Short.parseShort(aux2[4]);
                        values[5] = Short.parseShort(aux2[5]);

                        //System.out.println(Arrays.toString(values));

                        for(int j = 0; j < 6; j++){
                            if(values[j] > 0){
                                incMacQuant(4,j+1,values[j]);
                                values[j] = 0;
                                conn.setValue(M4_Pieces,values);
                            }
                        }
                    }
                    if(item.getNodeId().getIdentifier().equals(M5_Pieces)){

                        String aux = dataValues.get(i).getValue().toString();
                        String aux1 = aux.replace("Variant{value=[","").replace(" ","").replace("]}","");
                        String[] aux2 = aux1.split(",");

                        short[] values = new short[6];
                        values[0] = Short.parseShort(aux2[0]);
                        values[1] = Short.parseShort(aux2[1]);
                        values[2] = Short.parseShort(aux2[2]);
                        values[3] = Short.parseShort(aux2[3]);
                        values[4] = Short.parseShort(aux2[4]);
                        values[5] = Short.parseShort(aux2[5]);

                        //System.out.println(Arrays.toString(values));

                        for(int j = 0; j < 6; j++){
                            if(values[j] > 0){
                                incMacQuant(5,j+1,values[j]);
                                values[j] = 0;
                                conn.setValue(M5_Pieces,values);
                            }
                        }
                    }
                    if(item.getNodeId().getIdentifier().equals(M6_Pieces)){

                        String aux = dataValues.get(i).getValue().toString();
                        String aux1 = aux.replace("Variant{value=[","").replace(" ","").replace("]}","");
                        String[] aux2 = aux1.split(",");

                        short[] values = new short[6];
                        values[0] = Short.parseShort(aux2[0]);
                        values[1] = Short.parseShort(aux2[1]);
                        values[2] = Short.parseShort(aux2[2]);
                        values[3] = Short.parseShort(aux2[3]);
                        values[4] = Short.parseShort(aux2[4]);
                        values[5] = Short.parseShort(aux2[5]);

                        //System.out.println(Arrays.toString(values));

                        for(int j = 0; j < 6; j++){
                            if(values[j] > 0){
                                incMacQuant(6,j+1,values[j]);
                                values[j] = 0;
                                conn.setValue(M6_Pieces,values);
                            }
                        }
                    }
                    if(item.getNodeId().getIdentifier().equals(M7_Pieces)){

                        String aux = dataValues.get(i).getValue().toString();
                        String aux1 = aux.replace("Variant{value=[","").replace(" ","").replace("]}","");
                        String[] aux2 = aux1.split(",");

                        short[] values = new short[6];
                        values[0] = Short.parseShort(aux2[0]);
                        values[1] = Short.parseShort(aux2[1]);
                        values[2] = Short.parseShort(aux2[2]);
                        values[3] = Short.parseShort(aux2[3]);
                        values[4] = Short.parseShort(aux2[4]);
                        values[5] = Short.parseShort(aux2[5]);

                        //System.out.println(Arrays.toString(values));

                        for(int j = 0; j < 6; j++){
                            if(values[j] > 0){
                                incMacQuant(7,j+1,values[j]);
                                values[j] = 0;
                                conn.setValue(M7_Pieces,values);
                            }
                        }
                    }
                    if(item.getNodeId().getIdentifier().equals(M8_Pieces)){

                        String aux = dataValues.get(i).getValue().toString();
                        String aux1 = aux.replace("Variant{value=[","").replace(" ","").replace("]}","");
                        String[] aux2 = aux1.split(",");

                        short[] values = new short[6];
                        values[0] = Short.parseShort(aux2[0]);
                        values[1] = Short.parseShort(aux2[1]);
                        values[2] = Short.parseShort(aux2[2]);
                        values[3] = Short.parseShort(aux2[3]);
                        values[4] = Short.parseShort(aux2[4]);
                        values[5] = Short.parseShort(aux2[5]);

                        //System.out.println(Arrays.toString(values));

                        for(int j = 0; j < 6; j++){
                            if(values[j] > 0){
                                incMacQuant(8,j+1,values[j]);
                                values[j] = 0;
                                conn.setValue(M8_Pieces,values);
                            }
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

            synchronized (unldOrders) {
                for (Unloading_Order aux : unldOrders) {
                    if (aux.getStatus() == Order.Status.READY && aux.getPath().contains("Wo1") && aux.isPriority()) {
                        if(hasStock(Integer.parseInt(aux.getBlockType().substring(1)))) {
                            unld = aux;
                            break;
                        }
                    }
                }
                if (unld != null) {
                    if(unld.getPath().contains("O1") && !((boolean) conn.getValue(O1Full)) ||
                            unld.getPath().contains("O2") && !((boolean) conn.getValue(O2Full)) ||
                                unld.getPath().contains("O3") && !((boolean) conn.getValue(O3Full))) {

                        unld.setStatus(Order.Status.RUNNING);

                        String path = unld.getPath().replace("Wo1:","") + "?P=" + unld.getBlockType().substring(1)
                                + "?ID=" + unld.getMainID() + "_" + unld.getID() + "_" + unld.getSubID();

                        //System.out.println(path);

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
                            if(hasStock(Integer.parseInt(aux.getInitBlockType().substring(1)))) {
                                transf = aux;
                                break;
                            }
                        }
                    }
                    if (transf != null) {
                        transf.setStatus(Order.Status.RUNNING);
                        transf.setStartTime(Instant.now().getEpochSecond());

                        String path = transf.getPath().replace("Wo1:","") + "?P=" + transf.getInitBlockType().substring(1)
                                + "?ID=" + transf.getMainID() + "_" + transf.getID() + "_" + transf.getSubID();

                        //System.out.println(path);

                        conn.setValue(wo1PieceNode, path);
                        Database_Connection.updateTOrders(transfOrders);
                    }
                }

                if (transf == null) {

                    /* check for unloading orders */
                    synchronized (unldOrders) {
                        for (Unloading_Order aux : unldOrders) {
                            if (aux.getStatus() == Order.Status.READY && aux.getPath().contains("Wo1")) {
                                if (hasStock(Integer.parseInt(aux.getBlockType().substring(1)))) {
                                    unld = aux;
                                    break;
                                }
                            }
                        }
                    }
                    if (unld != null) {
                        if(unld.getPath().contains("O1") && !((boolean) conn.getValue(O1Full)) ||
                                unld.getPath().contains("O2") && !((boolean) conn.getValue(O2Full)) ||
                                unld.getPath().contains("O3") && !((boolean) conn.getValue(O3Full))) {

                            unld.setStatus(Order.Status.RUNNING);

                            String path = unld.getPath().replace("Wo1:", "") + "?P=" + unld.getBlockType().substring(1)
                                    + "?ID=" + unld.getMainID() + "_" + unld.getID() + "_" + unld.getSubID();

                            //System.out.println(path);

                            conn.setValue(wo1PieceNode, path);
                            Database_Connection.updateUOrders(unldOrders);
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
                        if(hasStock(Integer.parseInt(aux.getInitBlockType().substring(1)))) {
                            transf = aux;
                            break;
                        }
                    }
                }
                if (transf != null) {
                    transf.setStatus(Order.Status.RUNNING);
                    transf.setStartTime(Instant.now().getEpochSecond());

                    String path = transf.getPath().replace("Wo2:","") + "?P=" + transf.getInitBlockType().substring(1)
                            + "?ID=" + transf.getMainID() + "_" + transf.getID() + "_" + transf.getSubID();

                    //System.out.println(path);

                    conn.setValue(wo2PieceNode, path);
                    Database_Connection.updateTOrders(transfOrders);
                }
            }

        }
    }

    public boolean hasStock(int piece){
        if(piece == 1){
            int P1 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP1)).intValue();
            if(P1 > 0) return true;
        }
        else if(piece == 2){
            int P2 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP2)).intValue();
            if(P2 > 0) return true;
        }
        else if(piece == 3){
            int P3 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP3)).intValue();
            if(P3 > 0) return true;
        }
        else if(piece == 4){
            int P4 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP4)).intValue();
            if(P4 > 0) return true;
        }
        else if(piece == 5){
            int P5 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP5)).intValue();
            if(P5 > 0) return true;
        }
        else if(piece == 6){
            int P6 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP6)).intValue();
            if(P6 > 0) return true;
        }
        else if(piece == 7){
            int P7 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP7)).intValue();
            if(P7 > 0) return true;
        }
        else if(piece == 8){
            int P8 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP8)).intValue();
            if(P8 > 0) return true;
        }
        else if(piece == 9){
            int P9 = ((Short) PLC_Manager.getInstance().conn.getValue(stockP9)).intValue();
            if(P9 > 0) return true;
        }
        return false;
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