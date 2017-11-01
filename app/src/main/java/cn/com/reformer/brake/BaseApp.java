package cn.com.reformer.brake;

import android.app.Application;
import android.content.Context;

import mutils.CrashHandler;

public class BaseApp extends Application {
    private static BaseApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        CrashHandler.getInstance().init(this);//初始化崩溃工具
    }

    public static Context getInstance() {
        return instance;
    }
}
