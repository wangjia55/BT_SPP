package com.jacob.bt.spp.impl;

import android.bluetooth.BluetoothDevice;

/**
 * Package : com.jacob.bt.spp.core
 * Author : jacob
 * Date : 15-6-5
 * Description : 这个类是用来xxx
 */
public interface IBtService {
    void connect(String address, BtConnectCallBack btConnectCallBack) throws IllegalArgumentException;

    void connect(BluetoothDevice device, BtConnectCallBack btConnectCallBack);

    void disconnect();

    void writeData(byte[] data,BtTransferDataCallBack btTransferDataCallBack);
}
