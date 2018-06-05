package com.hengkai.officeautomationsystem.function.notice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.listener.OnItemClickListener;
import com.hengkai.officeautomationsystem.network.entity.MessageEntity;
import com.hengkai.officeautomationsystem.network.entity.NoticeEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/4/28.
 */
public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.ViewHolder> {

    private Context mContext;
    private List<NoticeEntity.DATEBean> mNoticeList;
    private OnItemClickListener mOnItemClickListener;

    public NoticeListAdapter(OnItemClickListener onItemClickListener, List<NoticeEntity.DATEBean> noticeList) {
        super();
        this.mNoticeList = noticeList;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final NoticeEntity.DATEBean bean = mNoticeList.get(position);
        holder.tvContent.setText(bean.noticeTitle);
        holder.tvSummary.setText(String.format("摘要：%s", bean.noticeSummary));
        holder.tvTime.setText(String.format("发布时间：%s", DateFormatUtils.getFormatedNewsTime(bean.createTime)));
        holder.tvType.setText(bean.noticeTypeName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.itemView, bean, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNoticeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_notice_content)
        TextView tvContent;
        @BindView(R.id.tv_notice_summary)
        TextView tvSummary;
        @BindView(R.id.tv_notice_time)
        TextView tvTime;
        @BindView(R.id.tv_notice_type)
        TextView tvType;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
