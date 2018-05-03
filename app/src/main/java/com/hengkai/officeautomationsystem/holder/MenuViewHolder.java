package com.hengkai.officeautomationsystem.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;

public class MenuViewHolder extends RecyclerView.ViewHolder {

    public TextView tvContent;
    public ImageView ivContent;
    public LinearLayout item;

    public MenuViewHolder(View itemView) {
        super(itemView);
        tvContent = itemView.findViewById(R.id.tv_content);
        ivContent = itemView.findViewById(R.id.iv_content);
        item = (LinearLayout) itemView;
    }
}
