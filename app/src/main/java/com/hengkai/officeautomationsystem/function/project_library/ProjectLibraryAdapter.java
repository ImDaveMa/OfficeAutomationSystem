package com.hengkai.officeautomationsystem.function.project_library;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.listener.OnItemClickListener;
import com.hengkai.officeautomationsystem.network.entity.ProjectEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;
import com.hengkai.officeautomationsystem.utils.MoneyUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/4/28.
 */
public class ProjectLibraryAdapter extends RecyclerView.Adapter<ProjectLibraryAdapter.ViewHolder> {

    private List<ProjectEntity.ProjectBean> mGoodsOutList;
    private OnItemClickListener mOnItemClickListener;

    public ProjectLibraryAdapter(OnItemClickListener onItemClickListener, List<ProjectEntity.ProjectBean> goodsOutList) {
        super();
        this.mGoodsOutList = goodsOutList;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ProjectEntity.ProjectBean bean = mGoodsOutList.get(position);
        holder.tvProjectName.setText(bean.getProjectName());
        holder.tvProjectType.setText(bean.getProjectType());
        holder.tvProjectPrincipalUser.setText(String.format("负责人：%s",bean.getPrincipalUserName()));
        holder.tvProjectCreateUser.setText(String.format("发布人：%s",bean.getCreateName()));
        if(!TextUtils.isEmpty(bean.getProjectSummarize())) {
            holder.tvProjectSummaries.setText(String.format("概述：%s", bean.getProjectSummarize()));
            holder.tvProjectSummaries.setVisibility(View.VISIBLE);
        } else {
            holder.tvProjectSummaries.setVisibility(View.GONE);
        }
        String createTime = bean.getCreateTime();
        if(TextUtils.isEmpty(createTime)) {
            createTime = "暂无";
        } else {
            createTime = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_1, Long.valueOf(bean.getCreateTime()));
        }
        holder.tvProjectCreateTime.setText(String.format("发布时间：%s",createTime));
        String qualifiedTime = bean.getQualifiedTime();
        if(TextUtils.isEmpty(qualifiedTime)) {
            qualifiedTime = "暂无";
        } else {
            qualifiedTime = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_1, Long.valueOf(bean.getQualifiedTime()));
        }
        holder.tvProjectQualifiedTime.setText(String.format("验收时间：%s",qualifiedTime));
        String money = bean.getDealMoney();
        if(!TextUtils.isEmpty(money)) {
            double dealMoney = Double.parseDouble(money);
            String total = MoneyUtils.getUnitMoneyStr(dealMoney);
            holder.tvProjectDealMoney.setText(String.format("成交金额：%s", total));
        } else {
            holder.tvProjectDealMoney.setText("成交金额:暂无");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.itemView, bean, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGoodsOutList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_project_name)
        TextView tvProjectName;
        @BindView(R.id.tv_project_type)
        TextView tvProjectType;
        @BindView(R.id.tv_project_principal_user)
        TextView tvProjectPrincipalUser;
        @BindView(R.id.tv_project_create_user)
        TextView tvProjectCreateUser;
        @BindView(R.id.tv_project_summaries)
        TextView tvProjectSummaries;
        @BindView(R.id.tv_project_create_time)
        TextView tvProjectCreateTime;
        @BindView(R.id.tv_project_qualified_time)
        TextView tvProjectQualifiedTime;
        @BindView(R.id.tv_project_dealMoney)
        TextView tvProjectDealMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
