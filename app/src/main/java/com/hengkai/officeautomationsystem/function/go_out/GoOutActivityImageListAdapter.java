package com.hengkai.officeautomationsystem.function.go_out;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.function.login.LoginActivity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.luck.picture.lib.entity.LocalMedia;
import com.unistrong.yang.zb_permission.ZbPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harry on 2018/5/3.
 */
public class GoOutActivityImageListAdapter extends RecyclerView.Adapter<GoOutActivityImageListAdapter.ViewHolder> {

    private List<LocalMedia> list;
    private Activity mActivity;

    public GoOutActivityImageListAdapter(Activity activity) {
        list = new ArrayList<>();
        mActivity = activity;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_add_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.ivCancel.setImageResource(R.drawable.ic_cancel32);
        if (position == list.size()) {
            holder.ivAddImage.setImageResource(R.drawable.ic_add128);
            holder.ivCancel.setVisibility(View.GONE);
            holder.ivAddImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.click();
                    }
                }
            });
        } else {
            holder.ivCancel.setVisibility(View.VISIBLE);
            String path = list.get(position).getCompressPath();
            holder.ivAddImage.setImageBitmap(BitmapFactory.decodeFile(path));
            holder.ivAddImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, GoOutPreviewActivity.class);
                    intent.putExtra("previewImage", list.get(holder.getAdapterPosition()).getPath());
                    mActivity.startActivity(intent);
                }
            });
            holder.ivCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeData(holder.getAdapterPosition());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAddImage;
        ImageView ivCancel;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAddImage = itemView.findViewById(R.id.iv_add_image);
            ivCancel = itemView.findViewById(R.id.iv_cancel);
        }
    }

    /**
     * 删除指定的Item
     */
    public void removeData(int position) {
        list.remove(position);
        //  通知RecyclerView控件某个Item已经被删除
        notifyItemRemoved(position);
    }

    /**
     * 在指定位置添加一个新的Item
     */
    public void addItem(LocalMedia media, int positionToAdd) {
        list.add(media);
        //  通知RecyclerView控件插入了某个Item
        notifyItemInserted(positionToAdd);
    }

    public void addItems(List<LocalMedia> medias) {
        list.addAll(0, medias);
        notifyDataSetChanged();
    }

    public interface OnImageViewClickListener {
        void click();
    }

    private OnImageViewClickListener mListener;

    public void setOnImageViewClickListener(OnImageViewClickListener listener) {
        mListener = listener;
    }

}
