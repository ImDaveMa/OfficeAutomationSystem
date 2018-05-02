package com.hengkai.officeautomationsystem.function.work_platform;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseFragment;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Harry on 2018/4/26.
 * 工作台 页面
 */
public class WorkPlatformFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;

    @Override
    protected int setupView() {
        return R.layout.fragment_work_platform;
    }

    @Override
    protected void initView(View view) {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(mActivity, getResources().getColor(R.color.app_theme_color), 0);
        unbinder = ButterKnife.bind(this, view);

        // 员工 项目 物品 通用
        initRecyclerView();
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(new WorkPlatformFragmentAdapter());
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
}
