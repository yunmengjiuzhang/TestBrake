package cn.com.reformer.brake;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.vh.VoiceSwitchVH;
import mutils.ByteUtils;

public class VoiceSwitchActivity extends BaseActivity {

    private VoiceSwitchVH voiceSwitchVH;

    @Override
    protected void initViews() {
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_voice_switch);
        voiceSwitchVH = new VoiceSwitchVH(this);
        viewDataBinding.setVariable(BR.voiceSwitch, voiceSwitchVH);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BleUtils.send(new byte[]{0x01, 0x03, 0x00, 0x09, 0x00, 0x01});
    }

    @Override
    protected void onDataReceived(byte[] datas) {
        super.onDataReceived(datas);
        if (datas[0] == 0x01 && datas[1] == 0x03) {
            int i = ByteUtils.bytes2int(datas[3], datas[4]);
            voiceSwitchVH.refresh(i);
        }
    }
}
