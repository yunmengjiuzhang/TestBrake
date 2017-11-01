package cn.com.reformer.brake;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import cn.com.reformer.brake.vh.ElectricParamTestVH;

public class ElectricParamTestActivity extends BaseActivity {

    private ElectricParamTestVH electricParamTestVH;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_electric_param_test);
        electricParamTestVH = new ElectricParamTestVH(this);
        viewDataBinding.setVariable(BR.electrictest, electricParamTestVH);
    }

    @Override
    protected void onResume() {
        super.onResume();
        electricParamTestVH.refreshDatas();
    }

    @Override
    protected void onDataReceived(byte[] bytes) {
        electricParamTestVH.isRefresh.set(false);
        if (bytes[0] == 0x01 && bytes[1] == 0x03 && (electricParamTestVH.time++ == 0)) {
            int position = 3;
            electricParamTestVH.datas[0] = bytes[position];
            electricParamTestVH.datas[1] = bytes[++position];
            electricParamTestVH.datas[2] = bytes[++position];
            electricParamTestVH.datas[3] = bytes[++position];
            electricParamTestVH.datas[4] = bytes[++position];
            electricParamTestVH.datas[5] = bytes[++position];
            electricParamTestVH.adapterMain.notifyDataSetChanged();
        }
        if (bytes[0] == 0x01 && bytes[1] == 0x03 && electricParamTestVH.time == 1) {
            electricParamTestVH.time = 0;
            int position = 3;
            electricParamTestVH.datas[6] = bytes[position];
            electricParamTestVH.datas[7] = bytes[++position];
            electricParamTestVH.datas[8] = bytes[++position];
            electricParamTestVH.datas[9] = bytes[++position];
            electricParamTestVH.datas[10] = bytes[++position];
            electricParamTestVH.datas[11] = bytes[++position];
            electricParamTestVH.adapterSecond.notifyDataSetChanged();
        }
    }
}
