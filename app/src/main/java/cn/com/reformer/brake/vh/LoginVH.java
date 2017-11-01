package cn.com.reformer.brake.vh;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.gjiazhe.panoramaimageview.PanoramaImageView;

import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.ScanActivity;
import cn.com.reformer.brake.utils.UIUtils;

public class LoginVH extends BaseVH {

    private String phone;
    private String code;

    public ObservableField<String> btncode = new ObservableField<>("获取验证码");

    public final ObservableField<GyroscopeObserver> gyroscopeObserver = new ObservableField<>();

    public LoginVH(BaseActivity ctx) {
        super(ctx);
    }

    public void onGetCodeClick(View view) {
//        if (phone.get() == null) {
//            ToastUtils.s("手机号为空!");
//            return;
//        }
        time = 60;
        mHandler.sendEmptyMessage(0);
    }

    public void onOKClick(View view) {
        UIUtils.startActivity(new Intent(mCtx, ScanActivity.class));
//        UIUtils.startActivity(new Intent(mCtx, MaindddActivity.class));
//        TestReceiveutils.registerReceive(mCtx);
    }

    private int time = 60;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    mHandler.removeMessages(0);
                    btncode.set(String.valueOf(--time));
                    mHandler.sendEmptyMessageDelayed(0, 1000);
                    if (time == 0) {
                        btncode.set("发送验证码");
                        mHandler.removeMessages(0);
                    }
                    break;
            }
        }
    };

    @BindingAdapter({"app:setPanoramaImageView"})
    public static void setPanoramaImageView(final PanoramaImageView panoramaImageView, final GyroscopeObserver gyroscopeObserver) {
        if (gyroscopeObserver != null) {
            panoramaImageView.setGyroscopeObserver(gyroscopeObserver);
        }
    }

    @BindingAdapter({"app:phonetextwatcher"})
    public static void setphoneListener(final EditText editText, final LoginVH loginVH) {
        if (loginVH != null) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    loginVH.phone = s.toString();
                }
            });
        }
    }

    @BindingAdapter({"app:codetextwatcher"})
    public static void setcodeListener(final EditText editText, final LoginVH loginVH) {
        if (loginVH != null) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    loginVH.code = s.toString();
                }
            });
        }
    }
}
