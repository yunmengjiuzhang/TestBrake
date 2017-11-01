package cn.com.reformer.brake;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import cn.com.reformer.brake.vh.EmergencyVH;
import mutils.ByteUtils;

public class EmergencyActivity extends BaseActivity {

    public void initViews() {
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_emergency);
        viewDataBinding.setVariable(BR.emergency, new EmergencyVH(this));
    }

    @Override
    protected void onDataReceived(byte[] result) {
        if (result[1] == 0x05) {
            switch (ByteUtils.bytes2int(result[2], result[3])) {
                case 7://设置电机中点
//                    ToastUtils.showToast(mCtx, "设置电机中点");
                    break;
                case 8://设置电机确认
//                    ToastUtils.showToast(mCtx, "设置电机确认");
                    break;
                case 9://恢复出厂设置
//                    ToastUtils.showToast(mCtx, "恢复出厂设置");
                    break;
                case 3://紧急开门
//                    ToastUtils.showToast(mCtx, "开门成功");
                    break;
                case 5://紧急关门
//                    ToastUtils.showToast(mCtx, "关门成功");
                    break;
                case 6://取消紧急
//                    ToastUtils.showToast(mCtx, "取消成功");
                    break;
            }
        }
    }
}
