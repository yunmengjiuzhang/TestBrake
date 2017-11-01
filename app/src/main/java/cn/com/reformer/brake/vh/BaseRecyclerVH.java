package cn.com.reformer.brake.vh;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class BaseRecyclerVH extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;

    public BaseRecyclerVH(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ViewDataBinding getBinding() {
        return binding;
    }
}
