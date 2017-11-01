package cn.com.reformer.brake.vh;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.view.View;

import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.R;
import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.utils.UIUtils;

public class MessageVH extends BaseVH{
    public final ObservableField<String> workPatten = new ObservableField<>();
    public final ObservableField<String> enterPeple = new ObservableField<>();
    public final ObservableField<String> unenterPeple = new ObservableField<>();
    public final ObservableField<String> outPeple = new ObservableField<>();
    public final ObservableField<String> unoutPeple = new ObservableField<>();
    public final ObservableField<Boolean> point1 = new ObservableField<>();
    public final ObservableField<Boolean> point2 = new ObservableField<>();
    public final ObservableField<Boolean> point3 = new ObservableField<>();
    public final ObservableField<Boolean> point4 = new ObservableField<>();
    public final ObservableField<Boolean> point5 = new ObservableField<>();
    public final ObservableField<Boolean> point6 = new ObservableField<>();
    public final ObservableField<Boolean> point7 = new ObservableField<>();
    public final ObservableField<Boolean> point8 = new ObservableField<>();
    public final ObservableField<Integer> arm = new ObservableField<>(0);

    public MessageVH(BaseActivity ctx) {
        super(ctx);
    }

    public void enterOpen(View view) {
        BleUtils.send(new byte[]{0x01, 0x05, 0x00, 0x01, (byte) 0xFF, 0x00});
    }

    public void outOpen(View view) {
        BleUtils.send(new byte[]{0x01, 0x05, 0x00, 0x00, (byte) 0xFF, 0x00});
    }

    public void close(View view) {
        BleUtils.send(new byte[]{0x01, 0x05, 0x00, 0x02, (byte) 0xFF, 0x00});
    }

    @BindingAdapter({"app:changeBrakeUI"})
    public static void refreshBrakeUI(final View view, final Integer arm) {
        switch (arm) {
            case 0:
                view.setBackground(UIUtils.getDrawable(R.mipmap.brake_close));
                break;
            case 1:
                view.setBackground(UIUtils.getDrawable(R.mipmap.brake_enter));
                break;
            case 2:
                view.setBackground(UIUtils.getDrawable(R.mipmap.brake_out));
                break;
        }
    }
}
