package com.hengkai.officeautomationsystem.base;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.application.OfficeAutomationSystemApplication;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.base.view.BaseActivityImpl;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.utils.LoadingDialogType;
import com.hengkai.officeautomationsystem.utils.WindowUtil;
import com.hengkai.officeautomationsystem.utils.dbhelper.MenuDbHelper;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by Harry on 2018/4/17.
 */
public abstract class BaseActivity<P extends BasePresenter> extends BaseActivityImpl<P> {

    private OfficeAutomationSystemApplication application;
    private Dialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //防止软键盘把布局顶起
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(setupView());

        application = (OfficeAutomationSystemApplication) getApplication();
        application.addActivity(this);
        //只是手机竖屏显示
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initView();

        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
//        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);

        // 增加点击次数
        if(getIntent().hasExtra(CommonFinal.MENU_ID)) {
            int dbId = getIntent().getIntExtra(CommonFinal.MENU_ID, 0);
            if (dbId > 0) {
                new MenuDbHelper(this).setCount(dbId, System.currentTimeMillis());
            }
        }
     }

     /**
     * @return 布局文件的ID
     */
    protected abstract int setupView();

    /**
     * 初始化布局(例如findViewById)
     */
    protected abstract void initView();

    /**
     * 初始化Dialog
     */
    private void initDialog() {
        dialog = new Dialog(this, R.style.LoadingDialog);
        final AVLoadingIndicatorView indicatorView = new AVLoadingIndicatorView(this);
        indicatorView.setIndicator(LoadingDialogType.BallScaleMultipleIndicator.name());    //设置dialog的样式
        indicatorView.setIndicatorColor(getResources().getColor(R.color.teal));

        dialog.setContentView(indicatorView);

        int deviceWidth = WindowUtil.getScreenWidth();
        int deviceHeight = WindowUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams params = dialogWindow.getAttributes();
            params.width = deviceWidth / 8;
            params.height = deviceHeight / 8;
            params.gravity = Gravity.CENTER;
            dialogWindow.setAttributes(params);
        }

        dialog.setCancelable(true);
    }

    /**
     * 显示LoadingDialog
     */
    public void showDialog() {
        initDialog();
        dialog.show();
    }

    /**
     * 关闭LoadingDialog
     */
    public void dismissDialog() {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ArrayList<String> tags = cancelNetWork();
        if (tags != null && tags.size() != 0) {
            for (String tag : tags) {
                RxApiManager.get().cancel(tag);
            }
        }

        application.finishActivity(this);   //清除栈中的Activity
    }

    protected abstract ArrayList<String> cancelNetWork();

}
