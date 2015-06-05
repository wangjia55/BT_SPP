package com.jacob.bt.spp.impl;

import java.util.UUID;

/**
 * Package : com.jacob.bt.spp.core
 * Author : jacob
 * Date : 15-6-5
 * Description : 这个类是用来xxx
 */
public interface IBtService {
    void connect(UUID uuid);

    void writeData(byte[] data);

    void readData(BtReadDataCallBack btReadDataCallBack);
}
