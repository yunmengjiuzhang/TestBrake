package cn.com.reformer.brake;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.KeyEvent;

import java.util.ArrayList;

import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.utils.ToastUtils;
import cn.com.reformer.brake.vh.ScanVH;
import cn.com.reformer.ble.BleBean;
import cn.com.reformer.ble.OnBleListListener;

public class ScanActivity extends BaseActivity {
    private ScanVH scanVH;

    @Override
    protected void initViews() {
        mSwipeBackLayout.setEnableGesture(false);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_scan);
        scanVH = new ScanVH(this);
        viewDataBinding.setVariable(BR.scan, scanVH);
        BleUtils.init();
        BleUtils.setScanListener(new OnBleListListener() {
            @Override
            public void OnNewBleBean(BleBean bean) {
                scanVH.OnNewBleBean(bean);
            }

            @Override
            public void OnBleDevList(ArrayList<BleBean> mScans) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanVH.refresh();
        hideLoadingDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();
        BleUtils.scanStop();
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        BaseApp.mBle.close();
//        killAll();
//    }

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
