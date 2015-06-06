package com.jacob.bt.spp;

import android.app.Application;
import android.widget.Toast;

import com.jacob.bt.spp.core.BtManager;
import com.jacob.bt.spp.exception.BtInitException;

/**
 * Package : com.jacob.bt.spp
 * Author : jacob
 * Date : 15-6-6
 * Description : 这个类是用来xxx
 */
public class BtSppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            BtManager.getInstance().init();
        } catch (BtInitException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
