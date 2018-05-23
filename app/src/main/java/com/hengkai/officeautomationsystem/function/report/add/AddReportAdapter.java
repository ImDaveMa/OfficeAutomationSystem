package com.hengkai.officeautomationsystem.function.report.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.network.entity.ReportContactsEntity;
import com.hengkai.officeautomationsystem.utils.WindowUtil;
import com.luck.picture.lib.entity.LocalMedia;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/5/19.
 */
public class AddReportAdapter extends RecyclerView.Adapter<AddReportAdapter.ViewHolder> {

    private ArrayList<ReportContactsEntity.DATEBean> mList;
    private Context context;

    public AddReportAdapter() {
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_resport, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if (position == 0) {
            holder.ivHeader.setImageResource(R.drawable.ic_add128);
            holder.tvName.setVisibility(View.GONE);
            holder.ivHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClick();
                    }
                }
            });
        } else {
            ReportContactsEntity.DATEBean bean = mList.get(position - 1);
            holder.tvName.setVisibility(View.VISIBLE);
            holder.tvName.setText(bean.name);
            if (!TextUtils.isEmpty(bean.iconLink)) {
                Picasso.with(context)
                        .load(URLFinal.BASE_URL + bean.iconLink)
                        .error(R.drawable.ic_user64)
//                    .transform(new PicassoCircleTransform())
                        .resize(WindowUtil.dp2px(40, context), WindowUtil.dp2px(40, context))
                        .centerCrop()
                        .into(holder.ivHeader);
            } else {
                holder.ivHeader.setImageResource(R.drawable.ic_user64);
            }
            holder.ivHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mList.remove(holder.getAdapterPosition() - 1);
                        mListener.onHeaderClick(holder.getAdapterPosition());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
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

    public void addItems(List<ReportContactsEntity.DATEBean> list) {
        mList.clear();
        mList.addAll(0, list);
        notifyDataSetChanged();
    }

}
