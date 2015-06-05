package com.jacob.bt.spp.impl;

/**
 * Package : com.jacob.bt.spp.core
 * Author : jacob
 * Date : 15-6-5
 * Description : 这个类是用来xxx
 */
public interface BtReadDataCallBack {

    void readData(byte[] data);

    void readError(String reason);
}
