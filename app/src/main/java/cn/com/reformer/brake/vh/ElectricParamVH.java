package cn.com.reformer.brake.vh;


import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import java.util.ArrayList;

import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.brake.views.spinner.AbstractSpinerAdapter;
import cn.com.reformer.brake.views.spinner.SpinerPopWindow;
import mutils.ThreadUtils;

public class ElectricParamVH extends BaseVH {
    public final ObservableField<Boolean> isRefresh = new ObservableField<>(false);
    public final ObservableField<Boolean> isMain = new ObservableField<>(true);
    public final ObservableField<String> openSpeed = new ObservableField<>("未知");
    public final ObservableField<String> closeSpeed = new ObservableField<>("未知");
    public final ObservableField<String> resetSpeed = new ObservableField<>("未知");

    public int[] datas = new int[]{75, 25, 75, 5, 60, 5, 75, 25, 75, 5, 60, 5};

    private SpinerPopWindow mSpinerPopWindow2;

    public ElectricParamVH(BaseActivity ctx) {
        super(ctx);
    }

    public void onRefresh(View view) {
        isRefresh.set(true);
        BleUtils.send(new byte[]{0x01, 0x03, 0x00, (byte) (isMain.get() ? 0x0A : 0x0D), 0x00, 0x03});
    }

    public void onOpenClick(View view) {
        init2(view, 0);
    }

    public void onCloseClick(View view) {
        init2(view, 1);
    }

    public void onAutoResetClick(View view) {
        init2(view, 2);
    }

    private void sendcmd() {
        int isMainCmd = isMain.get() ? 0 : 6;
        BleUtils.send(new byte[]{0x01, 0x10, 0x00, (byte) (isMain.get() ? 0x0A : 0x0D), 0x00, 0x03, 0x06, int2bytes(0 + isMainCmd), int2bytes(1 + isMainCmd), int2bytes(2 + isMainCmd), int2bytes(3 + isMainCmd), int2bytes(4 + isMainCmd), int2bytes(5 + isMainCmd)});
    }

    private void init2(View view, final int position) {
        final ArrayList<String> titles = new ArrayList<>();
        titles.add("超低速");
        titles.add("低速");
        titles.add("中速");
        titles.add("高速");
        titles.add("超高速");
        mSpinerPopWindow2 = new SpinerPopWindow(mCtx);
        mSpinerPopWindow2.refreshData(titles, 0);
        mSpinerPopWindow2.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
            @Override
            public void onItemClick(int index) {
                int isMainCmd = isMain.get() ? 0 : 6;
                switch (position) {
                    case 0:
                        openSpeed.set(titles.get(index));
                        switch (index) {
                            case 0:
                                datas[0 + isMainCmd] = 65;
                                datas[1 + isMainCmd] = 25;
                                break;
                            case 1:
                                datas[0 + isMainCmd] = 70;
                                datas[1 + isMainCmd] = 25;
                                break;
                            case 2:
                                datas[0 + isMainCmd] = 75;
                                datas[1 + isMainCmd] = 25;
                                break;
                            case 3:
                                datas[0 + isMainCmd] = 80;
                                datas[1 + isMainCmd] = 25;
                                break;
                            case 4:
                                datas[0 + isMainCmd] = 85;
                                datas[1 + isMainCmd] = 29;
                                break;
                        }
                        break;
                    case 1:
                        closeSpeed.set(titles.get(index));
                        switch (index) {
                            case 0:
                                datas[2 + isMainCmd] = 65;
                                datas[3 + isMainCmd] = 25;
                                break;
                            case 1:
                                datas[2 + isMainCmd] = 70;
                                datas[3 + isMainCmd] = 25;
                                break;
                            case 2:
                                datas[2 + isMainCmd] = 75;
                                datas[3 + isMainCmd] = 25;
                                break;
                            case 3:
                                datas[2 + isMainCmd] = 80;
                                datas[3 + isMainCmd] = 25;
                                break;
                            case 4:
                                datas[2 + isMainCmd] = 85;
                                datas[3 + isMainCmd] = 29;
                                break;
                        }
                        break;
                    case 2:
                        resetSpeed.set(titles.get(index));
                        switch (index) {
                            case 0:
                                datas[4 + isMainCmd] = 60;
                                datas[5 + isMainCmd] = 5;
                                break;
                            case 1:
                                datas[4 + isMainCmd] = 65;
                                datas[5 + isMainCmd] = 6;
                                break;
                            case 2:
                                datas[4 + isMainCmd] = 70;
                                datas[5 + isMainCmd] = 7;
                                break;
                            case 3:
                                datas[4 + isMainCmd] = 75;
                                datas[5 + isMainCmd] = 9;
                                break;
                            case 4:
                                datas[4 + isMainCmd] = 80;
                                datas[5 + isMainCmd] = 10;
                                break;
                        }
                        break;
                }
                sendcmd();
            }
        });
        mSpinerPopWindow2.setWidth(view.getWidth() * 2);
        mSpinerPopWindow2.showAsDropDown(view);
    }

    private byte int2bytes(int i) {
        return (byte) (datas[i] & 0xff);
    }

    public void onTabMain(View view) {
        if (!isMain.get())
            isMain.set(true);
        BleUtils.send(new byte[]{0x01, 0x03, 0x00, 0x0A, 0x00, 0x03});
    }

    public void onTabSecond(View view) {
        if (isMain.get())
            isMain.set(false);
        BleUtils.send(new byte[]{0x01, 0x03, 0x00, 0x0D, 0x00, 0x03});
    }

    public void refreshMain() {
        if (datas[0] <= 65) {
            openSpeed.set("超低速");
        } else if (datas[0] <= 70) {
            openSpeed.set("低速");
        } else if (datas[0] <= 75) {
            openSpeed.set("中速");
        } else if (datas[0] <= 80) {
            openSpeed.set("高速");
        } else {
            openSpeed.set("超高速");
        }
        if (datas[2] <= 65) {
            closeSpeed.set("超低速");
        } else if (datas[2] <= 70) {
            closeSpeed.set("低速");
        } else if (datas[2] <= 75) {
            closeSpeed.set("中速");
        } else if (datas[2] <= 80) {
            closeSpeed.set("高速");
        } else {
            closeSpeed.set("超高速");
        }
        if (datas[4] <= 60) {
            resetSpeed.set("超低速");
        } else if (datas[4] <= 65) {
            resetSpeed.set("低速");
        } else if (datas[4] <= 70) {
            resetSpeed.set("中速");
        } else if (datas[4] <= 75) {
            resetSpeed.set("高速");
        } else {
            resetSpeed.set("超高速");
        }
    }

    public void refreshSecond() {
        if (datas[6] <= 65) {
            openSpeed.set("超低速");
        } else if (datas[6] <= 70) {
            openSpeed.set("低速");
        } else if (datas[6] <= 75) {
            openSpeed.set("中速");
        } else if (datas[6] <= 80) {
            openSpeed.set("高速");
        } else {
            openSpeed.set("超高速");
        }
        if (datas[8] <= 65) {
            closeSpeed.set("超低速");
        } else if (datas[8] <= 70) {
            closeSpeed.set("低速");
        } else if (datas[8] <= 75) {
            closeSpeed.set("中速");
        } else if (datas[8] <= 80) {
            closeSpeed.set("高速");
        } else {
            closeSpeed.set("超高速");
        }
        if (datas[10] <= 60) {
            resetSpeed.set("超低速");
        } else if (datas[10] <= 65) {
            resetSpeed.set("低速");
        } else if (datas[10] <= 70) {
            resetSpeed.set("中速");
        } else if (datas[10] <= 75) {
            resetSpeed.set("高速");
        } else {
            resetSpeed.set("超高速");
        }
    }

    @BindingAdapter({"app:elecParame"})
    public static void refreshScan(final SwipeRefreshLayout swipeRefreshLayout, final ElectricParamVH logVH) {
        if (logVH != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    logVH.isRefresh.set(true);
                    BleUtils.send(new byte[]{0x01, 0x03, 0x00, (byte) (logVH.isMain.get() ? 0x0A : 0x0D), 0x00, 0x03});
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
