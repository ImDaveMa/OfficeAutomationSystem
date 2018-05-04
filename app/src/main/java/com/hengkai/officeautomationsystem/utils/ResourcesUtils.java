package com.hengkai.officeautomationsystem.utils;

import android.content.Context;


/**
 * Created by Administrator on 2018/5/3.
 */

public class ResourcesUtils {

    /**
     * 根据资源名称获取资源ID
     * @param context
     * @param imageName
     * @return
     */
    public static int  getMipmapResource(Context context, String imageName){
        int resId = context.getResources().getIdentifier(imageName, "mipmap", context.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }

    /**
     * 根据资源名称获取资源ID
     * @param context
     * @param imageName
     * @return
     */
    public static int  getResource(Context context, String imageName){
        int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }

    /**
     * 获取资源名称（不带前缀）
     * @param context
     * @param rid
     * @return
     */
    public static String getResourceName(Context context, int rid){
        String longName = context.getResources().getResourceName(rid);
        int index = longName.indexOf('/') + 1;
        return longName.substring(index);
    }
}
