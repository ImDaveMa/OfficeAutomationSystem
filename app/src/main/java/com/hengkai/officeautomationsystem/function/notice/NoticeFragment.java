package com.hengkai.officeautomationsystem.function.notice;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseFragment;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.listener.OnItemClickListener;
import com.hengkai.officeautomationsystem.network.entity.NoticeEntity;
import com.hengkai.officeautomationsystem.view.refreshing.LoadMoreFooterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoticeFragment extends BaseFragment<NoticeListPresenter> implements OnItemClickListener<NoticeEntity.DATEBean> {

    public static final String BUNDLE_KEY_STATE = "BUNDLE_KEY_STATE";

    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private List<NoticeEntity.DATEBean> mList;
    private NoticeListAdapter adapter;
    private int lastID;
    private int state;

    @Override
    protected int setupView() {
        return R.layout.fragment_common_list;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);

        setupRecyclerView();
        if (getArguments() != null) {
            state = getArguments().getInt(BUNDLE_KEY_STATE, NoticeListModel.SEARCHSTATUS_NORMAL); // 分类状态，默认全部
        } else {
            state = NoticeListModel.SEARCHSTATUS_NORMAL; // 全部
        }
        //请求网络
        mPresenter.getNoticeList(0, state);
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.NOTICE_FRAGMENT_GET_LIST);
        return tags;
    }

    @Override
    protected NoticeListPresenter bindPresenter() {
        return new NoticeListPresenter();
    }

    /**
     * @param list
     */
    public void prepareData(List<NoticeEntity.DATEBean> list) {
        if (mList != null && list != null && list.size() > 0) {
            mList.addAll(list);
            adapter.notifyDataSetChanged();
            // 获取最后一个ID
            lastID = list.get(list.size() - 1).id;
        } else {
            // 没有更多数据
            swipeLoadMoreFooter.setloadMoreState(LoadMoreFooterView.REFRESH_STATE_NONE);
            swipeToLoadLayout.setLoadingMore(false);
        }
        stopRefreshing();
    }

    /**
     * 配置列表相关
     */
    private void setupRecyclerView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        //初始化数据列表
        mList = new ArrayList<>();
        //创建数据适配器
        adapter = new NoticeListAdapter(this, mList);
        swipeTarget.setAdapter(adapter);
        swipeTarget.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 清空历史数据
                mList.clear();
                mPresenter.getNoticeList(0, state);
                swipeLoadMoreFooter.onReset();
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getNoticeList(lastID, state);
            }
        });
    }

    @Override
    protected void reloadData() {
        mPresenter.getNoticeList(0, state);
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
    public void onItemClick(View v, NoticeEntity.DATEBean bean, int position) {
        Intent intent = new Intent(getContext(), NoticeDetailActivity.class);
        intent.putExtra(NoticeDetailActivity.EXTRA_KEY_ID, bean.id);
        startActivityForResult(intent, 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            swipeToLoadLayout.setRefreshing(true);
        }
    }
}
