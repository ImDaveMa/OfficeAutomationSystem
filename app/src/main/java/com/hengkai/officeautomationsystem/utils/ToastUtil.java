package com.hengkai.officeautomationsystem.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.hengkai.officeautomationsystem.application.OfficeAutomationSystemApplication;

/**
 * Created by Harry on 2017/7/10.
 */

public class ToastUtil {
    private static Toast mToast;

    public static void showToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(OfficeAutomationSystemApplication.getAppContext(), text, Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        View view = mToast.getView();
        mToast.show();
    }

    /**
     * 在子线程中toast内容
     * @param context
     * @param text
     */
    public static void showToast(Context context, final String text) {

        if (context instanceof Activity){
            Activity activity = (Activity) context;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast(text);
                }
            });
        }else{
            showToast(text);
        }

    }
}
