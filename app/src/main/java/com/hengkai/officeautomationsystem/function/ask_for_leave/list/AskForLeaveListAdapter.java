package com.hengkai.officeautomationsystem.function.ask_for_leave.list;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.network.entity.GetAskForLeaveListEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/5/22.
 */
public class AskForLeaveListAdapter extends RecyclerView.Adapter<AskForLeaveListAdapter.ViewHolder> {

    private List<GetAskForLeaveListEntity.DATABean> mList;
    private Context context;

    public AskForLeaveListAdapter(List<GetAskForLeaveListEntity.DATABean> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_ask_for_leave_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GetAskForLeaveListEntity.DATABean bean = mList.get(position);
        holder.tvName.setText(String.format("%s的请假申请单", bean.name));
        holder.tvCreateTime.setText(getTime(bean.create_time));
        switch (bean.leave_type) {
            case 1:
                holder.tvType.setText("请假类型: 零星假");
                break;
            case 2:
                holder.tvType.setText("请假类型: 事假");
                break;
            case 3:
                holder.tvType.setText("请假类型: 病假");
                break;
            case 4:
                holder.tvType.setText("请假类型: 婚假");
                break;
            case 5:
                holder.tvType.setText("请假类型: 产假");
                break;
            case 6:
                holder.tvType.setText("请假类型: 陪产假");
                break;
            case 7:
                holder.tvType.setText("请假类型: 丧假");
                break;
        }
        holder.tvStartTime.setText(String.format("开始时间: %s", getTime(bean.start_time)));
        holder.tvEndTime.setText(String.format("结束时间: %s", getTime(bean.end_time)));
        switch (bean.state) {
            case 0:
                holder.tvState.setText("未审批");
                break;
            case 1:
                holder.tvState.setText("审批通过");
                break;
            case 2:
                holder.tvState.setText("审批未通过");
                break;
            case 3:
                holder.tvState.setText("已撤销");
                break;
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AskForLeaveListDetailActivity.class);
                intent.putExtra("bean", bean);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_create_time)
        TextView tvCreateTime;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_start_time)
        TextView tvStartTime;
        @BindView(R.id.tv_end_time)
        TextView tvEndTime;
        @BindView(R.id.tv_state)
        TextView tvState;
        LinearLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            item = (LinearLayout) itemView;
        }
    }

    private String getTime(long time) {
        return DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_1, time);
    }

}
