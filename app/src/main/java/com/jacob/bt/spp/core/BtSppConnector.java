package com.jacob.bt.spp.core;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.jacob.bt.spp.impl.BtConnectCallBack;
import com.jacob.bt.spp.utils.LogUtils;

import java.io.IOException;
import java.util.UUID;

/**
 * Package : com.jacob.bt.spp.core
 * Author : jacob
 * Date : 15-6-5
 * Description : 这个类是用来xxx
 */
public class BtSppConnector {
    public static final String TAG = "BtSppConnector";

    private final String ANDROID_BT_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private ConnectDeviceThread mConnectThread;
    private BtConnectCallBack mBtConnectCallBack;
    private ConnectState mConnectState = ConnectState.STATE_DISCONNECTED;

    public static final int MSG_CONNECT_SUCCESS = 0x100;
    public static final int MSG_CONNECT_FAIL = 0x101;

    private static final String PARAMS_STRING = "params_string";

    public BtSppConnector() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_CONNECT_SUCCESS:
                    if (mBtConnectCallBack != null) {
                        mBtConnectCallBack.deviceConnected();
                    }
                    break;
                case MSG_CONNECT_FAIL:
                    if (mBtConnectCallBack != null) {
                        String reason = msg.getData().getString(PARAMS_STRING);
                        mBtConnectCallBack.deviceDisconnected(reason);
                    }
                    break;

            }

        }
    };

    public void connectDevice(BluetoothDevice bluetoothDevice, BtConnectCallBack btConnectCallBack) {
        if (mConnectState != ConnectState.STATE_DISCONNECTED) {
            LogUtils.LOGE(TAG, "bluetooth is not disconnect ,so you can not connect ,please disconnect device first!");
        }

        mBtConnectCallBack = btConnectCallBack;
        mBluetoothDevice = bluetoothDevice;
        mConnectState = ConnectState.STATE_CONNECTING;

        disconnect();

        mBluetoothAdapter.cancelDiscovery();
        mConnectThread = new ConnectDeviceThread(bluetoothDevice);
        mConnectThread.start();

    }


    /**
     * 断开蓝牙连接
     */
    public void disconnect() {
        mConnectState = ConnectState.STATE_DISCONNECTED;
        if (mConnectThread != null) {
            mConnectThread.cancel();
        }
        if (mBtConnectCallBack != null) {
            mBtConnectCallBack.deviceDisconnected("");
        }
    }


    /**
     * 这个线程是用于连接设备而开启的子线程
     */
    private class ConnectDeviceThread extends Thread {

        public ConnectDeviceThread(BluetoothDevice device) {
            try {
                if (mBluetoothSocket != null) {
                    mBluetoothSocket.close();
                }

                mBluetoothSocket = device.createRfcommSocketToServiceRecord(UUID.fromString(ANDROID_BT_UUID));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                mBluetoothSocket.connect();
                mConnectState = ConnectState.STATE_CONNECTED;
                sendConnectSuccessMessage();
            } catch (IOException e) {
                cancel();
                mConnectState = ConnectState.STATE_DISCONNECTED;
                sendConnectFailMessage(e.getMessage());
            }

        }


        public void cancel() {
            try {
                mBluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 发送连接设备成功的消息
     */
    private void sendConnectSuccessMessage() {
        Message msg = Message.obtain();
        msg.what = MSG_CONNECT_SUCCESS;
        mHandler.sendMessage(msg);
    }

    /**
     * 发送连接设备成功的消息
     */
    private void sendConnectFailMessage(String message) {
        Message msg = Message.obtain();
        msg.what = MSG_CONNECT_FAIL;
        Bundle bundle = new Bundle();
        bundle.putString(PARAMS_STRING, message);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }
}
