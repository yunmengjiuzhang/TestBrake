package cn.com.reformer.brake.vh;

import android.databinding.ObservableField;
import android.view.View;

public class VoiceSwitchItemVH {
    public final ObservableField<Boolean> isCheck = new ObservableField<>(true);
    public final ObservableField<String> name = new ObservableField<>("未知");

    public VoiceSwitchItemVH(String nam) {
        name.set(nam);
    }

    public void setCheck(View view) {
        isCheck.set(!isCheck.get());
    }
}
