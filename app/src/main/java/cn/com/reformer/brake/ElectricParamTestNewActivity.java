package cn.com.reformer.brake;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.vh.ElectricParamTestNewVH;
import mutils.ThreadUtils;

public class ElectricParamTestNewActivity extends BaseActivity {

    private ElectricParamTestNewVH electricParamTestNewVH;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_electric_param_test_new);
        electricParamTestNewVH = new ElectricParamTestNewVH(this);
        viewDataBinding.setVariable(BR.electnew, electricParamTestNewVH);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDatas();
    }

    private int time = 0;

    public void refreshDatas() {
        time = 0;
        electricParamTestNewVH.isRefresh.set(true);
        BleUtils.send(new byte[]{0x01, 0x03, 0x00, 0x0A, 0x00, 0x06});
        ThreadUtils.runOnUiThreadDelayed(new Runnable() {
            @Override
            public void run() {
                BleUtils.send(new byte[]{0x01, 0x03, 0x00, 0x0D, 0x00, 0x06});
            }
        }, 500);
    }

    @Override
    protected void onDataReceived(byte[] bytes) {
        electricParamTestNewVH.isRefresh.set(false);
        if (bytes[0] == 0x01 && bytes[1] == 0x03 && (time++ == 0)) {
            int position = 3;
            electricParamTestNewVH.datas[0] = bytes[position];
            electricParamTestNewVH.datas[1] = bytes[++position];
            electricParamTestNewVH.datas[2] = bytes[++position];
            electricParamTestNewVH.datas[3] = bytes[++position];
            electricParamTestNewVH.datas[4] = bytes[++position];
            electricParamTestNewVH.datas[5] = bytes[++position];
            electricParamTestNewVH.datas[6] = bytes[++position];
            electricParamTestNewVH.datas[7] = bytes[++position];
            electricParamTestNewVH.datas[8] = bytes[++position];
            electricParamTestNewVH.datas[9] = bytes[++position];
            electricParamTestNewVH.datas[10] = bytes[++position];
            electricParamTestNewVH.datas[11] = bytes[++position];
        }
        if (bytes[0] == 0x01 && bytes[1] == 0x03 && time == 1) {
            time = 0;
            int position = 3;
            electricParamTestNewVH.datas[12] = bytes[position];
            electricParamTestNewVH.datas[13] = bytes[++position];
            electricParamTestNewVH.datas[14] = bytes[++position];
            electricParamTestNewVH.datas[15] = bytes[++position];
            electricParamTestNewVH.datas[16] = bytes[++position];
            electricParamTestNewVH.datas[17] = bytes[++position];
            electricParamTestNewVH.datas[18] = bytes[++position];
            electricParamTestNewVH.datas[19] = bytes[++position];
            electricParamTestNewVH.datas[20] = bytes[++position];
            electricParamTestNewVH.datas[21] = bytes[++position];
            electricParamTestNewVH.datas[22] = bytes[++position];
            electricParamTestNewVH.datas[23] = bytes[++position];
            electricParamTestNewVH.refreshUI();
        }
    }
}
