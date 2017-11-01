package cn.com.reformer.brake.vh;

import android.view.View;

import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.views.WFDialog;

public class EmergencyVH extends BaseVH {

    public EmergencyVH(BaseActivity ctx) {
        super(ctx);
    }

    public void onOpen(View view) {
        BleUtils.send(new byte[]{0x01, 0x05, 0x00, 0x03, (byte) 0xFF, 0x00});
    }

    public void onClose(View view) {
        BleUtils.send(new byte[]{0x01, 0x05, 0x00, 0x05, (byte) 0xFF, 0x00});
    }

    public void onCancle(View view) {
        BleUtils.send(new byte[]{0x01, 0x05, 0x00, 0x06, (byte) 0xFF, 0x00});
    }

    public void onMidpoint(View view) {
        BleUtils.send(new byte[]{0x01, 0x05, 0x00, 0x07, (byte) 0xFF, 0x00});
    }

    public void onOK(View view) {
        BleUtils.send(new byte[]{0x01, 0x05, 0x00, 0x08, (byte) 0xFF, 0x00});
    }

    public void onReset(View view) {
        final WFDialog wfDialog = new WFDialog(mCtx);
        wfDialog.setTitle("恢复出厂设置 ?");
        wfDialog.setYesOnclickListener("是", new WFDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(String messge) {
                BleUtils.send(new byte[]{0x01, 0x05, 0x00, 0x09, (byte) 0xFF, 0x00});
                wfDialog.dismiss();
            }
        });
        wfDialog.setNoOnclickListener("否", new WFDialog.onNoOnclickListener() {
            @Override
            public void onNoClick(String messge) {
                wfDialog.dismiss();
            }
        });
        wfDialog.show();
    }

    public void onResetSoft(View view) {
        final WFDialog wfDialog = new WFDialog(mCtx);
        wfDialog.setTitle("软重启 ?");
        wfDialog.setYesOnclickListener("是", new WFDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(String messge) {
                BleUtils.send(new byte[]{0x01, 0x05, 0x00, 0x0B, (byte) 0xFF, 0x00});
                wfDialog.dismiss();
            }
        });
        wfDialog.setNoOnclickListener("否", new WFDialog.onNoOnclickListener() {
            @Override
            public void onNoClick(String messge) {
                wfDialog.dismiss();
            }
        });
        wfDialog.show();
    }
}
