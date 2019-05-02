package com.sunxiaohang.root.ctimes;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ConnectionStateChangeReceiver extends BroadcastReceiver{
    private ConnectivityManager manager;
    private NetworkInfo networkInfo;
    private Dialog globalDialog;
    private boolean networkDisConnected = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        globalDialog = new Dialog(context);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            globalDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY));
        }else {
            globalDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(context))return;
        }
        manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = manager.getActiveNetworkInfo();
        globalDialog.setTitle(context.getResources().getString(R.string.notification));
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_notification,null,false);
        TextView textView = view.findViewById(R.id.notification_message);
        ImageView imageView = view.findViewById(R.id.notification_image);

        if(networkInfo != null && networkInfo.isConnected() && networkDisConnected){
            networkDisConnected = false;
            textView.setText(context.getResources().getString(R.string.networkReconnected));
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_signal_wifi_3_bar_black_24dp));
            globalDialog.setContentView(view);
        }else if(networkInfo == null
                || !networkInfo.isConnected()){
            networkDisConnected = true;
            textView.setText(context.getResources().getString(R.string.networkDisConnected));
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_signal_cellular_off_black_24dp));
            globalDialog.setContentView(view);
        }else return;
        globalDialog.show();
    }
}
