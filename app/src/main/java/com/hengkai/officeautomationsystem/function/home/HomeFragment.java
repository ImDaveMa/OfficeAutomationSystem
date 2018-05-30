package com.hengkai.officeautomationsystem.function.home;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseFragment;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.approve.ApproveListActivity;
import com.hengkai.officeautomationsystem.function.message.MessageListActivity;
import com.hengkai.officeautomationsystem.function.notice.NoticeListActivity;
import com.hengkai.officeautomationsystem.function.schedule.ScheduleActivity;
import com.hengkai.officeautomationsystem.network.entity.MessageEntity;
import com.hengkai.officeautomationsystem.network.entity.NoticeEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;
import com.hengkai.officeautomationsystem.utils.WindowUtil;
import com.jaeger.library.StatusBarUtil;
import com.paradoxie.autoscrolltextview.VerticalTextview;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Harry on 2018/4/26.
 * 首页
 */
public class HomeFragment extends BaseFragment<HomePresenter> {

    @BindView(R.id.tv_message1)
    TextView tvMessage1;
    @BindView(R.id.tv_message2)
    TextView tvMessage2;
    @BindView(R.id.cv_home_msg_container)
    CardView cvHomeMsgContainer;
    @BindView(R.id.tv_to_do)
    TextView tvToDo;
    VerticalTextview tvReceiveMessage;
    @BindView(R.id.cv_home_approve_container)
    CardView cvHomeApproveContainer;
    @BindView(R.id.tv_text2)
    TextView tvText2;
    @BindView(R.id.tv_schedule_time)
    TextView tvScheduleTime;
    @BindView(R.id.tv_receive_schedule)
    TextView tvReceiveSchedule;
    @BindView(R.id.tv_statistics1)
    TextView tvStatistics1;
    @BindView(R.id.tv_statistics2)
    TextView tvStatistics2;
    Unbinder unbinder1;
    @BindView(R.id.tv_message1_time)
    TextView tvMessage1Time;
    @BindView(R.id.tv_message2_time)
    TextView tvMessage2Time;

    private HomeFragmentGridLayoutAdapter adapter;

    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.rl_schedule)
    RelativeLayout rlSchedule;

    @Override
    protected int setupView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(mActivity, getResources().getColor(R.color.app_theme_color), 0);
        unbinder = ButterKnife.bind(this, view);

        // 初始化滚动文字
        tvReceiveMessage = view.findViewById(R.id.tv_receive_message);
        tvReceiveMessage.setText(13, 0, getResources().getColor(R.color.black1));//设置属性,具体跟踪源码
        tvReceiveMessage.setTextStillTime(3000);//设置停留时长间隔
        tvReceiveMessage.setAnimTime(300);//设置进入和退出的时间间隔

        initRecyclerView();

        mPresenter.getNoticeList();
        mPresenter.getMsgList();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.HOME_FRAGMENT_GET_APPROVE_DATA);
        tags.add(NetworkTagFinal.HOME_FRAGMENT_GET_MSG_DATA);
        tags.add(NetworkTagFinal.HOME_FRAGMENT_GET_NOTICE_DATA);
        return tags;
    }

    @Override
    protected HomePresenter bindPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 4) {
            @Override
            public boolean canScrollHorizontally() { //禁止横向滑动
                return false;
            }

            @Override
            public boolean canScrollVertically() {  //禁止纵向滑动
                return false;
            }
        };

        adapter = new HomeFragmentGridLayoutAdapter(mActivity);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @OnClick({R.id.rl_schedule, R.id.cv_home_approve_container, R.id.cv_home_msg_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_schedule:
                startActivity(new Intent(mActivity, ScheduleActivity.class));
                break;
            case R.id.cv_home_approve_container:
                startActivity(new Intent(mActivity, NoticeListActivity.class));
                break;
            case R.id.cv_home_msg_container:
                startActivity(new Intent(mActivity, MessageListActivity.class));
                break;
        }
    }

    /**
     * 更新列表
     */
    public void updateAll() {
        // 更新消息
        mPresenter.getNoticeList();
        mPresenter.getMsgList();

        // 更新菜单
        if (adapter != null) {
            adapter.updateDataSet();
        }
    }

    protected void prepareApproveList(List<MessageEntity.MsgBean> list, int total) {}

    protected void prepareMsgList(List<MessageEntity.MsgBean> list) {
        if (list != null && list.size() > 0) {
            MessageEntity.MsgBean bean = list.get(0);
            tvMessage1.setText(String.format("您收到了一条%s消息", bean.getTypeName()));
            tvMessage1Time.setText(DateFormatUtils.getFormatedNewsTime(bean.getCreateTime()));
        }
        if (list != null && list.size() > 1) {
            MessageEntity.MsgBean bean = list.get(1);
            tvMessage2.setText(String.format("您收到了一条%s消息", bean.getTypeName()));
            tvMessage2Time.setText(DateFormatUtils.getFormatedNewsTime(bean.getCreateTime()));
        }
    }

    protected void prepareNoticeList(List<NoticeEntity.DATEBean> list, int total) {
        tvToDo.setText(String.format("%d条未读", total));
        ArrayList<String> items = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (NoticeEntity.DATEBean bean :  list) {
                items.add(bean.noticeTitle);
            }
        } else {
            items.add("暂无数据");
        }
        tvReceiveMessage.setTextList(items);

        tvReceiveMessage.startAutoScroll();
    }

//    //停止滚动 报错
//    @Override
//    public void onPause() {
//        super.onPause();
//        tvReceiveMessage.stopAutoScroll();
//    }
}
