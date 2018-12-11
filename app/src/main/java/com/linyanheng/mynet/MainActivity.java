package com.linyanheng.mynet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ConnectivityManager cmgr;
    WifiManager wifi;
    String mssid;
    MyNetworkReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cmgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifi.getConnectionInfo();
        mssid = wifiInfo.getBSSID();
        TextView textView1 = (TextView)findViewById(R.id.textView1);
        textView1.setText(""+wifiInfo.toString());
        receiver = new MyNetworkReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver,filter);


    }

    @Override
    public void finish() {
        unregisterReceiver(receiver);
        super.finish();
    }

    private boolean isConnectNetwork(){
        NetworkInfo info = cmgr.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    public void test1(View v)
    {
        Log.v("YH","isConnected "+isConnectNetwork());
    }


    public boolean isConnectWIFI()
    {
      NetworkInfo info =  cmgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
      return info.isConnected();
    }

    public void test2(View view) {
        Log.v("YH","isConnected "+isConnectWIFI());

    }
    private class MyNetworkReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("YH","wifi "+isConnectWIFI());

        }
    }
}
