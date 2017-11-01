package cn.com.reformer.brake.vh;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.CheckBox;

import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.ElecrcParamActivity;
import cn.com.reformer.brake.ElectricParamTestActivity;
import cn.com.reformer.brake.ElectricParamTestNewActivity;
import cn.com.reformer.brake.ElectricTimeActivity;
import cn.com.reformer.brake.EmergencyActivity;
import cn.com.reformer.brake.RunParamActivity;
import cn.com.reformer.brake.VoiceSwitchActivity;
import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.utils.UIUtils;
import cn.com.reformer.brake.views.floatView.SettingsCompat;

public class SettingVH extends BaseVH{

    public SettingVH(BaseActivity ctx) {
        super(ctx);
    }

    public void onClick1(View view) {
        UIUtils.startActivity(new Intent(mCtx, RunParamActivity.class));
    }

    public void onClick2(View view) {
        UIUtils.startActivity(new Intent(mCtx, ElecrcParamActivity.class));
    }

    public void onClicktest(View view) {
        UIUtils.startActivity(new Intent(mCtx, ElectricParamTestActivity.class));
    }

    public void onClick3(View view) {
        UIUtils.startActivity(new Intent(mCtx, VoiceSwitchActivity.class));
    }

    public void onClick4(View view) {
        UIUtils.startActivity(new Intent(mCtx, EmergencyActivity.class));
    }

    public void onDevicetimes(View view) {
        UIUtils.startActivity(new Intent(mCtx, ElectricTimeActivity.class));
    }

    public void oneChicken(View view) {
        BleUtils.send(new byte[]{0x01, 0x05, 0x00, 0x0A, (byte) 0xFF, 0x00});
    }

    public void onNewElecParam(View view) {
        UIUtils.startActivity(new Intent(mCtx, ElectricParamTestNewActivity.class));
    }

    @BindingAdapter({"app:startFloat"})
    public static void floatwindow(final CheckBox checkBox, final SettingVH logVH) {
        if (logVH != null) {
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent();
                    intent.setAction("wangfei.floatwindow");
                    intent.setPackage(logVH.mCtx.getPackageName());
                    if (checkBox.isChecked()) {
                        if (SettingsCompat.canDrawOverlays(logVH.mCtx)) {
                            logVH.mCtx.startService(intent);
                        } else {
                            SettingsCompat.manageDrawOverlays(logVH.mCtx);
                        }
                    } else {
                        logVH.mCtx.stopService(intent);
                    }
                }
            });
        }
    }
}
