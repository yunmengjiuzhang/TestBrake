package cn.com.reformer.brake.vh;

import android.content.Intent;
import android.databinding.ObservableField;
import android.view.View;

import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.LoginActivity;
import cn.com.reformer.brake.utils.UIUtils;
import cn.com.reformer.brake.views.WFDialog;

public class MeVH extends BaseVH{

    public final ObservableField<String> c5500 = new ObservableField<>();
    public final ObservableField<String> c5501 = new ObservableField<>();
    public final ObservableField<String> c5502 = new ObservableField<>();
    public final ObservableField<String> c5503 = new ObservableField<>();
    public final ObservableField<String> c5504 = new ObservableField<>();
    public final ObservableField<String> c5505 = new ObservableField<>();

    public MeVH(BaseActivity ctx) {
        super(ctx);
    }

    public void onClose(View view) {
        final WFDialog wfDialog = new WFDialog(mCtx);
        wfDialog.setTitle("退出登录?");
        wfDialog.setMessage("清楚信息,重新登录");
        wfDialog.setYesOnclickListener("是", new WFDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(String messge) {
                UIUtils.startActivity(new Intent(UIUtils.getContext(), LoginActivity.class));
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
