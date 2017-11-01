package cn.com.reformer.brake.vh;


import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import cn.com.reformer.brake.BR;
import cn.com.reformer.brake.BaseActivity;
import cn.com.reformer.brake.R;
import cn.com.reformer.brake.utils.BleUtils;
import mutils.ThreadUtils;

public class LogVH extends BaseVH {
    public RecyclerView.Adapter adapter;

    public ArrayList<String> results = new ArrayList<>();

    public LogVH(BaseActivity ctx) {
        super(ctx);
    }

    @BindingAdapter({"app:logRefreshLayout"})
    public static void refreshScan(final SwipeRefreshLayout swipeRefreshLayout, final LogVH logVH) {
        if (logVH != null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    BleUtils.send(new byte[]{0x01, 0x03, 0x00, 0x14, 0x00, 0x14});
                    ThreadUtils.runOnUiThreadDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, 1000);
                }
            });
        }
    }

    @BindingAdapter({"app:logRecyclerView"})
    public static void scanAdapter(final RecyclerView recyclerView, final LogVH logVH) {
        if (logVH != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            logVH.adapter = new RecyclerView.Adapter() {
                @Override
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    ViewDataBinding binding = DataBindingUtil.inflate(logVH.mCtx.getLayoutInflater(), R.layout.item_log, parent, false);
                    RecyclerView.ViewHolder viewHolder = new LogItemVH(binding.getRoot());
                    binding.setVariable(BR.logitem, viewHolder);
                    return viewHolder;
                }

                @Override
                public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                    LogItemVH viewHolder = (LogItemVH) holder;
                    viewHolder.content.set(logVH.results.get(position));
                }

                @Override
                public int getItemCount() {
                    return logVH.results.size();
                }
            };
            recyclerView.setAdapter(logVH.adapter);
        }
    }
}
