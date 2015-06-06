package com.jacob.bt.spp.core;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.jacob.bt.spp.exception.BluetoothInitException;
import com.jacob.bt.spp.impl.BtConnectCallBack;
import com.jacob.bt.spp.impl.BtReadDataCallBack;
import com.jacob.bt.spp.impl.IBtService;

/**
 * Package : com.jacob.bt.spp.core
 * Author : jacob
 * Date : 15-6-5
 * Description : 这个类是用来管理所有的蓝牙相关的操作
 */
public class BtManager implements IBtService {

    private static BtManager sInstance;
    private BluetoothAdapter mBluetoothAdapter;

    private BtSppConnector mBtSppConnector = new BtSppConnector();

    private BtManager() {
    }


    public static BtManager getInstance() {
        if (sInstance == null) {
            sInstance = new BtManager();
        }
        return sInstance;
    }


    /**
     * 初始化，检查蓝牙是否可用
     */
    public void init() throws BluetoothInitException {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            throw new BluetoothInitException("设备不支持蓝牙 ！");
        }
    }

    @Override
    public void connect(String address,BtConnectCallBack btConnectCallBack) throws IllegalArgumentException {
        if (address == null || address.length() == 0) {
            throw new IllegalArgumentException("MAC地址不能为空！");
        }
        if (mBluetoothAdapter != null) {
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
            connect(device,btConnectCallBack);
        }
    }

    @Override
    public void connect(BluetoothDevice device,BtConnectCallBack btConnectCallBack) {
        mBtSppConnector.connectDevice(device,btConnectCallBack);
    }

    @Override
    public void disconnect() {
        mBtSppConnector.disconnect();
    }

    @Override
    public void writeData(byte[] data) {

    }

    @Override
    public void readData(BtReadDataCallBack btReadDataCallBack) {

    }

}
