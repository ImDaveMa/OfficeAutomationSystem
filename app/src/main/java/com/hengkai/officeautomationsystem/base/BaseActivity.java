package com.hengkai.officeautomationsystem.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.application.OfficeAutomationSystemApplication;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.base.view.BaseActivityImpl;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.function.login.LoginActivity;
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
    private AlertDialog dialog;

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
        if (getIntent().hasExtra(CommonFinal.MENU_ID)) {
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
//    private void initDialog() {
//        dialog = new Dialog(this, R.style.LoadingDialog);
//        final AVLoadingIndicatorView indicatorView = new AVLoadingIndicatorView(this);
//        indicatorView.setIndicator(LoadingDialogType.BallScaleMultipleIndicator.name());    //设置dialog的样式
//        indicatorView.setIndicatorColor(getResources().getColor(R.color.white));
//
//        dialog.setContentView(indicatorView);
//
//        int deviceWidth = WindowUtil.getScreenWidth();
//        int deviceHeight = WindowUtil.getScreenHeight();
//
//        final Window dialogWindow = dialog.getWindow();
//        if (dialogWindow != null) {
//            WindowManager.LayoutParams params = dialogWindow.getAttributes();
//            params.width = deviceWidth / 8;
//            params.height = deviceHeight / 8;
//            params.gravity = Gravity.CENTER;
//            dialogWindow.setAttributes(params);
//        }
//
//        dialog.setCancelable(false);
//    }
    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.LoadingDialog);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setView(View.inflate(this, R.layout.dialog_base, null));
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

    public void showLoginDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(context, LoginActivity.class));
                dialog.dismiss();
                //看需求, 是否需要发送消息在重新登录后刷新当前列表的数据, 这里暂时先不添加了
            }
        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setMessage("您目前尚未登录，是否前往登录界面").show();
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

    /**
     * 子类实现, 以便于点击noData页面来加载数据
     */
    protected void reloadData(){}

    public void noData(){
        noData(1);
    }

    /**
     * 显示没有数据的页面
     * @param dataLayoutIndex 当前列表是父布局的第几个子布局
     */
    public void noData(int dataLayoutIndex){
        final ViewGroup view = getWindow().getDecorView().findViewById(android.R.id.content);
        final ViewGroup rootView = (ViewGroup)view.getChildAt(0);
        if(rootView != null) {
            final View childView = rootView.getChildAt(dataLayoutIndex);
            if (childView != null) {
                childView.setVisibility(View.GONE);

                final View noDataView = LayoutInflater.from(this).inflate(R.layout.layout_no_data, null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                noDataView.setLayoutParams(layoutParams);
                rootView.addView(noDataView);
                noDataView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rootView.removeView(noDataView);
                        childView.setVisibility(View.VISIBLE);
                        reloadData();
                    }
                });
            }
        }
    }

    public void removeNoDataLayout(int noDataLayoutIndex){
        final ViewGroup view = getWindow().getDecorView().findViewById(android.R.id.content);
        final ViewGroup rootView = (ViewGroup)view.getChildAt(0);
        if(rootView != null) {
            View childView = rootView.getChildAt(noDataLayoutIndex - 1);
            View noDataView = rootView.getChildAt(noDataLayoutIndex);
            if (noDataView != null && childView != null) {
                rootView.removeView(noDataView);
                childView.setVisibility(View.VISIBLE);
            }
        }
    }
}
