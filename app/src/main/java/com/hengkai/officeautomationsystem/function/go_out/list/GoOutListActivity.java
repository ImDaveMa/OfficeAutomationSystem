package com.hengkai.officeautomationsystem.function.go_out.list;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.ask_for_leave.list.AskForLeaveListAdapter;
import com.hengkai.officeautomationsystem.network.entity.GetAskForLeaveListEntity;
import com.hengkai.officeautomationsystem.view.refreshing.LoadMoreFooterView;
import com.hengkai.officeautomationsystem.view.refreshing.RefreshHeaderView;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/22.
 * 外出的列表页面
 */
public class GoOutListActivity extends BaseActivity<GoOutListPresenter> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private boolean isLoadMore = false;
    private List<GetAskForLeaveListEntity.DATABean> mList;
    private GoOutListAdapter adapter;

    @Override
    protected int setupView() {
        return R.layout.activity_go_out_list;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);
        tvTitle.setText("请假记录");

        mList = new ArrayList<>();

        setupRecyclerView();

        mPresenter.getAskForLeaveList(0);
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.ASK_FOR_LEAVE_LIST_ACTIVITY_GET_LIST);
        return tags;
    }

    @Override
    protected GoOutListPresenter bindPresenter() {
        return new GoOutListPresenter();
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /**
     * 配置列表相关
     */
    private void setupRecyclerView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GoOutListAdapter(mList);
        swipeTarget.setAdapter(adapter);
        swipeTarget.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getAskForLeaveList(0);
                isLoadMore = false;
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getAskForLeaveList(mList.get(mList.size() - 1).id);
                isLoadMore = true;
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

    public void getAskForLeaveList(List<GetAskForLeaveListEntity.DATABean> list) {
        if (!isLoadMore) {
            mList.clear();
        }
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
