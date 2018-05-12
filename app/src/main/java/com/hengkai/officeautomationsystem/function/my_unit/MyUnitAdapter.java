package com.hengkai.officeautomationsystem.function.my_unit;

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
import com.hengkai.officeautomationsystem.function.unit_library.detail.UnitLibraryDetailActivity;
import com.hengkai.officeautomationsystem.network.entity.MyUnitEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/5/12.
 */
public class MyUnitAdapter extends RecyclerView.Adapter<MyUnitAdapter.ViewHolder> {

    private List<MyUnitEntity.DATABean> mList;
    private Context context;

    public MyUnitAdapter(List<MyUnitEntity.DATABean> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_unit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyUnitEntity.DATABean bean = mList.get(position);
        holder.tvUnitName.setText(bean.name);
        holder.tvUnitAddress.setText(bean.address);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UnitLibraryDetailActivity.class);
                intent.putExtra("ID", bean.id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_unit_name)
        TextView tvUnitName;
        @BindView(R.id.tv_unit_address)
        TextView tvUnitAddress;
        LinearLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            container = (LinearLayout) itemView;
        }
    }

}
