package com.hengkai.officeautomationsystem.function.management_of_goods;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
public class SelectGoodsAdapter extends RecyclerView.Adapter<SelectGoodsAdapter.ViewHolder> {

    private List<GoodsEntity.GoodsBean> goodsList;
    private OnItemClickListener mOnItemClickListener;

    public SelectGoodsAdapter(OnItemClickListener onItemClickListener, List<GoodsEntity.GoodsBean> goodsList) {
        super();
        this.goodsList = goodsList;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_goods, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final GoodsEntity.GoodsBean bean = goodsList.get(position);
        holder.tvName.setText(bean.getName());
        holder.tvPrice.setText(bean.getCost() + "元");
        holder.tvNum.setText(String.format("(库存%d%s)", bean.getNum(),bean.getUnit()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.itemView, bean, position);
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
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_num)
        TextView tvNum;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
