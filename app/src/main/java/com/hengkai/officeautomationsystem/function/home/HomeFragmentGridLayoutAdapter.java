package com.hengkai.officeautomationsystem.function.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.function.contacts.ContactsActivity;

/**
 * Created by Harry on 2018/4/26.
 */
public class HomeFragmentGridLayoutAdapter extends RecyclerView.Adapter<HomeFragmentGridLayoutAdapter.ViewHolder> {

    private String[] names = {"我的任务", "通知", "审批", "我的申请", "我的审批", "通知", "审批", "通讯录"};
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_fragment_grid_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.tvItem.setText(names[position]);
//        holder.tvItem.setCompoundDrawables(null, topDrawable, null, null);
        holder.tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.getAdapterPosition()) {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:
                        onClickMethod(ContactsActivity.class);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);
        }
    }

    private void onClickMethod(Class aClass) {
        Intent intent = new Intent(context, aClass);
        context.startActivity(intent);
    }
}
