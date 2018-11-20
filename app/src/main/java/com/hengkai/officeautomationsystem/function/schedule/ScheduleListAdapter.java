package com.hengkai.officeautomationsystem.function.schedule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.listener.OnItemClickListener;
import com.hengkai.officeautomationsystem.network.entity.ScheduleEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder> {

    private List<ScheduleEntity.ScheduleBean> mScheduleList;
    private OnItemClickListener mOnItemClickListener;

    public ScheduleListAdapter(OnItemClickListener onItemClickListener, List<ScheduleEntity.ScheduleBean> msgList) {
        super();
        this.mScheduleList = msgList;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ScheduleEntity.ScheduleBean bean = mScheduleList.get(position);

        holder.tvTime.setText(bean.memoStartDate+" 至 "+bean.memoEndDate);
        holder.tvContent.setText(bean.memoContent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.itemView, bean, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mScheduleList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_schedule_item_time)
        TextView tvTime;
        @BindView(R.id.tv_schedule_item_content)
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
