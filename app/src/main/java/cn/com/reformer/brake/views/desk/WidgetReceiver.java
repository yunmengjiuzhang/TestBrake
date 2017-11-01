package cn.com.reformer.brake.views.desk;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import cn.com.reformer.brake.utils.ToastUtils;

/**
 * Created by fei on 2016/12/6.
 */

public class WidgetReceiver extends AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        System.out.println("MyWidgetReceiver.onReceive()");
        ToastUtils.showToast("点击了!");
    }


    @Override
    /**
     * 添加第一个widget时 ，调用
     */
    public void onEnabled(Context context) {
        super.onEnabled(context);
        System.out.println("MyWidgetReceiver.onEnabled()");
        Intent intent = new Intent(context, UpdateWidgetService.class);
        context.startService(intent);
    }


    @Override
    /**
     * 删除最后一个widget时，调用
     */
    public void onDisabled(Context context) {
        super.onDisabled(context);
        System.out.println("MyWidgetReceiver.onDisabled()");
        Intent intent = new Intent(context, UpdateWidgetService.class);
        context.stopService(intent);
    }


    @Override
    /**
     * 删除任何一个widget时调用
     */
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        System.out.println("MyWidgetReceiver.onDeleted()");
    }


    @Override
    /**
     * 添加任何一个wdiget时，调用
     */
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        System.out.println("MyWidgetReceiver.onUpdate()");
    }
}
