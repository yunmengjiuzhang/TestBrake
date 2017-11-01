package cn.com.reformer.brake.vh;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.com.reformer.brake.BR;
import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.R;
import cn.com.reformer.brake.utils.BleUtils;
import cn.com.reformer.ble.BleBean;
import mutils.ThreadUtils;

public class ScanVH extends BaseVH {

    public final ObservableField<Boolean> isRefresh = new ObservableField<>(true);

    public final ObservableArrayList<BleBean> itmesVHs = new ObservableArrayList<>();

    public ScanVH(BaseActivity ctx) {
        super(ctx);
    }

    public void onRefreshClick(View view) {
        refresh();
    }

    public void refresh() {
        itmesVHs.clear();
        isRefresh.set(true);
        BleUtils.scan();
        ThreadUtils.runOnUiThreadDelayed(new Runnable() {
            @Override
            public void run() {
                isRefresh.set(false);
            }
        }, 5000);
    }

    public void OnNewBleBean(final BleBean bean) {
        itmesVHs.add(bean);
    }

    @BindingAdapter({"app:scanRecyclerView", "app:arraylist"})
    public static void scanAdapter(final RecyclerView recyclerView, final ScanVH scanVH, final ArrayList<BleBean> arrayList) {
        if (scanVH != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());// TODO: 2017/8/3 0003
            recyclerView.setAdapter(new RecyclerView.Adapter() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    ViewDataBinding binding = null;
                    RecyclerView.ViewHolder viewHolder = null;
                    switch (viewType) {
                        case 0:
                            binding = DataBindingUtil.inflate(scanVH.mCtx.getLayoutInflater(), R.layout.item_scan, parent, false);
                            viewHolder = new ScanItemVH(binding);
                            binding.setVariable(BR.scanitem, viewHolder);
                            break;
                        case 1:
                            binding = DataBindingUtil.inflate(scanVH.mCtx.getLayoutInflater(), R.layout.item_scan2, parent, false);
                            viewHolder = new ScanItemVH2(binding);
                            binding.setVariable(BR.scanitem2, viewHolder);
                            break;
                    }
                    return viewHolder;
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                    if (getItemViewType(position) == 0) {
                        ScanItemVH viewHolder = (ScanItemVH) holder;
//                        BleBean bleBean = scanVH.ScanItemVHs.get(position);
                        BleBean bleBean = arrayList.get(position);
                        viewHolder.mBean = bleBean;
                        viewHolder.mMac.set(bleBean.name + " " + bleBean.getMacStr() + " " + bleBean.rssi);
                        viewHolder.getBinding().executePendingBindings();
                    } else {
                        ScanItemVH2 viewHolder = (ScanItemVH2) holder;
                        BleBean bleBean = arrayList.get(position);
//                        BleBean bleBean = scanVH.ScanItemVHs.get(position);
                        viewHolder.mBean = bleBean;
                        viewHolder.mMac.set(bleBean.name + " " + bleBean.getMacStr() + " " + bleBean.rssi);
                        viewHolder.getBinding().executePendingBindings();
                    }
                }

                @Override
                public int getItemCount() {
                    return arrayList.size();
                }

                @Override
                public int getItemViewType(int position) {
                    String name = arrayList.get(position).name;
                    if (name != null && name.contains("RF")) {
                        return 1;
                    } else {
                        return 0;
                    }
                }

                @Override
                public long getItemId(int position) {
                    return super.getItemId(position);
                }
            });
        }
    }

    @BindingAdapter({"app:swipeRefreshLayout"})
    public static void refreshScan(final SwipeRefreshLayout swipeRefreshLayout, final ScanVH scanVH) {
        if (scanVH != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    scanVH.refresh();
                    ThreadUtils.runOnUiThreadDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                            scanVH.isRefresh.set(false);
                        }
                    }, 1000);
                }
            });
        }
    }
}
