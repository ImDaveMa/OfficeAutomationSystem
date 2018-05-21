package com.hengkai.officeautomationsystem.function.goods_supplier;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.network.entity.AddGoodsEntity;
import com.hengkai.officeautomationsystem.network.entity.AddGoodsSupplierEntity;

import java.util.List;

/**
 * Created by Harry on 2018/5/8.
 * 底部弹窗的适配器
 */
public class AddGoodsSupplierTypeBottomDialogAdapter extends RecyclerView.Adapter<AddGoodsSupplierTypeBottomDialogAdapter.ViewHolder> {

    private List<AddGoodsSupplierEntity.ParamBean> mList;

    public AddGoodsSupplierTypeBottomDialogAdapter(List<AddGoodsSupplierEntity.ParamBean> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visit_record_detail_bottom_dialog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AddGoodsSupplierEntity.ParamBean bean = mList.get(position);
        holder.tvName.setText(bean.name);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        FrameLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            container = (FrameLayout) itemView;
        }
    }

    public interface OnItemClickListener {
        void onClick(AddGoodsSupplierEntity.ParamBean bean);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}
