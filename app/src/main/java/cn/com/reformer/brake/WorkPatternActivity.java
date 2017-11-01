package cn.com.reformer.brake;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;

import cn.com.reformer.brake.vh.WorkPatternTestVH;

public class WorkPatternActivity extends BaseActivity {

    public WorkPatternTestVH workPatternTestVH;

    @Override
    protected void initViews() {
//        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_work_pattern);
//        viewDataBinding.setVariable(BR.workPattern, new WorkPatternVH(this));
        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_work_pattern_test);
        workPatternTestVH = new WorkPatternTestVH(this);
        viewDataBinding.setVariable(BR.workPatterntest, workPatternTestVH);
    }


    @Override
    protected void onDataReceived(byte[] bytes) {
        if (bytes[0] == 0x01 && bytes[1] == 0x10 && bytes[2] == 0x00 && bytes[3] == 0x00 && bytes[4] == 0x00 && bytes[5] == 0x01) {
            finish();
        }
    }
}
