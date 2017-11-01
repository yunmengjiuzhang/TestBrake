package cn.com.reformer.brake.views.desk;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 更新widget的服务
 * @author Administrator
 *
 */
public class UpdateWidgetService extends Service {

    /**
     * 定时器
     */
    private Timer timer;
    private AppWidgetManager widgetManager;
    protected Context ctx;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 点击widget中的清理按钮时，发出的广播
     * @author Administrator
     *
     */
    private class UpdateWidgetClearReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // System.out.println("点击widget中的清理按钮时，发出的广播");

            // 更新控件
            updateWidget();
        }

    }

    private UpdateWidgetClearReceiver clearReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

        ctx = this;

        clearReceiver = new UpdateWidgetClearReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction("zz.itcast.mobilesafez16.UpdateWidget");

        registerReceiver(clearReceiver, filter);


        /**
         * 定时更新桌面widget
         * 定时更新的实现方式：
         * 一：开子线程，while 循环 + sleep 休眠
         * 二：Timer 定时器
         * 三：使用手机中的闹钟服务  AlarmManager
         * 四：使用 handler (个人最爱)
         */

        timer = new Timer();

        widgetManager = AppWidgetManager.getInstance(this);

        // 要执行的定时任务
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
//				System.out.println("定时任务=============");
                updateWidget();
            }
        };
		/*
		 * 参数一： 定时任务
		 * 参数二： 开始执行时的间隔时间
		 * 参数三： 二次执行之间的间隔时间
		 */
        timer.scheduleAtFixedRate(task, 0, 10000); // 立刻执行，每隔10秒执行一次
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        // 取消定时任务
        timer.cancel();

        unregisterReceiver(clearReceiver);

    }


    private void updateWidget() {
        /**
         * ComponentName 其实就是对四大组件的一个包装类，方便在应用中传输
         */
//        ComponentName provider = new ComponentName(UpdateWidgetService.this, WidgetReceiver.class);
//        // 远程的，远端的view
//        RemoteViews views = new RemoteViews(getPackageName(), R.layout.activity_main_ble);
//        Intent intent = new Intent("zz.itcast.mobilesafez16.UpdateWidget");
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, 88, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        // 告诉luncher 当点击ID为R.id.btn_clear 的view 时，发出广播
//        views.setOnClickPendingIntent(R.id.iv_debug, pendingIntent);

//        // 将id 为 R.id.process_memory 的TextView 设置内容：
//        views.setTextViewText(R.id.process_memory, "可用内存:"+avMemStr);



		/*
		 * 更新桌面widget控件
		 * 参数一： 是指，更新谁？
		 * 参数二： 是指，更新成什么样子
		 */
//        widgetManager.updateAppWidget(provider, views);
    }


}
