package cn.com.reformer.brake.vh;

import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.view.View;

import cn.com.reformer.brake.MainActivity;
import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.utils.UIUtils;
import cn.com.reformer.ble.BleBean;

public class ScanItemVH extends BaseRecyclerVH {

    public final ObservableField<String> mMac = new ObservableField<>();
    public BleBean mBean;

    public ScanItemVH(ViewDataBinding binding) {
        super(binding);
    }

    public void onItemClick(View view) {
        BleUtils.conn(mBean.address);
        UIUtils.startActivity(new Intent(UIUtils.getContext(), MainActivity.class));
        //判断机型并存储 // TODO: 2017/7/10 0010
    }

    public int getColor() {
        return mMac.get().contains("RF") ? Color.parseColor("#000000") : Color.parseColor("#ffffff");
    }
}
