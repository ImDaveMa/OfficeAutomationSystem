package com.hengkai.officeautomationsystem.function.work_platform;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/4/26.
 */
public class WorkPlatformFragmentAdapter extends RecyclerView.Adapter<WorkPlatformFragmentAdapter.ViewHolder> {

    private final String[] names = {"通用", "员工", "项目", "物品"};
    private Context context;
    private Activity activity;

    public WorkPlatformFragmentAdapter(Activity activity) {
        super();
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_work_platform_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(names[position]);
        holder.rvCommon.setLayoutManager(new GridLayoutManager(context, 4){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        holder.rvCommon.setAdapter(new WorkPlatformFragmentGridLayoutAdapter(activity, position));
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.rv_common)
        RecyclerView rvCommon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
