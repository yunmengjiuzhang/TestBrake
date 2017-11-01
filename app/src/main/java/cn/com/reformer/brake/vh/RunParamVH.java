package cn.com.reformer.brake.vh;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import cn.com.reformer.brake.RunParamActivity;
import cn.com.reformer.brake.WorkPatternActivity;
import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.utils.UIUtils;
import cn.com.reformer.brake.views.picker.PickerDialog;
import mutils.ByteUtils;
import mutils.ThreadUtils;

public class RunParamVH extends BaseVH {

    public final ObservableField<String> workpattenStr = new ObservableField<>("未知模式");
    public final ObservableField<String> machineSpeedStr = new ObservableField<>("中速");
    public final ObservableField<Boolean> voiceSwitchBoo = new ObservableField<>(true);
    public final ObservableField<Boolean> oneCardBoo = new ObservableField<>(true);
    public final ObservableField<Boolean> isRefresh = new ObservableField<>(false);
    //    public final ObservableField<Boolean> barVisible = new ObservableField<>(false);
    public final ObservableField<Integer> cardAuthorityInt = new ObservableField<>();
    public final ObservableField<Integer> infraredInt = new ObservableField<>();
    public final ObservableField<Integer> occupyWayInt = new ObservableField<>();
    public final ObservableField<Integer> voiceNextInt = new ObservableField<>();
    public final ObservableField<Integer> wayCloseInt = new ObservableField<>();
    private PickerDialog mPickerDialog;
    private RunParamActivity mCtx;

    public RunParamVH(RunParamActivity ctx) {
        super(ctx);
        mCtx = ctx;
    }

    private TextView textView;

    @BindingAdapter({"app:tempworkpattern"})
    public static void refreshScan(final TextView textView, final RunParamVH runParamVH) {
        if (runParamVH != null) {
            runParamVH.textView = textView;
        }
    }

    public void workPattern(View view) {
        UIUtils.startActivity(new Intent(UIUtils.getContext(), WorkPatternActivity.class));
    }

    private ArrayList<String> priDatas = new ArrayList<String>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"));

    public void cardAuthority(View view) {
        mPickerDialog = new PickerDialog(mCtx);
        mPickerDialog.setTitle("卡权限保持时间");
        mPickerDialog.setData(priDatas);
        mPickerDialog.setYesOnclickListener("确定", new PickerDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(String messge) {
                final int i = Integer.parseInt(messge) * 10;
                byte[] bytes = ByteUtils.int2bytes1(i);
                BleUtils.send(new byte[]{0x01, 0x10, 0x00, 0x02, 0x00, 0x01, 0x02, bytes[0], bytes[1]});
                mPickerDialog.dismiss();
            }
        });
        mPickerDialog.setNoOnclickListener("取消", new PickerDialog.onNoOnclickListener() {
            @Override
            public void onNoClick(String messge) {
                mPickerDialog.dismiss();
            }
        });
        mPickerDialog.show();
    }

    public void infrared(View view) {
        mPickerDialog = new PickerDialog(mCtx);
        mPickerDialog.setTitle("红外触发权限保持时间");
        mPickerDialog.setData(priDatas);
        mPickerDialog.setYesOnclickListener("确定", new PickerDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(String messge) {
                final int i = Integer.parseInt(messge) * 10;
                byte[] bytes = ByteUtils.int2bytes1(i);
                BleUtils.send(new byte[]{0x01, 0x10, 0x00, 0x01, 0x00, 0x01, 0x02, bytes[0], bytes[1]});
                mPickerDialog.dismiss();
            }
        });
        mPickerDialog.setNoOnclickListener("取消", new PickerDialog.onNoOnclickListener() {
            @Override
            public void onNoClick(String messge) {
                mPickerDialog.dismiss();
            }
        });
        mPickerDialog.show();
    }

    public void occupyWay(View view) {
        mPickerDialog = new PickerDialog(mCtx);
        mPickerDialog.setTitle("占用通道超时报警");
        mPickerDialog.setData(priDatas);
        mPickerDialog.setYesOnclickListener("确定", new PickerDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(String messge) {
                final int i = Integer.parseInt(messge) * 10;
                byte[] bytes = ByteUtils.int2bytes1(i);
                BleUtils.send(new byte[]{0x01, 0x10, 0x00, 0x03, 0x00, 0x01, 0x02, bytes[0], bytes[1]});
                mPickerDialog.dismiss();
            }
        });
        mPickerDialog.setNoOnclickListener("取消", new PickerDialog.onNoOnclickListener() {
            @Override
            public void onNoClick(String messge) {
                mPickerDialog.dismiss();
            }
        });
        mPickerDialog.show();
    }

    public void voiceNext(View view) {
        mPickerDialog = new PickerDialog(mCtx);
        mPickerDialog.setTitle("语音再播时间");
        mPickerDialog.setData(priDatas);
        mPickerDialog.setYesOnclickListener("确定", new PickerDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(String messge) {
                final int i = Integer.parseInt(messge) * 10;
                byte[] bytes = ByteUtils.int2bytes1(i);
                BleUtils.send(new byte[]{0x01, 0x10, 0x00, 0x06, 0x00, 0x01, 0x02, bytes[0], bytes[1]});
                mPickerDialog.dismiss();
            }
        });
        mPickerDialog.setNoOnclickListener("取消", new PickerDialog.onNoOnclickListener() {
            @Override
            public void onNoClick(String messge) {
                mPickerDialog.dismiss();
            }
        });
        mPickerDialog.show();
    }

    public void wayClose(View view) {
        mPickerDialog = new PickerDialog(mCtx);
        mPickerDialog.setTitle("通道关门延时");
        mPickerDialog.setData(priDatas);
        mPickerDialog.setYesOnclickListener("确定", new PickerDialog.onYesOnclickListener() {
            @Override
            public void onYesClick(String messge) {
                final int i = Integer.parseInt(messge) * 10;
                byte[] bytes = ByteUtils.int2bytes1(i);
                BleUtils.send(new byte[]{0x01, 0x10, 0x00, 0x05, 0x00, 0x01, 0x02, bytes[0], bytes[1]});
                mPickerDialog.dismiss();
            }
        });
        mPickerDialog.setNoOnclickListener("取消", new PickerDialog.onNoOnclickListener() {
            @Override
            public void onNoClick(String messge) {
                mPickerDialog.dismiss();
            }
        });
        mPickerDialog.show();
    }

    public void refresh(View view) {
        mCtx.refresh();
    }

    public String getString(boolean a) {
        return a ? "开启" : "关闭";
    }

    @BindingAdapter({"app:paramRefresh"})
    public static void refreshScan(final SwipeRefreshLayout swipeRefreshLayout, final RunParamVH logVH) {
        if (logVH != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    logVH.mCtx.refresh();
                    ThreadUtils.runOnUiThreadDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, 400);
                }
            });
        }
    }
}
