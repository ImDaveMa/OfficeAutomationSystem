package com.hengkai.officeautomationsystem.utils.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hengkai.officeautomationsystem.base.model.MenuConfigModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/3.
 */

public class MenuDbHelper extends BaseDbHelper {

    private Context ctx;

    public MenuDbHelper(Context context) {
        super(context);
        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        super.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        super.onUpgrade(sqLiteDatabase, i, i1);
    }

    /**
     * 获取菜单列表
     * 按照count倒叙排序
     * @return
     */
    public List<MenuConfigModel> getMenus(){
        List<MenuConfigModel> menus = null;
        //创建数据库实例
        SQLiteDatabase db = new MenuDbHelper(ctx).getReadableDatabase();

        Cursor cursor = db.query(BaseDbHelper.TBL_MENUS, null, null, null, null, null, "count desc","0,8");
        if(cursor != null){
            menus = new ArrayList<MenuConfigModel>();
            while(cursor.moveToNext()){
                MenuConfigModel menu = new MenuConfigModel();
                int _id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String image = cursor.getString(cursor.getColumnIndex("image"));
                int count = cursor.getInt(cursor.getColumnIndex("count"));
                int last_time = cursor.getInt(cursor.getColumnIndex("last_time"));
                String target = cursor.getString(cursor.getColumnIndex("target"));

                menu.setId(_id);
                menu.setName(name);
                menu.setImage(image);
                menu.setCount(count);
                menu.setLast_time(last_time);
                menu.setTarget(target);

                menus.add(menu);
            }
        }
        return menus;
    }

    /**
     * 根据ID设置点击数量以及点击时间
     * @param id
     * @param last_time
     * @return
     */
    public boolean setCount(int id, long last_time){
        //创建数据库实例
        SQLiteDatabase db = new MenuDbHelper(ctx).getWritableDatabase();

        Cursor cursor = db.rawQuery("select count from " + TBL_MENUS + " where id=?", new String[]{ id+"" });
        if(cursor != null){
            if(cursor.moveToFirst()){
                int count = cursor.getInt(cursor.getColumnIndex("count"));
                count++;

                ContentValues valus = new ContentValues();
                valus.put("count",count);
                valus.put("last_time",last_time);
                return db.update(TBL_MENUS, valus,"id=?",new String[]{ id+"" }) > 0;
            }
        }
        return false;
    }

    /**
     * 通过图片名称获取
     * @param image
     * @return
     */
    public int getIDByImage(String image){
        //创建数据库实例
        SQLiteDatabase db = new MenuDbHelper(ctx).getWritableDatabase();

        Cursor cursor = db.rawQuery("select id from " + TBL_MENUS + " where image=?", new String[]{ image });
        if(cursor!=null){
            if(cursor.moveToFirst()){
                return cursor.getInt(cursor.getColumnIndex("id"));
            }
        }
        return 0;
    }
}
