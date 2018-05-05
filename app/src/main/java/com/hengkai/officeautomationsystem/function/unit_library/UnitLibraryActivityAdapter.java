package com.hengkai.officeautomationsystem.function.unit_library;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.network.entity.UnitLibraryEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/5/5.
 */
public class UnitLibraryActivityAdapter extends RecyclerView.Adapter<UnitLibraryActivityAdapter.ViewHolder> {

    private List<UnitLibraryEntity.DATABean> mList;

    public UnitLibraryActivityAdapter(List<UnitLibraryEntity.DATABean> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_unit_library, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UnitLibraryEntity.DATABean bean = mList.get(position);
        holder.tvUnitName.setText(bean.name);
        holder.tvUnitAddress.setText(bean.address);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_unit_name)
        TextView tvUnitName;
        @BindView(R.id.tv_unit_address)
        TextView tvUnitAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
