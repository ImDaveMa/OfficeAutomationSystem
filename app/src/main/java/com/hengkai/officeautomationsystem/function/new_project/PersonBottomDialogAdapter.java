package com.hengkai.officeautomationsystem.function.new_project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.network.entity.NewProjectGetPersonEntity;
import com.hengkai.officeautomationsystem.network.entity.NewProjectGetTypeEntity;

import java.util.List;

/**
 * Created by Harry on 2018/5/8.
 * 底部弹窗的适配器
 */
public class PersonBottomDialogAdapter extends RecyclerView.Adapter<PersonBottomDialogAdapter.ViewHolder> {

    private List<NewProjectGetPersonEntity.DATEBean> mList;
    private String name;
    private Context context;

    public PersonBottomDialogAdapter(List<NewProjectGetPersonEntity.DATEBean> list, String name) {
        mList = list;
        if (!TextUtils.isEmpty(name)) {
            this.name = name;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_new_project_bottom_dialog, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NewProjectGetPersonEntity.DATEBean bean = mList.get(position);
        holder.tvName.setTextColor(context.getResources().getColor(R.color.black1));
        holder.tvName.setText(bean.userName);
        holder.tvDepartment.setText(bean.dName);
        if (bean.userName.equals(name)) {
            holder.tvName.setTextColor(context.getResources().getColor(R.color.blue2));
        }
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
        TextView tvDepartment;
        LinearLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDepartment = itemView.findViewById(R.id.tv_department);
            container = (LinearLayout) itemView;
        }
    }

    public interface OnItemClickListener {
        void onClick(NewProjectGetPersonEntity.DATEBean bean);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

}
