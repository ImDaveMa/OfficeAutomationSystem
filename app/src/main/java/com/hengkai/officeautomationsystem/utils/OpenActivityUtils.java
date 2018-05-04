package com.hengkai.officeautomationsystem.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.function.contacts.ContactsActivity;
import com.hengkai.officeautomationsystem.function.go_out.GoOutActivity;
import com.hengkai.officeautomationsystem.function.management_of_goods.ManagementOfGoodsActivity;
import com.hengkai.officeautomationsystem.function.schedule.ScheduleActivity;
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
                String tag = holder.item.getTag()+"";
                int rId = ResourcesUtils.getResource(activity,tag);
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
                    default:
                        break;
                }
            }
        });
    }
}
