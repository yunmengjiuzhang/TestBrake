package cn.com.reformer.brake.vh;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import cn.com.reformer.brake.BR;
import cn.com.reformer.brake.ElectricParamTestNewActivity;
import cn.com.reformer.brake.R;
import cn.com.reformer.brake.utils.BleUtils;

public class ElectricParamTestNewVH {

    private ElectricParamTestNewActivity mCtx;

    public final ObservableField<Boolean> isRefresh = new ObservableField<>(false);


    public ElectricParamTestNewVH(ElectricParamTestNewActivity activity) {
        mCtx = activity;
    }

    public void onBack(View view) {
        mCtx.onBackPressed();
    }

    public void onRefresh(View view) {
        mCtx.refreshDatas();
    }

    public void refreshUI() {
        adapterMain.notifyDataSetChanged();
        adapterSecond.notifyDataSetChanged();
    }

    public void sendDatasMain(View view) {
        getMainChild();
        BleUtils.send(new byte[]{0x01, 0x10, 0x00, 0x0A, 0x00, 0x03, 0x0C, int2bytes(0), int2bytes(1), int2bytes(2), int2bytes(3), int2bytes(4), int2bytes(5), int2bytes(6), int2bytes(7), int2bytes(8), int2bytes(9), int2bytes(10), int2bytes(11)});
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
        BleUtils.send(new byte[]{0x01, 0x10, 0x00, 0x0D, 0x00, 0x03, 0x0C, int2bytes(12), int2bytes(13), int2bytes(14), int2bytes(15), int2bytes(16), int2bytes(17), int2bytes(18), int2bytes(19), int2bytes(20), int2bytes(21), int2bytes(22), int2bytes(23)});
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
        datas[12] = datas[0];
        datas[13] = datas[1];
        datas[14] = datas[2];
        datas[15] = datas[3];
        datas[16] = datas[4];
        datas[17] = datas[5];
        datas[18] = datas[6];
        datas[19] = datas[7];
        datas[20] = datas[8];
        datas[21] = datas[9];
        datas[22] = datas[10];
        datas[23] = datas[11];
        adapterSecond.notifyDataSetChanged();
        BleUtils.send(new byte[]{0x01, 0x10, 0x00, 0x0D, 0x00, 0x03, 0x0C, int2bytes(12), int2bytes(13), int2bytes(14), int2bytes(15), int2bytes(16), int2bytes(17), int2bytes(18), int2bytes(19), int2bytes(20), int2bytes(21), int2bytes(22), int2bytes(23)});
        isRefresh.set(true);
    }

    public void seamMain(View view) {
        getSecondChild();
        datas[0] = datas[12];
        datas[1] = datas[13];
        datas[2] = datas[14];
        datas[3] = datas[15];
        datas[4] = datas[16];
        datas[5] = datas[17];
        datas[6] = datas[18];
        datas[7] = datas[19];
        datas[8] = datas[20];
        datas[9] = datas[21];
        datas[10] = datas[22];
        datas[11] = datas[23];
        adapterMain.notifyDataSetChanged();
        BleUtils.send(new byte[]{0x01, 0x10, 0x00, 0x0A, 0x00, 0x03, 0x0C, int2bytes(0), int2bytes(1), int2bytes(2), int2bytes(3), int2bytes(4), int2bytes(5), int2bytes(6), int2bytes(7), int2bytes(8), int2bytes(9), int2bytes(10), int2bytes(11)});
        isRefresh.set(true);
    }

    private byte int2bytes(int i) {
        return (byte) (datas[i] & 0xff);
    }

    private RecyclerView.Adapter adapterMain;
    private RecyclerView mainView;
    private RecyclerView secondView;
    private RecyclerView.Adapter adapterSecond;
    public int[] datas = new int[]{75, 15, 75, 15, 75, 15, 75, 13, 75, 13, 75, 13, 75, 15, 75, 15, 75, 15, 75, 13, 75, 13, 75, 13};
    private String[] names = new String[]{"左开速度", "左开角度", "左关速度", "左关角度", "左停速度", "左停角度", "右开速度", "右开角度", "右关速度", "右关角度", "右停速度", "右停角度"};

    @BindingAdapter({"app:electrictestmain"})
    public static void setelectmainAdapter(final RecyclerView recyclerView, final ElectricParamTestNewVH mainVH) {
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
    public static void setelectsecondAdapter(final RecyclerView recyclerView, final ElectricParamTestNewVH mainVH) {
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
}
