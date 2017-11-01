package cn.com.reformer.brake.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.CycleInterpolator;
import android.widget.TextView;

import cn.com.reformer.brake.R;
import mutils.ThreadUtils;


/**
 * 准备登录对话框
 */
public class WFLoginDialog {
    private static WFInnerLoginDialog dialog;
    private static String mmessage;

    public static void showTime(final Activity ctx) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog == null)
                    dialog = new WFInnerLoginDialog(ctx).initView();
                dialog.show();
            }
        });
    }

    public static void showTime(final Activity ctx, final String message) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mmessage = message;
                if (dialog == null)
                    dialog = new WFInnerLoginDialog(ctx).initView();
                dialog.show();
            }
        });
    }

    public static void dismissSelf() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        });
    }

    private static class WFInnerLoginDialog extends Dialog {

        private final Context context;

        public WFInnerLoginDialog(Context context) {
            super(context, R.style.opennigstyle);
            this.context = context;
        }

        private WFInnerLoginDialog initView() {
            return init();
        }

        private WFInnerLoginDialog init() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.dialog_login, null);
            View iv_loading = layout.findViewById(R.id.iv_loading);
            TextView tv_message = (TextView) layout.findViewById(R.id.tv_message);
            if (mmessage != null) {
                tv_message.setText(mmessage);
            }
            iv_loading.animate().rotation(20000).setInterpolator(new CycleInterpolator(1)).setDuration(100000);
            this.addContentView(layout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            this.setCancelable(false);
            this.setContentView(layout);
            return this;
        }
    }


}
