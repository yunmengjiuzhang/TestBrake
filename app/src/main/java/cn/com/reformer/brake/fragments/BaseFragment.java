package cn.com.reformer.brake.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.com.reformer.brake.MainActivity;

public abstract class BaseFragment extends Fragment {

    protected MainActivity mAct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAct = (MainActivity) getActivity();
        return createSuccessView(inflater, container);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(byte[] bytes) {
        onDataReceived(bytes);
    }

    protected void onDataReceived(byte[] bytes) {

    }

    protected abstract View createSuccessView(LayoutInflater inflater, ViewGroup container);

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
