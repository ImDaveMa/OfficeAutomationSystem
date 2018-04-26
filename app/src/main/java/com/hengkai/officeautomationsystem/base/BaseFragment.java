package com.hengkai.officeautomationsystem.base;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.base.view.BaseFragmentImpl;
import com.hengkai.officeautomationsystem.utils.LoadingDialogType;
import com.hengkai.officeautomationsystem.utils.WindowUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by Harry on 2018/4/17.
 */
public abstract class BaseFragment<P extends BasePresenter> extends BaseFragmentImpl<P> {

    protected Activity mActivity;
    private View view;
    private Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        if (view == null) {
            view = inflater.inflate(setupView(), container, false);
            initView(view);
        }

        //判断Fragment对应的Activity是否存在这个视图
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            //如果存在,那么我就干掉,重写添加,这样的方式我们就可以缓存视图
            parent.removeView(view);
        }

        return view;
    }

    /**
     * 初始化Dialog
     */
    private void initDialog() {
        dialog = new Dialog(mActivity, R.style.LoadingDialog);
        final AVLoadingIndicatorView indicatorView = new AVLoadingIndicatorView(mActivity);
        indicatorView.setIndicator(LoadingDialogType.BallPulseIndicator.name());    //设置dialog的样式
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
    protected void showDialog() {
        initDialog();
        dialog.show();
    }

    /**
     * 关闭LoadingDialog
     */
    protected void dismissDialog() {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    /**
     * @return 布局文件的ID
     */
    protected abstract int setupView();

    /**
     * 初始化布局(例如findViewById)
     *
     * @param view
     */
    protected abstract void initView(View view);

    @Override
    public void onDestroy() {
        super.onDestroy();

        ArrayList<String> tags = cancelNetWork();
        if (tags != null && tags.size() != 0) {
            for (String tag : tags) {
                RxApiManager.get().cancel(tag);
            }
        }
    }

    protected abstract ArrayList<String> cancelNetWork();
}
