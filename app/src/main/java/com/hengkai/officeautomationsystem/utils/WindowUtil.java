package com.hengkai.officeautomationsystem.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.hengkai.officeautomationsystem.application.OfficeAutomationSystemApplication;

/**
 * Created by Harry on 2017/7/13.
 * 设置手机窗体相关的属性的工具类
 */

public class WindowUtil {

    /**
     * 设置添加屏幕的背景透明度
     * @param alpha 透明度 数值为0.0 - 1.0
     * @param activity 当前Activity的对象
     */
    public static void backgroundAlpht(float alpha, Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = alpha;
        activity.getWindow().setAttributes(params);
    }

    /**
     * dp转换成px
     * @param dp
     * @return
     */
    public static int dp2px(float dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * px转换成dp
     * @param px
     * @return
     */
    public static int px2dp(float px, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    /**
     * convert px to its equivalent sp
     *
     * 将px转换为sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * convert sp to its equivalent px
     *
     * 将sp转换为px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * @return 屏幕的宽度
     */
    public static int getScreenWidth() {
        final Resources resources = OfficeAutomationSystemApplication.getAppContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * @return 屏幕的高度
     */
    public static int getScreenHeight() {
        final Resources resources = OfficeAutomationSystemApplication.getAppContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

}
