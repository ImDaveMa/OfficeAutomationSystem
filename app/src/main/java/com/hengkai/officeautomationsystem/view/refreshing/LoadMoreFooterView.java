package com.hengkai.officeautomationsystem.view.refreshing;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.aspsine.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by Harry on 2017/6/29.
 * 列表上拉加载更多的脚布局
 */

public class LoadMoreFooterView extends RefreshingLayout implements SwipeTrigger, SwipeLoadMoreTrigger {

    private static final int REFRESH_STATE_SUCCESS = 1;
    private static final int REFRESH_STATE_ERROR = 2;
    private int loadMoreStateCode = 1;

    public LoadMoreFooterView(Context context) {
        super(context);
    }

    public LoadMoreFooterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onLoadMore() {
//        setText("Loading more");
        setText("正在加载....");
    }

    @Override
    public void onPrepare() {
        setText("");
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
//        if (!isComplete) {
//            if (yScrolled <= -getHeight()) {
//                setText("Release to load more");
//            } else {
//                setText("Swipe to load more");
//            }
//        } else {
//            setText("Load more returning");
//        }
    }

    @Override
    public void onRelease() {
//        setText("Loading more");
        setText("正在加载....");
    }

    @Override
    public void onComplete() {
        // 下拉刷新/上拉加载更多状态 1:成功 2.异常
        if (loadMoreStateCode == REFRESH_STATE_SUCCESS) {
            setText("加载成功....");
        } else if (loadMoreStateCode == REFRESH_STATE_ERROR) {
            setText("加载失败....");
        } else {
            setText("");
        }
    }

    @Override
    public void onReset() {
        setText("");
    }

    public void setloadMoreState(int loadMoreStateCode) {
        this.loadMoreStateCode = loadMoreStateCode;
    }
}
