package cn.com.reformer.brake.vh;


import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.utils.BleUtils;
import mutils.ThreadUtils;

public class ElectricTimesVH extends BaseVH {
    public final ObservableField<Long> secondTimes = new ObservableField<>();
    public final ObservableField<Long> mainTimes = new ObservableField<>();
    public final ObservableField<Boolean> isRefresh = new ObservableField<>(true);

    public ElectricTimesVH(BaseActivity ctx) {
        super(ctx);
    }

    public void onRefreshClick(View view) {
        BleUtils.send(new byte[]{0x01, 0x04, 0x00, 0x07, 0x00, 0x04});
        isRefresh.set(true);
    }

    @BindingAdapter({"app:electricTimesRefresh"})
    public static void refreshScan(final SwipeRefreshLayout swipeRefreshLayout, final ElectricTimesVH logVH) {
        if (logVH != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    BleUtils.send(new byte[]{0x01, 0x04, 0x00, 0x07, 0x00, 0x04});
                    logVH.isRefresh.set(true);
                    ThreadUtils.runOnUiThreadDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, 400);
                }
            });
        }
    }
}
