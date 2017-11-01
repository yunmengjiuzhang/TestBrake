package cn.com.reformer.brake;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.vh.ElectricParamVH;

public class ElecrcParamActivity extends BaseActivity {

    private ElectricParamVH electricParam;

    @Override
    protected void initViews() {
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_elecrc_param);
        electricParam = new ElectricParamVH(this);
        viewDataBinding.setVariable(BR.elec, electricParam);
    }

    @Override
    protected void onResume() {
        super.onResume();
        electricParam.isRefresh.set(true);
        BleUtils.send(new byte[]{0x01, 0x03, 0x00, (byte) (electricParam.isMain.get() ? 0x0A : 0x0D), 0x00, 0x03});
    }

    @Override
    protected void onDataReceived(byte[] bytes) {
            electricParam.isRefresh.set(false);
            int position = 3;
            if (bytes[0] == 0x01 && bytes[1] == 0x03 && electricParam.isMain.get()) {
                electricParam.datas[0] = bytes[position];
                electricParam.datas[1] = bytes[++position];
                electricParam.datas[2] = bytes[++position];
                electricParam.datas[3] = bytes[++position];
                electricParam.datas[4] = bytes[++position];
                electricParam.datas[5] = bytes[++position];
                electricParam.refreshMain();
            }
            if (bytes[0] == 0x01 && bytes[1] == 0x03 && !electricParam.isMain.get()) {
                electricParam.datas[6] = bytes[position];
                electricParam.datas[7] = bytes[++position];
                electricParam.datas[8] = bytes[++position];
                electricParam.datas[9] = bytes[++position];
                electricParam.datas[10] = bytes[++position];
                electricParam.datas[11] = bytes[++position];
                electricParam.refreshSecond();
            }
    }
}
