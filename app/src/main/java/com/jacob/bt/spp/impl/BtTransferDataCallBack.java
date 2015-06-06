package com.jacob.bt.spp.impl;

/**
 * Package : com.jacob.bt.spp.core
 * Author : jacob
 * Date : 15-6-5
 * Description : 这个类是用来xxx
 */
public interface BtTransferDataCallBack {
    void sendData(byte[] data);

    void readData(byte[] data);

    void transDataError(String reason);
}
