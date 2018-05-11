package com.hengkai.officeautomationsystem.function.contacts_library;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.network.entity.ContactsLibraryEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Harry on 2018/5/11.
 */
public class ContactsLibraryAdapter extends RecyclerView.Adapter<ContactsLibraryAdapter.ViewHolder> {

    private List<ContactsLibraryEntity.DATABean> mList;

    public ContactsLibraryAdapter(List<ContactsLibraryEntity.DATABean> list) {
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacts_library, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ContactsLibraryEntity.DATABean bean = mList.get(position);
        holder.tvName.setText(bean.name);
        holder.tvUnit.setText(bean.companyName);
        holder.tvDepartment.setText(bean.department);
        holder.tvPhone.setText(bean.phone);

        holder.ivCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClick(bean.phone);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_unit)
        TextView tvUnit;
        @BindView(R.id.tv_department)
        TextView tvDepartment;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.iv_call_phone)
        ImageView ivCallPhone;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface OnImageViewClickListener {
        void onClick(String phone);
    }

    private OnImageViewClickListener mListener;

    public void setOnImageViewClickListener(OnImageViewClickListener listener) {
        mListener = listener;
    }

}
