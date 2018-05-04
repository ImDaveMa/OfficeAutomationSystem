package com.hengkai.officeautomationsystem.utils;

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

/**
 * 有页面的通用点击事件
 */
public class OpenActivityUtils {

    private static void onClickMethod(Context context, Class aClass, int id) {
        Intent intent = new Intent(context, aClass);
        intent.putExtra(CommonFinal.MENU_ID, id);
        context.startActivity(intent);
    }

    /**
     * 通用的点击事件
     *
     * @param holder ViewHolder
     */
    public static void setOnClickMethodToCommon(final Context context, final MenuViewHolder holder) {
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = holder.item.getTag()+"";
                int rId = ResourcesUtils.getResource(context,tag);
                int dbId = new MenuDbHelper(context).getIDByImage(tag);
                switch (rId) {
                    case R.drawable.ic_schedule://日程
                        onClickMethod(context, ScheduleActivity.class, dbId);
                        break;
                    case R.drawable.ic_contacts: //通讯录
                        onClickMethod(context, ContactsActivity.class, dbId);
                        break;
                    case R.drawable.ic_management_of_goods:
                        onClickMethod(context, ManagementOfGoodsActivity.class, dbId);
                        break;
                    case R.drawable.ic_go_out:
                        onClickMethod(context, GoOutActivity.class, dbId);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
