package com.hengkai.officeautomationsystem.function.report;

import android.content.Intent;
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
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.report.add.AddReportActivity;
import com.hengkai.officeautomationsystem.network.entity.ReportEntity;
import com.hengkai.officeautomationsystem.view.refreshing.LoadMoreFooterView;
import com.hengkai.officeautomationsystem.view.refreshing.RefreshHeaderView;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/18.
 * 报表(日报和周报共用此页面)
 */
public class ReportActivity extends BaseActivity<ReportPresenter> {

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
    /**
     * 根据传值来判断当前是周报入口进入还是日报入口进入
     */
    private int type;

    private boolean isLoadMore = false;
    private List<ReportEntity.DATEBean> mList;
    private ReportAdapter adapter;

    @Override
    protected int setupView() {
        return R.layout.activity_report;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        String reportType = getIntent().getStringExtra("reportType");
        if (reportType.equals("day")) {
            type = 0;
            tvTitle.setText("日报");
        } else {
            type = 1;
            tvTitle.setText("周报");
        }

        mList = new ArrayList<>();

        setupRecyclerView();

        mPresenter.getReportList(type, 0);
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.REPORT_ACTIVITY_GET_REPORT_LIST);
        return tags;
    }

    @Override
    protected ReportPresenter bindPresenter() {
        return new ReportPresenter();
    }

    @OnClick({R.id.iv_back, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add:
                Intent intent = new Intent(this, AddReportActivity.class);
                intent.putExtra("type", type);
                startActivityForResult(intent, CommonFinal.COMMON_REQUEST_CODE);
                break;
        }
    }

    /**
     * 配置列表相关
     */
    private void setupRecyclerView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReportAdapter(mList, type);
        swipeTarget.setAdapter(adapter);
        swipeTarget.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getReportList(type, 0);
                isLoadMore = false;
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getReportList(type, mList.get(mList.size() - 1).id);
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

    public void getReportList(List<ReportEntity.DATEBean> list) {
        if (!isLoadMore) {
            mList.clear();
        }
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonFinal.COMMON_REQUEST_CODE && resultCode == CommonFinal.COMMON_RESULT_CODE) {
            swipeToLoadLayout.setRefreshing(true);
        }
    }
}
