package cn.com.reformer.brake;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import mutils.ByteUtils;
import cn.com.reformer.brake.utils.BleUtils;
import mutils.SpUtil;
import cn.com.reformer.brake.utils.UIUtils;
import cn.com.reformer.brake.vh.RunParamVH;
import mutils.CRC16;

public class RunParamActivity extends BaseActivity {

    private RunParamVH runParamVH;

    public void initViews() {
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_run_parama);
        runParamVH = new RunParamVH(this);
        viewDataBinding.setVariable(BR.param, runParamVH);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh() {
        BleUtils.send(new byte[]{0x01, 0x03, 0x00, 0x00, 0x00, 0x07});
        runParamVH.isRefresh.set(true);
    }

    @Override
    protected void onDataReceived(byte[] datas) {
        if (datas[0] == 0x01 && datas[1] == 0x10 && datas[2] == 0x00) {
            switch (datas[3]) {
                case 0x01:
                case 0x02:
                case 0x03:
                case 0x04:
                case 0x05:
                case 0x06:
                    refresh();
                    return;
            }
        }


        if (datas[0] == 0x01 && datas[1] == 0x10) {//开始接受数据
            if (CRC16.calcCrc16(datas) != 0) {
//                ToastUtils.showToast(UIUtils.getContext(), "数据异常");
                return;
            }
//            ToastUtils.showToast(UIUtils.getContext(), "设置数据成功!");
            runParamVH.isRefresh.set(false);
            return;
        }
        if (datas[0] == 0x01 && datas[1] == 0x04) {
            runParamVH.isRefresh.set(false);
        }

        if (datas[0] == 0x01 && datas[1] == 0x03) {//开始接受数据
            dealDatas(datas);
        }
    }

    private void dealDatas(byte[] result) {
        runParamVH.isRefresh.set(false);
        if (CRC16.calcCrc16(result) != 0) {
//            ToastUtils.showToast(UIUtils.getContext(), "数据异常");
            return;
        }
        int startPosition = 3;

        byte c5200 = result[++startPosition];
        byte[] c5300 = new byte[]{0x00, 0x11, 0x44, 0x22, 0x21, 0x12, 0x24, 0x42, 0x14, 0x41, 0x01, 0x03};
        for (int i = 0; i < c5300.length; i++)
            if (c5200 == c5300[i]) {
//                SpUtil.putInt(mCtx, "5300", i);
                runParamVH.workpattenStr.set(UIUtils.getResources().getStringArray(R.array.work_partens)[i]);
                SpUtil.putInt(mCtx, "5300", i);
            }

        int c5201 = ByteUtils.bytes2int(result[++startPosition], result[++startPosition]);
        runParamVH.infraredInt.set(c5201 / 10);
        int c5202 = ByteUtils.bytes2int(result[++startPosition], result[++startPosition]);
        runParamVH.cardAuthorityInt.set(c5202 / 10);
        int c5203 = ByteUtils.bytes2int(result[++startPosition], result[++startPosition]);
        runParamVH.occupyWayInt.set(c5203 / 10);
        int c5204 = ByteUtils.bytes2int(result[++startPosition], result[++startPosition]);
        int c5205 = ByteUtils.bytes2int(result[++startPosition], result[++startPosition]);
        runParamVH.wayCloseInt.set(c5205 / 10);
        int c5206 = ByteUtils.bytes2int(result[++startPosition], result[++startPosition]);
        runParamVH.voiceNextInt.set(c5206 / 10);
    }
}
