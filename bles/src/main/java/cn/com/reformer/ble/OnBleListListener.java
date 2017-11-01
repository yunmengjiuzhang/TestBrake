package cn.com.reformer.ble;

import java.util.ArrayList;

/**
 * Created by fei on 2017/1/11.
 * 搜索到的ble列表改变监听
 */
public interface OnBleListListener {
    void OnNewBleBean(BleBean bean);

    void OnBleDevList(ArrayList<BleBean> mScans);
}
