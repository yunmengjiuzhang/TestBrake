package cn.com.reformer.brake.vh;

import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class LogItemVH extends RecyclerView.ViewHolder {
    public final ObservableField<String> content = new ObservableField<>();

    public LogItemVH(View root) {
        super(root);
    }

    public void onItemClick(View view) {

    }
}
