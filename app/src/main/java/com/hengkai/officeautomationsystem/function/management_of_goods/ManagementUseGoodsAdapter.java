package com.hengkai.officeautomationsystem.function.management_of_goods;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.listener.OnItemClickListener;
import com.hengkai.officeautomationsystem.network.entity.GoodsInEntity;
import com.hengkai.officeautomationsystem.network.entity.UseGoodsEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/4/28.
 */
public class ManagementUseGoodsAdapter extends RecyclerView.Adapter<ManagementUseGoodsAdapter.ViewHolder> {

    private List<UseGoodsEntity.OutStorageBean> mGoodsOutList;
    private OnItemClickListener mOnItemClickListener;

    public ManagementUseGoodsAdapter(OnItemClickListener onItemClickListener, List<UseGoodsEntity.OutStorageBean> goodsOutList) {
        super();
        this.mGoodsOutList = goodsOutList;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_in, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final UseGoodsEntity.OutStorageBean bean = mGoodsOutList.get(position);
        holder.tvPurpose.setText(bean.getPurpose());
        DecimalFormat df = new DecimalFormat("#.00");
        String total = df.format(bean.getTotal());
        holder.tvPrice.setText(String.format("总价值：%s元", total));
        holder.tvTime.setText(String.format("入库时间：%s", DateFormatUtils.getFormatedNewsTime(bean.getCreateTime())));
        String state = "";
        switch (bean.getState()){
            case 0:
                state = "待审核";
                break;
            case 1:
                state = "已通过";
                break;
            case 2:
                state = "已拒绝";
                break;
            case 3:
                state = "已撤销";
                break;
        }
        holder.tvType.setText(state);
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

        @BindView(R.id.tv_purpose)
        TextView tvPurpose;
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
