package cn.com.reformer.brake.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.com.reformer.brake.BR;
import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.R;
import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.vh.MeVH;
import mutils.ByteUtils;
import mutils.CRC16;


public class MeFragment extends BaseFragment {

    private MeVH meVH;

    @Override
    protected View createSuccessView(LayoutInflater inflater, ViewGroup container) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_me, container, false);
        meVH = new MeVH((BaseActivity) getActivity());
        viewDataBinding.setVariable(BR.me, meVH);
        return viewDataBinding.getRoot();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            BleUtils.send(new byte[]{0x01, 0x2B, 0x00, 0x00, 0x00, 0x06});
        }
    }


    private int time = 0;
    private byte[] mDatas = new byte[]{};

    @Override
    protected void onDataReceived(byte[] datas) {
        if (datas[0] == 0x01 && datas[1] == 0x2B) {
            time = 0;
            mDatas = new byte[]{};
        }
        mDatas = ByteUtils.concat(mDatas, datas);
        if (++time == 3) {
            dealDatas(mDatas);
        }
    }

    private void dealDatas(byte[] datas) {
        if (CRC16.calcCrc16(datas) != 0) {
//            ToastUtils.showToast(UIUtils.getContext(), "数据异常");
            return;
        }
        byte[] newDatas = new byte[datas.length - 2];
        System.arraycopy(datas, 0, newDatas, 0, datas.length - 2);
        getSmallDatas(newDatas);
    }

    private void getSmallDatas(byte[] datas) {
        ArrayList<byte[]> test = new ArrayList<>();
        try {
            int temid = 3;
//            int tempobj = datas[temid];
            int tempLength = datas[temid + 1];
            byte[] tempbytes = newsplitArray(datas, temid + 2, tempLength);
            test.add(tempbytes);
            while (temid + tempLength + 2 != datas.length) {
                temid = temid + tempLength + 2;
                tempLength = datas[temid + 1];
//                tempobj = datas[temid];
                tempbytes = newsplitArray(datas, temid + 2, tempLength);
                test.add(tempbytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        meVH.c5500.set(getS(test.get(0)));
        meVH.c5501.set(getS(test.get(1)));
        meVH.c5502.set(getS(test.get(2)));
        meVH.c5503.set(getS(test.get(3)));
        meVH.c5504.set(getS(test.get(4)));
        meVH.c5505.set(getS(test.get(5)));
    }

    private String getS(byte[] datas) {
        StringBuffer sb = new StringBuffer();
        char[] temp = new char[datas.length];
        for (int i = 0; i < datas.length; i++) {
            temp[i] = (char) datas[i];
            sb.append(temp[i]);
        }
        return sb.toString();
    }

    public byte[] newsplitArray(byte[] datas, int startPosition, int length) {
        byte[] temp = new byte[length];
        System.arraycopy(datas, startPosition, temp, 0, length);
        return temp;
    }
}
