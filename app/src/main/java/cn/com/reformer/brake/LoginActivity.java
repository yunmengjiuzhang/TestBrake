package cn.com.reformer.brake;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.KeyEvent;

import com.gjiazhe.panoramaimageview.GyroscopeObserver;

import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.utils.ToastUtils;
import cn.com.reformer.brake.vh.LoginVH;

public class LoginActivity extends BaseActivity {

    private GyroscopeObserver gyroscopeObserver;

    @Override
    protected void initViews() {
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        LoginVH loginVH = new LoginVH(this);
        viewDataBinding.setVariable(BR.login, loginVH);
        mSwipeBackLayout.setEnableGesture(false);
        gyroscopeObserver = new GyroscopeObserver();
        gyroscopeObserver.setMaxRotateRadian(Math.PI / 9);
        loginVH.gyroscopeObserver.set(gyroscopeObserver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gyroscopeObserver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gyroscopeObserver.unregister();
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 1000) {
                    ToastUtils.showToast("双击退出程序!");
                    firstTime = secondTime;
                    return true;
                } else {
                    BleUtils.close();
                    killAll();
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

}
