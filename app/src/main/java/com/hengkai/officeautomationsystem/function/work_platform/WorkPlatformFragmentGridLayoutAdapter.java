package com.hengkai.officeautomationsystem.function.work_platform;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.holder.MenuViewHolder;
import com.hengkai.officeautomationsystem.utils.OpenActivityUtils;
import com.hengkai.officeautomationsystem.utils.ResourcesUtils;

/**
 * Created by Harry on 2018/4/26.
 * 列表中GridLayout的适配器
 */
public class WorkPlatformFragmentGridLayoutAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    private int itemPosition;
    private Context context;
    private Activity activity;
    private String[] commonNames = {"日程", "日程提醒", "通讯录", "完善个人信息", "找回密码", "问题反馈", "修改密码", "更多"};
    private String[] employeeNames = {"日报", "周报", "员工档案库", "请假申请单", "补卡", "外出", "报销", "分配工作", "我的工作", "分配开发", "我的开发", "更多"};
    private String[] projectNames = {"单位库", "新增单位", "单位列表", "联系人库", "项目库", "新增项目", "拜访跟进记录", "方案需求", "新增方案", "合同预览", "售后续费", "更多"};
    private String[] resourceNames = {"物品管理", "物品单位管理", "供应商管理", "采购合同管理", "合作合同管理", "入库申请", "领用申请", "统计分析"};

    private int[] commonImageResources = {R.drawable.ic_schedule, R.drawable.ic_schedule_reminding,
            R.drawable.ic_contacts, R.drawable.ic_improve_personal_information,
            R.drawable.ic_forget_password, R.drawable.ic_problem_feedback,
            R.drawable.ic_modify_password, R.drawable.ic_more_common};
    private int[] employeeImageResources = {R.drawable.ic_daily_report, R.drawable.ic_weekly_report,
            R.drawable.ic_emplyee_archives, R.drawable.ic_ask_for_leave,
            R.drawable.ic_work_attendance_card, R.drawable.ic_go_out,
            R.drawable.ic_reimbursement, R.drawable.ic_assign_jobs,
            R.drawable.ic_my_work, R.drawable.ic_development,
            R.drawable.ic_my_development, R.drawable.ic_more_employee};
    private int[] projectImageResources = {R.drawable.ic_unit_library, R.drawable.ic_new_unit,
            R.drawable.ic_unit_list, R.drawable.ic_contacts_library,
            R.drawable.ic_project_library, R.drawable.ic_new_project,
            R.drawable.ic_visit_record, R.drawable.ic_programme_requirements,
            R.drawable.ic_new_programme, R.drawable.ic_preview_contract,
            R.drawable.ic_aftermarket_renewal, R.drawable.ic_more_project};
    private int[] resourceImageResources = {R.drawable.ic_management_of_goods, R.drawable.ic_item_management_of_goods,
            R.drawable.ic_supplier_management, R.drawable.ic_procurement_contract_management,
            R.drawable.ic_cooperation_contract_management, R.drawable.ic_application_for_warehousing,
            R.drawable.ic_application_for_use, R.drawable.ic_statistical_analysis};

    public WorkPlatformFragmentGridLayoutAdapter(Activity activity, int itemPosition) {
        this.activity = activity;
        this.itemPosition = itemPosition;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_work_platform_fragment_content, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        switch (itemPosition) {
            case 0://通用
                holder.tvContent.setText(commonNames[position]);
                holder.ivContent.setImageResource(commonImageResources[position]);
                //保存图片名称
                holder.item.setTag(ResourcesUtils.getResourceName(context,commonImageResources[position]));
                OpenActivityUtils.setOnClickMethodToCommon(activity, holder);//添加点击事件
                break;
            case 1://员工
                holder.tvContent.setText(employeeNames[position]);
                holder.ivContent.setImageResource(employeeImageResources[position]);
                //保存图片名称
                holder.item.setTag(ResourcesUtils.getResourceName(context,employeeImageResources[position]));
                OpenActivityUtils.setOnClickMethodToCommon(activity, holder);//添加点击事件
                break;
            case 2://项目
                holder.tvContent.setText(projectNames[position]);
                holder.ivContent.setImageResource(projectImageResources[position]);
                //保存图片名称
                holder.item.setTag(ResourcesUtils.getResourceName(context,projectImageResources[position]));
                OpenActivityUtils.setOnClickMethodToCommon(activity, holder);//添加点击事件
                break;
            case 3://物品
                holder.tvContent.setText(resourceNames[position]);
                holder.ivContent.setImageResource(resourceImageResources[position]);
                //保存图片名称
                holder.item.setTag(ResourcesUtils.getResourceName(context,resourceImageResources[position]));
                OpenActivityUtils.setOnClickMethodToCommon(activity, holder);//添加点击事件
                break;

            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        switch (itemPosition) {
            case 0://通用
                return 8;
            case 1://员工
                return 12;
            case 2://项目
                return 12;
            case 3://物品
                return 8;

            default:
                return 0;
        }
    }

}
