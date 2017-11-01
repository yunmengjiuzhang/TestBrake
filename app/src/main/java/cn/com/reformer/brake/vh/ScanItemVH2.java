package cn.com.reformer.brake.vh;

import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.view.View;

import cn.com.reformer.brake.MainActivity;
import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.utils.UIUtils;
import cn.com.reformer.ble.BleBean;

public class ScanItemVH2 extends BaseRecyclerVH {

    public final ObservableField<String> mMac = new ObservableField<>();
    public BleBean mBean;

    public ScanItemVH2(ViewDataBinding binding) {
        super(binding);
    }

    public void onItemClick(View view) {
        BleUtils.conn(mBean.address);
        UIUtils.startActivity(new Intent(UIUtils.getContext(), MainActivity.class));
        //判断机型并存储 // TODO: 2017/7/10 0010
    }

}
