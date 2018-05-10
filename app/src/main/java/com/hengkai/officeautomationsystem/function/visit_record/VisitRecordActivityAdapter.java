package com.hengkai.officeautomationsystem.function.visit_record;

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
import com.hengkai.officeautomationsystem.function.visit_record.detail.VisitRecordDetailActivity;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/5/7.
 */
public class VisitRecordActivityAdapter extends RecyclerView.Adapter<VisitRecordActivityAdapter.ViewHolder> {

    private List<VisitRecordEntity.DATABean> mList;
    private Context context;

    public VisitRecordActivityAdapter(List<VisitRecordEntity.DATABean> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_visit_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final VisitRecordEntity.DATABean bean = mList.get(position);
        holder.tvUnitName.setText(bean.companyName);
        if (!bean.isSubmission) {
            holder.tvStateType.setText("已提交");
        } else {
            holder.tvStateType.setText("保存中");
        }
        switch (bean.type) {
            case "0":   //跟进
                holder.tvType.setText("跟进");
                break;
            case "1":   //拜访
                holder.tvType.setText("拜访");
                break;
            case "2":   //招待
                holder.tvType.setText("招待");
                break;
        }
        holder.tvDepartment.setText(bean.department);
        holder.tvContactsName.setText(bean.contactsName);
        String startTime = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_1, bean.startTime);
        String endTime = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_1, bean.endTime);
        holder.tvTime.setText(String.format("%s至%s", startTime, endTime));
        holder.tvUserName.setText(bean.userName);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(bean);
                }
            }
        });

        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mListener != null) {
                    mListener.onLongClick(bean, holder.getAdapterPosition());
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_unit_name)
        TextView tvUnitName;
        @BindView(R.id.tv_state_type)
        TextView tvStateType;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_department)
        TextView tvDepartment;
        @BindView(R.id.tv_contacts_name)
        TextView tvContactsName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;

        LinearLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            container = (LinearLayout) itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onClick(VisitRecordEntity.DATABean bean);

        void onLongClick(VisitRecordEntity.DATABean bean, int position);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void updateData(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }
}
