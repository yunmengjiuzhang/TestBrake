package cn.com.reformer.brake.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.reformer.brake.BR;
import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.R;
import cn.com.reformer.brake.vh.SettingVH;

public class SettingFragment extends BaseFragment {
    @Override
    protected View createSuccessView(LayoutInflater inflater, ViewGroup container) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
        viewDataBinding.setVariable(BR.setting, new SettingVH((BaseActivity) getActivity()));
        return viewDataBinding.getRoot();
    }
}
