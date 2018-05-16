package com.hengkai.officeautomationsystem.function.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseFragment;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.schedule.ScheduleActivity;
import com.hengkai.officeautomationsystem.network.entity.MessageEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

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
    @BindView(R.id.tv_receive_message)
    TextView tvReceiveMessage;
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

        initRecyclerView();

        mPresenter.getApproveList();
        mPresenter.getMsgList();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.HOME_FRAGMENT_GET_APPROVE_DATA);
        tags.add(NetworkTagFinal.HOME_FRAGMENT_GET_MSG_DATA);
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

    @OnClick({R.id.rl_schedule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_schedule:
                startActivity(new Intent(mActivity, ScheduleActivity.class));
                break;
        }
    }

    /**
     * 更新列表
     */
    public void updateAll() {
        // 更新消息
        mPresenter.getApproveList();
        mPresenter.getMsgList();

        // 更新菜单
        if (adapter != null) {
            adapter.updateDataSet();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    protected void prepareApproveList(List<MessageEntity.MsgBean> list, int total){
        tvToDo.setText(String.format("您有%d条待办事项", total));
        if(list != null && list.size() > 0){
            MessageEntity.MsgBean bean = list.get(0);
            tvReceiveMessage.setText(String.format("您收到了一条%s，请尽快处理",bean.getTypeName()));
        }
        cvHomeApproveContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("跳转到审批列表");
            }
        });
    }

    protected void prepareMsgList(List<MessageEntity.MsgBean> list){
        if(list != null && list.size() > 0){
            MessageEntity.MsgBean bean = list.get(0);
            tvMessage1.setText(String.format("您收到了一条%s消息",bean.getTypeName()));
        }
        if(list != null && list.size() > 1){
            MessageEntity.MsgBean bean = list.get(1);
            tvMessage1.setText(String.format("您收到了一条%s消息",bean.getTypeName()));
        }
        cvHomeMsgContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("跳转到消息列表");
            }
        });
    }
}
