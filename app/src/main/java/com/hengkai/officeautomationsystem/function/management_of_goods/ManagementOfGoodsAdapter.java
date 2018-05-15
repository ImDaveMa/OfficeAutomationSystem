package com.hengkai.officeautomationsystem.function.management_of_goods;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.listener.OnItemClickListener;
import com.hengkai.officeautomationsystem.network.entity.GoodsEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/4/28.
 */
public class ManagementOfGoodsAdapter extends RecyclerView.Adapter<ManagementOfGoodsAdapter.ViewHolder> {

    private List<GoodsEntity.GoodsBean> goodsList;
    private OnItemClickListener mOnItemClickListener;

    public ManagementOfGoodsAdapter(OnItemClickListener onItemClickListener, List<GoodsEntity.GoodsBean> goodsList) {
        super();
        this.mOnItemClickListener = onItemClickListener;
        this.goodsList = goodsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_management_of_goods_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final GoodsEntity.GoodsBean bean = goodsList.get(position);
        holder.tvName.setText(bean.getName());
        holder.tvCount.setText(Html.fromHtml(String.format("库存数量：<font color='red'>%d</font>%s", bean.getNum(), bean.getUnit())));
        holder.tvType.setText(String.format("类别：%s", bean.getGoodsTypeName()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, bean, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.tv_type)
        TextView tvType;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
