package com.hengkai.officeautomationsystem.view.refreshing;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by Harry on 2017/6/29.
 * 列表下拉刷新的头布局
 */

public class RefreshHeaderView extends RefreshingLayout implements SwipeRefreshTrigger, SwipeTrigger {

    private static final int REFRESH_STATE_SUCCESS = 1;
    private static final int REFRESH_STATE_ERROR = 2;
    private int refreshStateCode = 1;

    public RefreshHeaderView(Context context) {
        super(context);
    }

    public RefreshHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onRefresh() {
        setText("正在刷新....");
    }

    @Override
    public void onPrepare() {
        setText("");
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
//        if (!isComplete) {
//            if (yScrolled >= getHeight()) {
//                setText("Release to refresh");
//            } else {
//                setText("Swipe to refresh");
//            }
//        } else {
//            setText("Refresh returning");
//        }
    }

    @Override
    public void onRelease() {
        setText("刷新中....");
    }

    @Override
    public void onComplete() {
        // 下拉刷新/上拉加载更多状态 1:成功 2.异常
        if (refreshStateCode == REFRESH_STATE_SUCCESS) {
            setText("刷新成功....");
        } else if (refreshStateCode == REFRESH_STATE_ERROR) {
            setText("网络连接异常....");
        } else {
            setText("刷新完成....");
        }
    }

    public void setRefreshState(int refreshStateCode) {
        this.refreshStateCode = refreshStateCode;
    }

    @Override
    public void onReset() {
        setText("");
    }
}
