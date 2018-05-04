package com.hengkai.officeautomationsystem.function.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseFragment;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.function.schedule.ScheduleActivity;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Harry on 2018/4/26.
 * 首页
 */
public class HomeFragment extends BaseFragment {

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
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        return null;
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
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
    public void modifyMenus(){
        if(adapter!=null){
            adapter.updateDataSet();
        }
    }

}
