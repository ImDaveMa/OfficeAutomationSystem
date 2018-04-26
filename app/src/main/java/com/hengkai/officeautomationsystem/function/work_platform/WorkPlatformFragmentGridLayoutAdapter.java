package com.hengkai.officeautomationsystem.function.work_platform;

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
import com.hengkai.officeautomationsystem.function.ask_for_leave.AskForLeaveActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harry on 2018/4/26.
 * 列表中GridLayout的适配器
 */
public class WorkPlatformFragmentGridLayoutAdapter extends RecyclerView.Adapter<WorkPlatformFragmentGridLayoutAdapter.ViewHolder> {

    private int itemPosition;
    private List<String> mList;
    private Context context;

    public WorkPlatformFragmentGridLayoutAdapter(int itemPosition) {
        this.itemPosition = itemPosition;
        initData();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_work_platform_fragment_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (itemPosition) {
            case 0://通用
                if (position == 15) {
                    holder.tvContent.setText("更多");
                } else {
                    holder.tvContent.setText(mList.get(position));
                }
                break;
            case 1://员工
                if (position == 15) {
                    holder.tvContent.setText("更多");
                } else {
                    holder.tvContent.setText(mList.get(position));
                }
                setOnClickMethodToEmployee(holder);//添加点击事件
                break;
            case 2://项目
                if (position == 15) {
                    holder.tvContent.setText("更多");
                } else {
                    holder.tvContent.setText(mList.get(position));
                }
                break;
            case 3://物品
                if (position == 15) {
                    holder.tvContent.setText("更多");
                } else {
                    holder.tvContent.setText(mList.get(position));
                }
                break;

            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 16;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvContent;
        ImageView ivContent;
        LinearLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
            ivContent = itemView.findViewById(R.id.iv_content);
            item = (LinearLayout) itemView;
        }
    }

    /**
     * 初始化列表的数据源
     */
    private void initData() {
        mList = new ArrayList<>();
        mList.add("请假");
        mList.add("考勤记录");
        mList.add("外出打卡");
        mList.add("离职");

        mList.add("请假1");
        mList.add("考勤记录1");
        mList.add("外出打卡1");
        mList.add("离职1");

        mList.add("请假2");
        mList.add("考勤记录2");
        mList.add("外出打卡2");
        mList.add("离职2");

        mList.add("请假3");
        mList.add("考勤记录3");
        mList.add("外出打卡3");
    }

    private void onClickMethod(Class aClass) {
        Intent intent = new Intent(context, aClass);
        context.startActivity(intent);
    }

    /**
     * 员工的点击事件
     * @param holder ViewHolder
     */
    private void setOnClickMethodToEmployee(final ViewHolder holder) {
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.getAdapterPosition()) {
                    case 0:
                        onClickMethod(AskForLeaveActivity.class); //跳转至请假页面
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

                        break;

                    default:
                        break;
                }
            }
        });
    }
}
