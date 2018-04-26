package com.hengkai.officeautomationsystem.view.refreshing;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;


/**
 * Created by harry on 2017/6/28.
 * 自定义的View, 继承自RelativeLayout, 为RecyclerView下拉刷新或者上拉加载的布局
 */

public class RefreshingLayout extends RelativeLayout {

    private View view;
    private TextView tv_load;

    public RefreshingLayout(Context context) {
        super(context);
    }

    public RefreshingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = LayoutInflater.from(context).inflate(R.layout.view_header_footer, this, true);
//        view = View.inflate(context, R.layout.view_footer, this);
        tv_load = (TextView) view.findViewById(R.id.tv_load);
    }

    public void setText(String text) {
        tv_load.setText(text);
    }

    public void setCompleteText(String text) {
        tv_load.setText(text);
    }
}
