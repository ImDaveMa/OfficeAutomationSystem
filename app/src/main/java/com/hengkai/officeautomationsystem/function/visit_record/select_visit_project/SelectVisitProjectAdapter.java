package com.hengkai.officeautomationsystem.function.visit_record.select_visit_project;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailGetVisitUnitEntity;

import java.util.List;

/**
 * Created by Harry on 2018/6/16.
 */
public class SelectVisitProjectAdapter extends RecyclerView.Adapter<SelectVisitProjectAdapter.ViewHolder> {

    private List<VisitRecordDetailGetVisitUnitEntity.DATABean> mList;

    public SelectVisitProjectAdapter(List<VisitRecordDetailGetVisitUnitEntity.DATABean> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visit_record_detail_bottom_dialog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final VisitRecordDetailGetVisitUnitEntity.DATABean bean = mList.get(position);
        holder.tvName.setText(bean.name);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(bean.id, bean.name);
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
        FrameLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            item = (FrameLayout) itemView;
        }
    }

    public interface OnItemClickListener {
        void onClick(int ID, String name);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

}
