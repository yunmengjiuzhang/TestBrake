package cn.com.reformer.brake.vh;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import cn.com.reformer.brake.BR;
import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.R;
import cn.com.reformer.brake.utils.BleUtils;
import mutils.SpUtil;
import cn.com.reformer.brake.utils.UIUtils;
import me.tatarka.bindingcollectionadapter.ItemView;

public class WorkPatternTestVH extends BaseVH {

    public final ObservableList<WorkPatternTestItmeVH> itemVHs = new ObservableArrayList<>();//数据集合
    public final ItemView itemView = ItemView.of(BR.itemWorkpatter, R.layout.item_work_pattern_test);//view

    public WorkPatternTestVH(BaseActivity ctx) {
        super(ctx);
        init();
    }

    private void init() {
        final String[] stringArray = UIUtils.getStringArray(R.array.work_partens);
        int anInt = SpUtil.getInt(mCtx, "5300", 10);
        for (int i = 0; i < stringArray.length - 4; i++) {
            itemVHs.add(new WorkPatternTestItmeVH(mCtx, stringArray[i], anInt == i, i));
        }
    }

    public void clear(int position) {
        for (int i = 0; i < itemVHs.size(); i++)
            itemVHs.get(i).isChecked.set(position == i);
        byte[] c5300 = new byte[]{0x00, 0x11, 0x44, 0x22, 0x21, 0x12, 0x24, 0x42, 0x14, 0x41, (byte) 0xff};
        BleUtils.send(new byte[]{0x01, 0x10, 0x00, 0x00, 0x00, 0x01, 0x02, 0x00, c5300[position]});
        SpUtil.putInt(mCtx, "5300", position);
    }
}
