package mutils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

public class TestReceiveutils {

    private BroadcastReceiver dynamicReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("wangfeideguangbojieshou")) {
                String msg = intent.getStringExtra("msg");
                String text = "";
                if (msg.startsWith("a")) {
                    text = DateUtils.getCurrentDate(DateUtils.dateFormatHMS) + ">>" + msg.substring(1);
                } else {
                    text = DateUtils.getCurrentDate(DateUtils.dateFormatHMS) + "<<" + msg.substring(1);
                }
            }
        }
    };

    public static void registerReceive(Context context) {
        IntentFilter filter_system = new IntentFilter();
        filter_system.addAction("wangfeideguangbojieshou");
        context.registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("wangfeideguangbojieshou")) {
                    String msg = intent.getStringExtra("msg");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            }
        }, filter_system);
    }

    public static void send(Context context, String msg) {
        Intent intent = new Intent();
        intent.setAction("wangfeideguangbojieshou");
        intent.putExtra("msg", msg);
        context.sendBroadcast(intent);
    }
}
