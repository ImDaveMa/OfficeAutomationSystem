package com.hengkai.officeautomationsystem.function.message;

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
import com.hengkai.officeautomationsystem.network.entity.MessageEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/4/28.
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    private Context mContext;
    private List<MessageEntity.MsgBean> mMessageList;
    private OnItemClickListener mOnItemClickListener;

    public MessageListAdapter(OnItemClickListener onItemClickListener, List<MessageEntity.MsgBean> msgList) {
        super();
        this.mMessageList = msgList;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final MessageEntity.MsgBean bean = mMessageList.get(position);
        String op = "";
        if(bean.getNews_type() == MessageListModel.STATE_APPROVE){
            op = "";
        } else if(bean.getNews_type() == MessageListModel.STATE_MESSAGE){
            op = "消息";
        }
        holder.tvContent.setText(String.format("您收到了一条%s%s",bean.getTypeName(),op));
        holder.tvCreator.setText(bean.getCreateName());
        holder.tvTime.setText(String.format("消息时间：%s", DateFormatUtils.getFormatedNewsTime(bean.getCreateTime())));
        String state = "";
        int color = R.color.blue;
        if(bean.getNews_type() == MessageListModel.STATE_APPROVE) { // 审核状态
            switch (bean.getState()) {
                case 0:
                    state = "待审核";
                    color = R.color.state_orange;
                    break;
                case 1:
                    state = "已通过";
                    color = R.color.state_green;
                    break;
                case 2:
                    state = "已拒绝";
                    color = R.color.state_red;
                    break;
                case 3:
                    state = "已撤销";
                    color = R.color.state_blue;
                    break;
            }
        } else if(bean.getNews_type() == MessageListModel.STATE_MESSAGE) { // 消息状态
            switch (bean.getState()) {
                case 0:
                    state = "未读";
                    color = R.color.state_orange;
                    break;
                case 1:
                    state = "已读";
                    color = R.color.state_blue;
                    break;
            }
        }
        holder.tvType.setText(state);
        holder.tvType.setTextColor(mContext.getResources().getColor(color));
        holder.ivTip.setVisibility(bean.getHomeState() == 0 ? View.VISIBLE : View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.itemView, bean, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_creator)
        TextView tvCreator;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.iv_tip)
        ImageView ivTip;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
