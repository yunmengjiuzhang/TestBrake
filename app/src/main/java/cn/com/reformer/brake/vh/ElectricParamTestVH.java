package cn.com.reformer.brake.vh;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import cn.com.reformer.brake.BR;
import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.R;
import cn.com.reformer.brake.utils.BleUtils;
import mutils.ThreadUtils;

public class ElectricParamTestVH extends BaseVH {


    public final ObservableField<Boolean> isRefresh = new ObservableField<>(false);

    public ElectricParamTestVH(BaseActivity ctx) {
        super(ctx);
    }

    public void onRefresh(View view) {
        refreshDatas();
    }

    public int time = 0;

    public void refreshDatas() {
        time = 0;
        isRefresh.set(true);
        BleUtils.send(new byte[]{0x01, 0x03, 0x00, 0x0A, 0x00, 0x03});
        ThreadUtils.runOnUiThreadDelayed(new Runnable() {
            @Override
            public void run() {
                BleUtils.send(new byte[]{0x01, 0x03, 0x00, 0x0D, 0x00, 0x03});
            }
        }, 500);
    }

    public void sendDatasMain(View view) {
        getMainChild();
        BleUtils.send(new byte[]{0x01, 0x10, 0x00, 0x0A, 0x00, 0x03, 0x06, int2bytes(0), int2bytes(1), int2bytes(2), int2bytes(3), int2bytes(4), int2bytes(5)});
        isRefresh.set(true);
    }

    private void getMainChild() {
        int childCount = mainView.getChildCount();
        ElectricParamTestItemVH itemVH;
        for (int i = 0; i < childCount; i++) {
            itemVH = (ElectricParamTestItemVH) mainView.getChildViewHolder(mainView.getChildAt(i));
            datas[i] = itemVH.num.get();
        }
    }

    public void sendDatasSecond(View view) {
        getSecondChild();
        BleUtils.send(new byte[]{0x01, 0x10, 0x00, 0x0D, 0x00, 0x03, 0x06, int2bytes(6), int2bytes(7), int2bytes(8), int2bytes(9), int2bytes(10), int2bytes(11)});
        isRefresh.set(true);
    }

    private void getSecondChild() {
        int childCount = secondView.getChildCount();
        ElectricParamTestItemVH itemVH;
        for (int i = 0; i < childCount; i++) {
            itemVH = (ElectricParamTestItemVH) secondView.getChildViewHolder(secondView.getChildAt(i));
            datas[i + 6] = itemVH.num.get();
        }
    }

    public void seamSecond(View view) {
        getMainChild();
        datas[6] = datas[0];
        datas[7] = datas[1];
        datas[8] = datas[2];
        datas[9] = datas[3];
        datas[10] = datas[4];
        datas[11] = datas[5];
        adapterSecond.notifyDataSetChanged();
        BleUtils.send(new byte[]{0x01, 0x10, 0x00, 0x0D, 0x00, 0x03, 0x06, int2bytes(6), int2bytes(7), int2bytes(8), int2bytes(9), int2bytes(10), int2bytes(11)});
        isRefresh.set(true);
    }

    public void seamMain(View view) {
        getSecondChild();
        datas[0] = datas[6];
        datas[1] = datas[7];
        datas[2] = datas[8];
        datas[3] = datas[9];
        datas[4] = datas[10];
        datas[5] = datas[11];
        adapterMain.notifyDataSetChanged();
        BleUtils.send(new byte[]{0x01, 0x10, 0x00, 0x0A, 0x00, 0x03, 0x06, int2bytes(0), int2bytes(1), int2bytes(2), int2bytes(3), int2bytes(4), int2bytes(5)});
        isRefresh.set(true);
    }

    private byte int2bytes(int i) {
        return (byte) (datas[i] & 0xff);
    }

    public RecyclerView.Adapter adapterMain;
    private RecyclerView mainView;
    private RecyclerView secondView;
    public RecyclerView.Adapter adapterSecond;
    public int[] datas = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private String[] names = new String[]{"开速度", "开角度", "关速度", "关角度", "停速度", "停角度"};

    @BindingAdapter({"app:electrictestmain"})
    public static void setelectmainAdapter(final RecyclerView recyclerView, final ElectricParamTestVH mainVH) {
        if (mainVH != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    ViewDataBinding binding = DataBindingUtil.inflate(mainVH.mCtx.getLayoutInflater(), R.layout.item_electric_param_test, parent, false);
                    ElectricParamTestItemVH viewHolder = new ElectricParamTestItemVH(binding);
                    binding.setVariable(BR.electTest, viewHolder);
                    return viewHolder;
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                    ElectricParamTestItemVH viewHolder = (ElectricParamTestItemVH) holder;
                    viewHolder.num.set(mainVH.datas[position]);
                    viewHolder.title.set(mainVH.names[position]);
                    viewHolder.getBinding().executePendingBindings();
                }

                @Override
                public int getItemCount() {
                    return mainVH.names.length;
                }
            };
            recyclerView.setAdapter(adapter);
            mainVH.mainView = recyclerView;
            mainVH.adapterMain = adapter;
        }
    }

    @BindingAdapter({"app:electrictestsecond"})
    public static void setelectsecondAdapter(final RecyclerView recyclerView, final ElectricParamTestVH mainVH) {
        if (mainVH != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            RecyclerView.Adapter adapter = new RecyclerView.Adapter() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    ViewDataBinding binding = DataBindingUtil.inflate(mainVH.mCtx.getLayoutInflater(), R.layout.item_electric_param_test, parent, false);
                    ElectricParamTestItemVH viewHolder = new ElectricParamTestItemVH(binding);
                    binding.setVariable(BR.electTest, viewHolder);
                    return viewHolder;
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                    ElectricParamTestItemVH viewHolder = (ElectricParamTestItemVH) holder;
                    viewHolder.num.set(mainVH.datas[position + 6]);
                    viewHolder.title.set(mainVH.names[position]);
                    viewHolder.getBinding().executePendingBindings();
                }

                @Override
                public int getItemCount() {
                    return mainVH.names.length;
                }
            };
            recyclerView.setAdapter(adapter);
            mainVH.adapterSecond = adapter;
            mainVH.secondView = recyclerView;
        }
    }

    @BindingAdapter({"app:elecParameTest"})
    public static void refreshScan(final SwipeRefreshLayout swipeRefreshLayout, final ElectricParamTestVH logVH) {
        if (logVH != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    logVH.refreshDatas();
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
