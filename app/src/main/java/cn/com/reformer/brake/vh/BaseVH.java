package cn.com.reformer.brake.vh;

import android.view.View;

import cn.com.reformer.brake.BaseActivity;

public class BaseVH {

    protected BaseActivity mCtx;

    public BaseVH(BaseActivity ctx) {
        mCtx = ctx;
    }

    public void onBack(View view) {
        mCtx.onBackPressed();
    }

    public void finish() {
        mCtx.finish();
    }
}
