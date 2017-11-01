package cn.com.reformer.brake.vh;

import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.view.View;

public class ElectricParamTestItemVH extends BaseRecyclerVH {
    public final ObservableField<String> title = new ObservableField<>("主开速度");
    public final ObservableField<Integer> num = new ObservableField<>(75);

    public ElectricParamTestItemVH(ViewDataBinding binding) {
        super(binding);
    }

    public void itemplus(View view) {
        num.set(num.get() + 1);
    }

    public void itemminus(View view) {
        num.set(num.get() - 1);
    }
}
