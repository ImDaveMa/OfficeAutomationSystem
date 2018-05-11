package com.hengkai.officeautomationsystem.utils;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.function.contacts.ContactsActivity;
import com.hengkai.officeautomationsystem.function.contacts_library.ContactsLibraryActivity;
import com.hengkai.officeautomationsystem.function.go_out.GoOutActivity;
import com.hengkai.officeautomationsystem.function.management_of_goods.GoodsSupplierActivity;
import com.hengkai.officeautomationsystem.function.management_of_goods.GoodsUnitActivity;
import com.hengkai.officeautomationsystem.function.management_of_goods.ManagementGoodsInActivity;
import com.hengkai.officeautomationsystem.function.management_of_goods.ManagementOfGoodsActivity;
import com.hengkai.officeautomationsystem.function.management_of_goods.UseGoodsActivity;
import com.hengkai.officeautomationsystem.function.schedule.ScheduleActivity;
import com.hengkai.officeautomationsystem.function.unit_library.UnitLibraryActivity;
import com.hengkai.officeautomationsystem.function.visit_record.VisitRecordActivity;
import com.hengkai.officeautomationsystem.holder.MenuViewHolder;
import com.hengkai.officeautomationsystem.utils.dbhelper.MenuDbHelper;

public class OpenActivityUtils {

    public static final int REQUEST_CODE = 100;

    private static void onClickMethod(Activity activity, Class aClass, int id) {
        Intent intent = new Intent(activity, aClass);
        intent.putExtra(CommonFinal.MENU_ID, id);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * 通用的点击事件
     *
     * @param holder ViewHolder
     */
    public static void setOnClickMethodToCommon(final Activity activity, final MenuViewHolder holder) {
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = holder.item.getTag() + "";
                int rId = ResourcesUtils.getResource(activity, tag);
                int dbId = new MenuDbHelper(activity).getIDByImage(tag);
                switch (rId) {
                    case R.drawable.ic_schedule://日程
                        onClickMethod(activity, ScheduleActivity.class, dbId);
                        break;
                    case R.drawable.ic_contacts: //通讯录
                        onClickMethod(activity, ContactsActivity.class, dbId);
                        break;
                    case R.drawable.ic_management_of_goods: //物品管理
                        onClickMethod(activity, ManagementOfGoodsActivity.class, dbId);
                        break;
                    case R.drawable.ic_go_out: //外出
                        onClickMethod(activity, GoOutActivity.class, dbId);
                        break;
                    case R.drawable.ic_unit_library: //单位库
                        onClickMethod(activity, UnitLibraryActivity.class, dbId);
                        break;
                    case R.drawable.ic_visit_record: //拜访跟进
                        onClickMethod(activity, VisitRecordActivity.class, dbId);
                        break;
                    case R.drawable.ic_application_for_use: //物品领用
                        onClickMethod(activity, UseGoodsActivity.class, dbId);
                        break;
                    case R.drawable.ic_item_management_of_goods: //物品单位列表
                        onClickMethod(activity, GoodsUnitActivity.class, dbId);
                        break;
                    case R.drawable.ic_contacts_library: //项目 - 联系人库
                        onClickMethod(activity, ContactsLibraryActivity.class, dbId);
                        break;
                    case R.drawable.ic_supplier_management: //物品单位列表
                        onClickMethod(activity, GoodsSupplierActivity.class, dbId);
                        break;
                    case R.drawable.ic_application_for_use_list: //领用列表
                        break;
                    case R.drawable.ic_application_for_warehousing_list: // 入库列表
                        onClickMethod(activity, ManagementGoodsInActivity.class, dbId);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
