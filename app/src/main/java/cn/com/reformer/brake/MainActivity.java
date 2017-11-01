package cn.com.reformer.brake;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.vh.MainVH;

public class MainActivity extends BaseActivity {

    private MainVH main2VH;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        main2VH = new MainVH(this);
        viewDataBinding.setVariable(BR.main2, main2VH);
    }
}
