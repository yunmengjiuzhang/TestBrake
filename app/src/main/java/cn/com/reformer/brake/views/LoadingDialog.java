package cn.com.reformer.brake.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import cn.com.reformer.brake.R;
import cn.com.reformer.brake.ScanActivity;
import cn.com.reformer.brake.utils.UIUtils;

public class LoadingDialog extends ProgressDialog {
    private String tip;
    private Context context;

    public LoadingDialog(Context context) {
        super(context, R.style.loading_dialog);
        this.context = context;
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_loading);
        View viewById = findViewById(R.id.body);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(String.valueOf(212));
                UIUtils.startActivity(new Intent(context, ScanActivity.class));
            }
        });
        if (!TextUtils.isEmpty(tip)) {
            TextView tvTip = (TextView) findViewById(android.R.id.message);
            tvTip.setText(tip);
        }
        setCancelable(false);
    }

    @Override
    public void setMessage(CharSequence message) {
        tip = message.toString();
    }
}