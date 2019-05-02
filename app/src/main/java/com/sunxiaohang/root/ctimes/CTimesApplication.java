package com.sunxiaohang.root.ctimes;

import android.app.Application;
import android.content.IntentFilter;

public class CTimesApplication extends Application{
    private IntentFilter intentFilter;
    private ConnectionStateChangeReceiver receiver;
    @Override
    public void onCreate() {
        super.onCreate();
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        receiver = new ConnectionStateChangeReceiver();
        registerReceiver(receiver,intentFilter);
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.out.println("TAG:"+"low memory occur! please check your application!");
    }
}
