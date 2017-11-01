package cn.com.reformer.brake.vh;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Arrays;

import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.R;
import cn.com.reformer.brake.utils.BleUtils;
import mutils.SpUtil;
import cn.com.reformer.brake.utils.ToastUtils;
import cn.com.reformer.brake.utils.UIUtils;
import mutils.ByteUtils;

public class WorkPatternVH extends BaseVH {
    private int setCheckedPosition = 10;

    public WorkPatternVH(BaseActivity ctx) {
        super(ctx);
        setCheckedPosition = SpUtil.getInt(mCtx, "5300", 10);
    }

    @BindingAdapter({"app:setitem"})
    public static void settextchangeListener(final RadioGroup radioGroup, final WorkPatternVH workPatternVH) {
        if (workPatternVH != null) {
            radioGroup.setDividerDrawable(UIUtils.getDrawable(R.drawable.list_divider));
            final String[] stringArray = UIUtils.getStringArray(R.array.work_partens);
            RadioButton radioButton;
            for (int i = 0; i < stringArray.length - 1; i++) {
                radioButton = new RadioButton(radioGroup.getContext());
                radioButton.setTextSize(16);
                radioButton.setText(stringArray[i]);
                radioButton.setHeight(107);
                radioButton.setTextColor(UIUtils.getResources().getColor(radioButton.isChecked() ? R.color.rb_true : R.color.rb_false));
                radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        buttonView.setTextColor(UIUtils.getResources().getColor(isChecked ? R.color.rb_true : R.color.rb_false));
                        buttonView.getId();
                    }
                });
                radioGroup.addView(radioButton);
                final int finalI = i;
                radioButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showToast("" + finalI);
//                        ArrayList<Integer> c5200All = new ArrayList<>(Arrays.asList(68, 0, 17, 34, 18, 33, 66, 36,/ 65, 20));
//                        byte[] valueaa = workPatternVH.get5300value(c5200All.get(finalI));
//                        BaseApp.mBle.send(new byte[]{0x01, 0x10, 0x00, 0x00, 0x00, 0x01, 0x02, valueaa[0], valueaa[1]});
                        byte[] c5300 = new byte[]{0x00, 0x11, 0x44, 0x22, 0x21, 0x12, 0x24, 0x42, 0x14, 0x41, (byte) 0xff};
                        BleUtils.send(new byte[]{0x01, 0x10, 0x00, 0x00, 0x00, 0x01, 0x02, 0x00, c5300[finalI]});
                        if (finalI >= 0 && finalI < stringArray.length)
                            SpUtil.putInt(radioGroup.getContext(), "5300", finalI);
//                        workPatternVH.finish();
                    }
                });
            }
            radioButton = (RadioButton) radioGroup.getChildAt(workPatternVH.setCheckedPosition);
            radioButton.setChecked(true);
        }
    }

    private static byte[] get5300value(int num) {
        ArrayList<Integer> c5300 = new ArrayList<>(Arrays.asList(0, 17, 68, 34, 18, 33, 66, 36, 65, 20));
        return ByteUtils.int2bytes1(c5300.get(num));
    }
}
