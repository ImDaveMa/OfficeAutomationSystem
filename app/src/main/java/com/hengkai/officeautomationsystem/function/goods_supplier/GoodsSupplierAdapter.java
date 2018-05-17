package com.hengkai.officeautomationsystem.function.goods_supplier;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.listener.OnItemClickListener;
import com.hengkai.officeautomationsystem.network.entity.GoodsSupplierEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/4/28.
 */
public class GoodsSupplierAdapter extends RecyclerView.Adapter<GoodsSupplierAdapter.ViewHolder> {

    private List<GoodsSupplierEntity.SupplierBean> mUnitList;
    private OnItemClickListener mOnItemClickListener;

    public GoodsSupplierAdapter(OnItemClickListener onItemClickListener, List<GoodsSupplierEntity.SupplierBean> unitList) {
        mUnitList = unitList;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_supplier_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final GoodsSupplierEntity.SupplierBean bean = mUnitList.get(position);
        holder.tvName.setText(bean.getName());
        holder.tvType.setText(bean.getSupplierTypeName());
        holder.tvAddress.setText(String.format("供应商地址：%s", bean.getCity()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, bean, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUnitList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_address)
        TextView tvAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
