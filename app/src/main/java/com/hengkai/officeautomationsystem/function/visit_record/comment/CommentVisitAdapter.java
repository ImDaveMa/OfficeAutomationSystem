package com.hengkai.officeautomationsystem.function.visit_record.comment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.network.entity.CommentVisitEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/5/17.
 */
public class CommentVisitAdapter extends RecyclerView.Adapter<CommentVisitAdapter.ViewHolder> {

    private List<CommentVisitEntity.DATEBean> mList;

    public CommentVisitAdapter(List<CommentVisitEntity.DATEBean> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_visit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentVisitEntity.DATEBean bean = mList.get(position);
        holder.tvContent.setText(bean.comment_content);
        holder.tvName.setText(bean.createUserName);
        holder.tvTime.setText(DateFormatUtils.getFormatedNewsTime(bean.comment_time));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_header)
        ImageView ivHeader;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
