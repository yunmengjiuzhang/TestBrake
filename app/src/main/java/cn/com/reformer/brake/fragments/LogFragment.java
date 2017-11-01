package cn.com.reformer.brake.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.reformer.brake.BR;
import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.R;
import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.vh.LogVH;
import mutils.ByteUtils;

public class LogFragment extends BaseFragment {

    private LogVH logVH;

    @Override
    protected View createSuccessView(LayoutInflater inflater, ViewGroup container) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_log, container, false);
        logVH = new LogVH((BaseActivity) getActivity());
        viewDataBinding.setVariable(BR.mylog, logVH);
        return viewDataBinding.getRoot();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            BleUtils.send(new byte[]{0x01, 0x03, 0x00, 0x14, 0x00, 0x14});
        }
    }

    private int time = 0;

    private byte[] mDatas = null;

    @Override
    protected void onDataReceived(byte[] datas) {
        time++;
        if (datas[0] == 0x01 && datas[1] == 0x03 && datas[2] == 0x28) {
            mDatas = datas;
            time = 0;
        }
        if (time != 2) {
            mDatas = ByteUtils.concat(mDatas, datas);
        } else {
            dealDatas(mDatas);
        }
    }

    private void dealDatas(byte[] datas) {
        logVH.results.clear();
        int position = 2;//3;
        for (int a = 0; a < 10; a++)
            logVH.results.add(getTime(datas[++position], datas[++position], datas[++position], datas[++position]));
        logVH.adapter.notifyDataSetChanged();
    }

    public String getTime(byte a, byte b, byte c, byte d) {
        int type = (int) (a >> 4);
        int time = (int) ((a & 0x0f << 24) | (b & 0xff << 16) | (c & 0xff << 8) | (d & 0xff));//s
        return type + "____" + time;
    }
}
