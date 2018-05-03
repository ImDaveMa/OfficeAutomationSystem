package com.hengkai.officeautomationsystem.function.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.model.MenuConfigModel;
import com.hengkai.officeautomationsystem.holder.MenuViewHolder;
import com.hengkai.officeautomationsystem.utils.OpenActivityUtils;
import com.hengkai.officeautomationsystem.utils.ResourcesUtils;
import com.hengkai.officeautomationsystem.utils.dbhelper.MenuDbHelper;

import java.util.List;

/**
 * Created by Harry on 2018/4/26.
 */
public class HomeFragmentGridLayoutAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    //存储菜单信息
    private List<MenuConfigModel> menus;
    private Context context;

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_fragment_grid_layout, parent, false);

        MenuDbHelper helper = new MenuDbHelper(context);
        menus = helper.getMenus();
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuViewHolder holder, int position) {
        if (menus != null && menus.size() > 0) {
            MenuConfigModel menu = menus.get(position);
            holder.tvContent.setText(menu.getName());
            holder.ivContent.setImageResource(ResourcesUtils.getResource(context, menu.getImage()));
            holder.item.setTag(menu.getImage());
            OpenActivityUtils.setOnClickMethodToCommon(context, holder);//添加点击事件
        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }
}
