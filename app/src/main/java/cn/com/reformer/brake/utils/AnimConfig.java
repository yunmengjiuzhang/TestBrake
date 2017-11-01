package cn.com.reformer.brake.utils;

import android.databinding.BindingAdapter;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import cn.com.reformer.brake.R;

public class AnimConfig {
    @BindingAdapter({"app:setImageViewFresh"})
    public static void mImagerefresh(final View view, final boolean isfocucs) {
        if (isfocucs) {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.myrotate);
            view.startAnimation(animation);
        } else {
            view.clearAnimation();
        }
    }
}