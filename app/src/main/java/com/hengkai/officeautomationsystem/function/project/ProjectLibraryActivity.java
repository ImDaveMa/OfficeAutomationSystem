package com.hengkai.officeautomationsystem.function.project;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.management_of_goods.ManagementUseGoodsAdapter;
import com.hengkai.officeautomationsystem.function.management_of_goods.ManagementUseGoodsPresenter;
import com.hengkai.officeautomationsystem.listener.OnItemClickListener;
import com.hengkai.officeautomationsystem.network.entity.ProjectEntity;
import com.hengkai.officeautomationsystem.network.entity.UseGoodsEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.view.refreshing.LoadMoreFooterView;
import com.hengkai.officeautomationsystem.view.refreshing.RefreshHeaderView;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProjectLibraryActivity extends BaseActivity<ProjectLibraryPresenter> implements OnItemClickListener<ProjectEntity.ProjectBean> {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private List<ProjectEntity.ProjectBean> projectList;
    private ProjectLibraryAdapter adapter;
    private int lastID;

    @Override
    protected int setupView() {
        return R.layout.activity_management_of_goods;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        tvTitle.setText("入库记录");
        setupRecyclerView();

        //请求网络
        mPresenter.getProjectList(0);
    }

    /**
     * 关闭页面时调用，用来取消指定的网络请求
     *
     * @return
     */
    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.PROJECT_LIBRARY_ACTIVITY_GET_PROJECT_LIST);
        return tags;
    }

    @Override
    protected ProjectLibraryPresenter bindPresenter() {
        return new ProjectLibraryPresenter();
    }

    /**
     * @param list
     */
    public void prepareData(List<ProjectEntity.ProjectBean> list) {
        if (projectList != null && list != null && list.size() > 0) {
            projectList.addAll(list);
            adapter.notifyDataSetChanged();
            // 获取最后一个ID
            lastID = list.get(list.size() - 1).getProjectId();
        } else {
            // 没有更多数据
            swipeLoadMoreFooter.setloadMoreState(LoadMoreFooterView.REFRESH_STATE_NONE);
            swipeToLoadLayout.setLoadingMore(false);
        }
        stopRefreshing();
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:

                break;
        }
    }

    /**
     * 配置列表相关
     */
    private void setupRecyclerView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        //初始化数据列表
        projectList = new ArrayList<>();
        //创建数据适配器
        adapter = new ProjectLibraryAdapter(this, projectList);
        swipeTarget.setAdapter(adapter);
        swipeTarget.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 清空历史数据
                projectList.clear();
                mPresenter.getProjectList(0);
                swipeLoadMoreFooter.onReset();
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getProjectList(lastID);
            }
        });
    }

    /**
     * 停止下拉刷新和上拉刷新
     */
    public void stopRefreshing() {
        swipeToLoadLayout.setLoadingMore(false);
        swipeToLoadLayout.setRefreshing(false);
    }

    /**
     * 列表项点击事件
     * @param v
     * @param bean
     * @param position
     */
    @Override
    public void onItemClick(View v, ProjectEntity.ProjectBean bean, int position) {
        ToastUtil.showToast(bean.getCreateName());
    }
}
