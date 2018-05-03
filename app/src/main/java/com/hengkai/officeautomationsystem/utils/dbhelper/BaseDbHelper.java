package com.hengkai.officeautomationsystem.utils.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2018/5/3.
 */

public class BaseDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLiteOpenHelper";

    public static final int VERSION = 1;

    public static final String DB_NAME = "main.db";
    public static final String TBL_MENUS = "tbl_menus";

    public BaseDbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建菜单表语句
        String sql = "create table " + TBL_MENUS + "(" +
                "id integer primary key autoincrement," +
                "name varchar(50)," +
                "image varchar(200)," +
                "count integer," +
                "last_time integer," +
                "target varchar(100))";
        //输出创建数据库的日志信息
        Log.i(TAG, "Create Database------------->");
        Log.i(TAG, "Create table tbl_menus------------->");
        //execSQL函数用于执行SQL语句
        sqLiteDatabase.execSQL(sql);

        // 默认数据处理
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('日程','ic_schedule',0,0,'schedule');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('日程提醒','ic_schedule_reminding',0,0,'schedule_reminding');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('通讯录','ic_contacts',1000000,0,'contacts');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('完善个人信息','ic_improve_personal_information',0,0,'improve_personal_information');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('找回密码','ic_forget_password',0,0,'forget_password');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('问题反馈','ic_problem_feedback',0,0,'problem_feedback');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('修改密码','ic_modify_password',0,0,'modify_password');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('日报','ic_daily_report',0,0,'daily_report');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('周报','ic_weekly_report',0,0,'weekly_report');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('员工档案库','ic_emplyee_archives',0,0,'emplyee_archives');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('请假申请单','ic_ask_for_leave',0,0,'ask_for_leave');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('补卡','ic_work_attendance_card',0,0,'work_attendance_card');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('外出','ic_go_out',0,0,'go_out');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('报销','ic_reimbursement',0,0,'reimbursement');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('分配工作','ic_assign_jobs',0,0,'assign_jobs');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('我的工作','ic_my_work',0,0,'my_work');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('分配开发','ic_development',0,0,'development');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('我的开发','ic_my_development',0,0,'my_development');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('单位库','ic_unit_library',0,0,'unit_library');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('新增单位','ic_new_unit',0,0,'new_unit');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('单位列表','ic_unit_list',0,0,'unit_list');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('联系人库','ic_contacts_library',0,0,'contacts_library');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('项目库','ic_project_library',0,0,'project_library');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('新增项目','ic_new_project',0,0,'new_project');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('拜访跟进记录','ic_visit_record',0,0,'visit_record');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('方案需求','ic_programme_requirements',0,0,'programme_requirements');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('新增方案','ic_new_programme',0,0,'new_programme');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('合同预览','ic_preview_contract',0,0,'preview_contract');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('售后续费','ic_aftermarket_renewal',0,0,'aftermarket_renewal');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('物品管理','ic_management_of_goods',0,0,'management_of_goods');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('物品单位管理','ic_item_management_of_goods',0,0,'item_management_of_goods');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('供应商管理','ic_supplier_management',0,0,'supplier_management');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('采购合同管理','ic_procurement_contract_management',0,0,'procurement_contract_management');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('合作合同管理','ic_cooperation_contract_management',0,0,'cooperation_contract_management');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('入库申请','ic_application_for_warehousing',0,0,'application_for_warehousing');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('领用申请','ic_application_for_use',0,0,'application_for_use');";
        sqLiteDatabase.execSQL(sql);
        sql = "insert into tbl_menus(name, image, count, last_time, target) values('统计分析','ic_statistical_analysis',0,0,'statistical_analysis');";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //输出更新数据库的日志信息
        Log.i(TAG, "Update Database------------->");
    }
}
