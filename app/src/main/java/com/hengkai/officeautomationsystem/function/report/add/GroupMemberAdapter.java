package com.hengkai.officeautomationsystem.function.report.add;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.network.entity.ReportContactsEntity;
import com.hengkai.officeautomationsystem.utils.WindowUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/5/19.
 */
public class GroupMemberAdapter extends RecyclerView.Adapter<GroupMemberAdapter.ViewHolder> {

    private List<ReportContactsEntity.DATEBean> mList;
    private Context context;

    /**
     * 初始化页面状态的集合
     */
    private ArrayList<ReportContactsEntity.DATEBean> statusList;


    public GroupMemberAdapter(List<ReportContactsEntity.DATEBean> list, ArrayList<ReportContactsEntity.DATEBean> statusList) {
        mList = list;
        this.statusList = statusList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_group_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ReportContactsEntity.DATEBean bean = mList.get(position);
        if (statusList != null) {
            for (ReportContactsEntity.DATEBean statusBean : statusList) {
                if (statusBean.userId == bean.userId) {
                    bean.checked = statusBean.checked;
                }
            }
        }
        holder.tvName.setText(bean.name);
        if (!TextUtils.isEmpty(bean.iconLink)) {
            Picasso.with(context)
                    .load(bean.iconLink)
                    .error(R.drawable.ic_user64)
//                    .transform(new PicassoCircleTransform())
                    .resize(WindowUtil.dp2px(40, context), WindowUtil.dp2px(40, context))
                    .centerCrop()
                    .into(holder.ivHeader);
        } else {
            holder.ivHeader.setImageResource(R.drawable.ic_user64);
        }
        holder.checkBox.setChecked(bean.checked);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    holder.checkBox.setChecked(false);
                    bean.checked = false;
//                    countList.remove(bean);
                } else {
                    holder.checkBox.setChecked(true);
                    bean.checked = true;
//                    countList.add(bean);
                }
                if (mListener != null) {
                    mListener.onCheckNumber();
                }
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
        @BindView(R.id.check_box)
        CheckBox checkBox;
        LinearLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            item = (LinearLayout) itemView;
        }
    }

    /**
     * 记录数字变化的接口
     */
    public interface OnNumberOfRecordListener {
        void onCheckNumber();
    }

    private OnNumberOfRecordListener mListener;

    public void setOnNumberOfRecordListener(OnNumberOfRecordListener listener) {
        this.mListener = listener;
    }

}
