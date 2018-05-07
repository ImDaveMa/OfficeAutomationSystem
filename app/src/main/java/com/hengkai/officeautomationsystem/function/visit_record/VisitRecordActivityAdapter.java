package com.hengkai.officeautomationsystem.function.visit_record;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hengkai.officeautomationsystem.network.entity.VisitRecordEntity;

import java.util.List;

/**
 * Created by Harry on 2018/5/7.
 */
public class VisitRecordActivityAdapter extends RecyclerView.Adapter<VisitRecordActivityAdapter.ViewHolder> {

    private List<VisitRecordEntity.DATABean> mList;

    public VisitRecordActivityAdapter(List<VisitRecordEntity.DATABean> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
