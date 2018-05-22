package com.hengkai.officeautomationsystem.function.ask_for_leave.list;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.network.entity.GetAskForLeaveListEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;
import com.jaeger.library.StatusBarUtil;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/22.
 * 请假记录的详情页面
 */
public class AskForLeaveListDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_reason)
    TextView tvReason;

    @Override
    protected int setupView() {
        return R.layout.activity_ask_for_leave_list_detail;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        initParams();
    }

    private void initParams() {
        GetAskForLeaveListEntity.DATABean bean = (GetAskForLeaveListEntity.DATABean) getIntent().getSerializableExtra("bean");
        tvTitle.setText(String.format("%s请假申请单", bean.name));

        tvName.setText(bean.name);
        switch (bean.leave_type) {
            case 1:
                tvType.setText("零星假");
                break;
            case 2:
                tvType.setText("事假");
                break;
            case 3:
                tvType.setText("病假");
                break;
            case 4:
                tvType.setText("婚假");
                break;
            case 5:
                tvType.setText("产假");
                break;
            case 6:
                tvType.setText("陪产假");
                break;
            case 7:
                tvType.setText("丧假");
                break;
        }
        tvStartTime.setText(String.format("开始时间: %s", getTime(bean.start_time)));
        tvEndTime.setText(String.format("结束时间: %s", getTime(bean.end_time)));
        switch (bean.state) {
            case 0:
                tvState.setText("未审批");
                break;
            case 1:
                tvState.setText("审批通过");
                break;
            case 2:
                tvState.setText("审批未通过");
                break;
            case 3:
                tvState.setText("已撤销");
                break;
        }

        tvTime.setText(String.valueOf(bean.time));
        tvReason.setText(bean.reason);
    }

    private String getTime(long time) {
        return DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_1, time);
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        return null;
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
