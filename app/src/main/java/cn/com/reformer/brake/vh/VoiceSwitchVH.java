package cn.com.reformer.brake.vh;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import cn.com.reformer.brake.BR;
import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.R;
import cn.com.reformer.brake.utils.BleUtils;
import me.tatarka.bindingcollectionadapter.ItemView;
import mutils.ByteUtils;
import mutils.ThreadUtils;

public class VoiceSwitchVH extends BaseVH {

    public final ObservableList<VoiceSwitchItemVH> itemVHs = new ObservableArrayList<>();//数据集合
    public final ItemView itemView = ItemView.of(BR.voiceSwitchItem, R.layout.item_voice_switch);//view

    private String[] itemNames = {"验证成功", "验证失败", "恢复出厂成功", "设置失败", "设置成功", "人员尾随", "人员反向", "欢迎光临", "占用通道超时", "紧急关门无法进入", "非法闯入"};

    public VoiceSwitchVH(BaseActivity ctx) {
        super(ctx);
        itemVHs.clear();
        for (int i = 0; i < itemNames.length; i++)
            itemVHs.add(new VoiceSwitchItemVH(itemNames[i]));
    }

    public void save(View view) {
        int value = 0;
        VoiceSwitchItemVH voiceSwitchItemVH;
        int temp = 0;
        for (int i = 0; i < itemNames.length; i++) {
            voiceSwitchItemVH = itemVHs.get(i);
            if (voiceSwitchItemVH.isCheck.get()) {
                if (i == 6) {
                    value = value | (1 << temp);
                    value = value | (1 << (++temp));
                } else if (i == 9) {
                    value = value | (1 << (temp++));
                } else {
                    value = value | 1 << temp;
                }
            } else {
                if (i == 6) {
                    value = value & (~(1 << temp));
                    value = value & (~(1 << (++temp)));
                } else if (i == 9) {
                    value = value & (~(1 << temp++));
                } else {
                    value = value & (~(1 << temp));
                }
            }
            ++temp;
        }
        byte[] bytes = ByteUtils.int2bytes1(value);
        BleUtils.send(new byte[]{0x01, 0x10, 0x00, 0x09, 0x00, 0x01, 0x02, bytes[0], bytes[1]});
    }

    public void refresh(int value) {
        int temp = 0;
        for (int i = 0; i < itemNames.length; i++) {
            VoiceSwitchItemVH voiceSwitchItemVH = itemVHs.get(i);
            if (i == 6) {
                voiceSwitchItemVH.isCheck.set(!((value & (1 << temp)) == 0));
                ++temp;
            } else if (i == 9) {
                voiceSwitchItemVH.isCheck.set(!((value & (1 << temp)) == 0));
                ++temp;
            } else {
                voiceSwitchItemVH.isCheck.set(!((value & (1 << temp)) == 0));
            }
            ++temp;
        }
    }

    @BindingAdapter({"app:voiceswitchRefresh"})
    public static void refreshScan(final SwipeRefreshLayout swipeRefreshLayout, final VoiceSwitchVH scanVH) {
        if (scanVH != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    BleUtils.send(new byte[]{0x01, 0x03, 0x00, 0x09, 0x00, 0x01});
                    ThreadUtils.runOnUiThreadDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, 1000);
                }
            });
        }
    }
}
