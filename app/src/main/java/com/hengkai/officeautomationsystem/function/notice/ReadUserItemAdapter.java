package com.hengkai.officeautomationsystem.function.notice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.network.entity.NoticeDetailEntity;
import com.hengkai.officeautomationsystem.utils.PicassoCircleTransform;
import com.hengkai.officeautomationsystem.utils.WindowUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/5/19.
 */
public class ReadUserItemAdapter extends RecyclerView.Adapter<ReadUserItemAdapter.ViewHolder> {

    private List<NoticeDetailEntity.DATEBean.AlrListBean> mList;
    private Context context;

    public ReadUserItemAdapter(List<NoticeDetailEntity.DATEBean.AlrListBean> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        NoticeDetailEntity.DATEBean.AlrListBean bean = mList.get(position);
        holder.tvName.setText(bean.userName);
        if (!TextUtils.isEmpty(bean.userAvatar)) {
            Picasso.with(context)
                    .load(bean.userAvatar)
                    .error(R.drawable.ic_user_blue)
                    .transform(new PicassoCircleTransform())
                    .resize(WindowUtil.dp2px(40, context), WindowUtil.dp2px(40, context))
                    .centerCrop()
                    .into(holder.ivHeader);
        } else {
            holder.ivHeader.setImageResource(R.drawable.ic_user_blue);
        }
        holder.ivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onClick();

        void onHeaderClick(int position);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public void addItems(List<NoticeDetailEntity.DATEBean.AlrListBean> list) {
        mList.clear();
        mList.addAll(0, list);
        notifyDataSetChanged();
    }

}
