package cn.com.reformer.ble;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;


public class Presenter {
    private final Ble mGattCallback;
    protected ArrayList<BleBean> mWhitelist;//白名单
    protected ArrayList<BleBean> mScans = new ArrayList<BleBean>();//扫描ble结果
    protected OnStateListener mOnStateListener;
    protected OnBleListListener mOnBleDevListListener;
    protected OnDatasListener mOnDatasListener;//接受数据的监听

    public Presenter(Context ctx) {
        mGattCallback = new Ble(ctx);
        mGattCallback.setOnGattListener(new Ble.OnBleListener() {
            @Override
            public void onConnectStatus(final int status) {
                if (status == 0) {
                    mHandler.removeMessages(1);
                } else {
                    if (mOnStateListener != null)
                        mOnStateListener.state(211);
                    mHandler.sendEmptyMessageDelayed(1, 10000);
                }
            }

            @Override
            public void onDescriptorWrite() {
                if (mOnStateListener != null)
                    mOnStateListener.state(203);//Connected to GATT server.
            }

            @Override
            public void onCharacteristicChanged(byte[] value) {
                if (mOnDatasListener != null)
                    mOnDatasListener.OnDatas(value);
            }

            @Override
            public void onScanResult(String name, String address, int rssi, byte[] mac) {
//        if (Utils.containBytes(mac, mWhitelist)) {//第二层筛选
                if (true) {//第二层筛选
                    BleBean bean = new BleBean(name, getNameServer(mac), address, rssi, mac);
                    if (!Utils.containBytes(mac, mScans)) {//是否为扫描记录列表中的设备
                        mScans.add(bean);
                        if (mOnBleDevListListener != null) {
                            mOnBleDevListListener.OnNewBleBean(bean);
                            mOnBleDevListListener.OnBleDevList(mScans);
                        }
                    }
                }
            }
        });
    }

    public void setOnBleListListener(OnBleListListener mOnBleDevListListener) {
        this.mOnBleDevListListener = mOnBleDevListListener;
    }

    public void setOnStateListener(OnStateListener mOnStateListener) {
        this.mOnStateListener = mOnStateListener;
    }

    public void setOnDatasListener(OnDatasListener ops) {
        this.mOnDatasListener = ops;
    }

    public String getNameServer(byte[] mac) {
        BleBean bean = Utils.bytes1Bean(mac, mWhitelist);
        if (bean == null) {
            return null;
        } else {
            return bean.nameServer;
        }
    }

    public boolean startScan(ArrayList<BleBean> bleBeans) {
        this.mWhitelist = bleBeans;
        close();
        mScans.clear();//清空扫描记录列表
        return mGattCallback.scanStart();
    }

    public void stopScan() {
        mGattCallback.scanStop();
    }

    protected void close() {
        mGattCallback.close();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (mOnStateListener != null)
                        mOnStateListener.state(212);//Connected to GATT server.
                    break;
            }
        }
    };

    public boolean writeChar(byte[] bytes) {//写特征值
        return mGattCallback.writeChar(bytes);
    }

    public int connect(String address) {
        return mGattCallback.connect(address);
    }
}
