package cn.com.reformer.brake;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.vh.ElectricTimesVH;
import mutils.ByteUtils;

public class ElectricTimeActivity extends BaseActivity {

    private ElectricTimesVH electricTimesVH;

    @Override
    protected void initViews() {
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_electric_time);
        electricTimesVH = new ElectricTimesVH(this);
        viewDataBinding.setVariable(BR.times, electricTimesVH);
        BleUtils.send(new byte[]{0x01, 0x04, 0x00, 0x07, 0x00, 0x04});
    }

    @Override
    protected void onDataReceived(byte[] datas) {
        if (datas[0] == 0x01 && datas[1] == 0x04 && datas[2] == 0x08) {
            electricTimesVH.isRefresh.set(false);
            electricTimesVH.mainTimes.set((long) ByteUtils.bytesToInt2(datas, 3));
            electricTimesVH.secondTimes.set((long) ByteUtils.bytesToInt2(datas, 7));
        }
    }

}
