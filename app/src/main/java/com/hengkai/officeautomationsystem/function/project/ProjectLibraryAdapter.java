package com.hengkai.officeautomationsystem.function.project;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.listener.OnItemClickListener;
import com.hengkai.officeautomationsystem.network.entity.ProjectEntity;
import com.hengkai.officeautomationsystem.network.entity.UseGoodsEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;

import java.text.DecimalFormat;
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
        DecimalFormat df = new DecimalFormat("#.00");
        String total = df.format(bean.getDealMoney());
        holder.tvPrice.setText(String.format("总价值：%s元", total));
        holder.tvTime.setText(bean.getCreateTime());
        holder.tvType.setText(bean.getProjectType());
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
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_type)
        TextView tvType;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
