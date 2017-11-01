package cn.com.reformer.brake.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mutils.ByteUtils;
import cn.com.reformer.brake.BR;
import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.R;
import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.utils.UIUtils;
import cn.com.reformer.brake.vh.MessageVH;
import mutils.CRC16;

public class MessageFragment extends BaseFragment {


    private MessageVH messageVH;

    @Override
    protected View createSuccessView(LayoutInflater inflater, ViewGroup container) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false);
        messageVH = new MessageVH((BaseActivity) getActivity());
        viewDataBinding.setVariable(BR.message, messageVH);
        return viewDataBinding.getRoot();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    removeMessages(0);
                    BleUtils.send(new byte[]{0x01, 0x04, 0x00, 0x02, 0x00, 0x07});
                    sendEmptyMessageDelayed(0, 1000);
                    break;
                case 1:
                    mHandler.removeMessages(1);
                    if (messageVH == null) {
                        mHandler.sendEmptyMessageDelayed(1, 200);
                        return;
                    }
                    if (messageVH.workPatten.get() == null) {
                        BleUtils.send(new byte[]{0x01, 0x03, 0x00, 0x00, 0x00, 0x01});
                        mHandler.sendEmptyMessageDelayed(1, 200);
                    } else {
                        mHandler.removeMessages(0);
                        mHandler.sendEmptyMessage(0);
                    }
                    break;
            }
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mHandler.sendEmptyMessage(1);
        } else {
            if (messageVH != null)
                messageVH.workPatten.set(null);
            mHandler.removeMessages(0);
            mHandler.removeMessages(1);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeMessages(0);
        mHandler.removeMessages(1);
    }

    @Override
    protected void onDataReceived(byte[] datas) {
        if (datas[0] == 0x01 && datas[1] == 0x03 && datas[2] == 0x02) {
            if (CRC16.calcCrc16(datas) != 0) {
                BleUtils.send(new byte[]{0x01, 0x03, 0x00, 0x00, 0x00, 0x01});
                return;
            }

//            int c5200 = ByteUtils.bytes2int(datas[3], datas[4]);
            byte c5200 = datas[4];
            byte[] c5300 = new byte[]{0x00, 0x11, 0x44, 0x22, 0x21, 0x12, 0x24, 0x42, 0x14, 0x41, 0x01, 0x03,0x04};
            for (int i = 0; i < c5300.length; i++)
                if (c5200 == c5300[i]) {
                    messageVH.workPatten.set(UIUtils.getResources().getStringArray(R.array.work_partens)[i]);
                }
        }

        if (datas[0] == 0x01 && datas[1] == 0x04) {
            if (CRC16.calcCrc16(datas) != 0) {
                return;
            }
            int position = 3;

            byte c5102_red = datas[position];
            byte c5102_arm = datas[++position];
            int c5103 = ByteUtils.bytes2int(datas[++position], datas[++position]);
            int c5104 = ByteUtils.bytes2int(datas[++position], datas[++position]);
            int c5105 = ByteUtils.bytes2int(datas[++position], datas[++position]);
            int c5106 = ByteUtils.bytes2int(datas[++position], datas[++position]);

            messageVH.point1.set(getPoint(c5102_red, 128));
            messageVH.point2.set(getPoint(c5102_red, 64));
            messageVH.point3.set(getPoint(c5102_red, 32));
            messageVH.point4.set(getPoint(c5102_red, 16));
            messageVH.point5.set(getPoint(c5102_red, 8));
            messageVH.point6.set(getPoint(c5102_red, 4));
            messageVH.point7.set(getPoint(c5102_red, 2));
            messageVH.point8.set(getPoint(c5102_red, 1));
            if (getPoint(c5102_arm, 0x44)) {
                messageVH.arm.set(1);
            } else if (getPoint(c5102_arm, 0x22)) {
                messageVH.arm.set(0);
            } else if (getPoint(c5102_arm, 0x11)) {
                messageVH.arm.set(2);
            }
            if (getPoint(c5102_arm, 0xff)) {
                messageVH.arm.set(0);
            }

            messageVH.enterPeple.set(String.valueOf(c5103));
            messageVH.unenterPeple.set(String.valueOf(c5105));
            messageVH.outPeple.set(String.valueOf(c5104));
            messageVH.unoutPeple.set(String.valueOf(c5106));

        }
    }

    public boolean getPoint(byte a, int b) {
        return b == (a & b);
    }
}
