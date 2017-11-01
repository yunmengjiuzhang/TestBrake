package cn.com.reformer.brake.vh;


import android.databinding.ObservableField;
import android.view.View;

import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.WorkPatternActivity;

public class WorkPatternTestItmeVH extends BaseVH {

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<Boolean> isChecked = new ObservableField<>();
    private int position;

    public WorkPatternTestItmeVH(BaseActivity ctx, String nam, boolean ischeck, int i) {
        super(ctx);
        name.set(nam);
        isChecked.set(ischeck);
        position = i;
    }

    public void onClick(View view) {
        ((WorkPatternActivity) mCtx).workPatternTestVH.clear(position);
        isChecked.set(true);
    }
}
