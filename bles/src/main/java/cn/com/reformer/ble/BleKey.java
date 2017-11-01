package cn.com.reformer.ble;

import android.content.Context;

import java.util.ArrayList;

public class BleKey {
    private Presenter mBle;

    public BleKey(Context mCtx) {
        mBle = new Presenter(mCtx);
    }

    public void setOnBleDevListListener(OnBleListListener oblc) {
        mBle.setOnBleListListener(oblc);
    }

    public void setOnStateListener(OnStateListener ocl) {
        mBle.setOnStateListener(ocl);
    }

    public void setOnDatasListener(OnDatasListener ops) {
        mBle.setOnDatasListener(ops);
    }

    //扫描开始
    public boolean scanStart(ArrayList<BleBean> bleBeans) {
        return mBle.startScan(bleBeans);
    }

    //关闭扫描
    public void scanStop() {
        mBle.stopScan();
    }

    public int connect(String address) {
        if (address.equals("")) {
            return 101;//mac不再搜索列表
        }
        return mBle.connect(address);//开始通信
    }

    public boolean writeChar(byte[] cmd) {
        return mBle.writeChar(cmd);
    }

    public void closeBle() {
        mBle.close();
    }
}
