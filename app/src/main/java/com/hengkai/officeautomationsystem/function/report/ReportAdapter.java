package com.hengkai.officeautomationsystem.function.report;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.network.entity.ReportEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/5/18.
 */
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private List<ReportEntity.DATEBean> mList;
    /**
     * 0 - 日报
     * 1 - 周报
     */
    private int type;

    public ReportAdapter(List<ReportEntity.DATEBean> list, int type) {
        mList = list;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (type == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report_day, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report_week, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReportEntity.DATEBean bean = mList.get(position);
        if (type == 0) {
            holder.tvName.setText(String.format("%s的日报", bean.userName));
        } else {
            holder.tvName.setText(String.format("%s的周报", bean.userName));
        }
        String time = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_1, bean.createTime);
        holder.tvTime.setText(time);
        holder.tvToday.setText(bean.presentContent);
        holder.tvTomorrow.setText(bean.nextContent);
        holder.tvNeedHelp.setText(bean.demandContent);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_today)
        TextView tvToday;
        @BindView(R.id.tv_tomorrow)
        TextView tvTomorrow;
        @BindView(R.id.tv_need_help)
        TextView tvNeedHelp;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
