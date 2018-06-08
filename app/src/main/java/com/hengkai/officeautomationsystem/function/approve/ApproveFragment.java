package com.hengkai.officeautomationsystem.function.approve;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseFragment;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.listener.OnItemClickListener;
import com.hengkai.officeautomationsystem.network.entity.MessageEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.view.refreshing.LoadMoreFooterView;
import com.hengkai.officeautomationsystem.view.refreshing.RefreshHeaderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApproveFragment extends BaseFragment<ApproveListPresenter> implements OnItemClickListener<MessageEntity.MsgBean> {

    public static final String BUNDLE_KEY_STATE = "BUNDLE_KEY_STATE";

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

    private List<MessageEntity.MsgBean> goodsOutList;
    private ApproveListAdapter adapter;
    private int lastID;
    private int state;

    @Override
    protected int setupView() {
        return R.layout.fragment_approve_waiting;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);

        setupRecyclerView();
        if (getArguments() != null) {
            state = getArguments().getInt(BUNDLE_KEY_STATE, -1); // 分类状态
        } else {
            state = -1; // 全部
        }
        //请求网络
        mPresenter.getApproveList(0, 0, -1, state);
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.APPROVE_LIST_ACTIVITY_GET_LIST);
        return tags;
    }

    @Override
    protected ApproveListPresenter bindPresenter() {
        return new ApproveListPresenter();
    }

    /**
     * @param list
     */
    public void prepareData(List<MessageEntity.MsgBean> list) {
        if (goodsOutList != null && list != null && list.size() > 0) {
            goodsOutList.addAll(list);
            adapter.notifyDataSetChanged();
            // 获取最后一个ID
            lastID = list.get(list.size() - 1).getId();
        } else {
            // 没有更多数据
            swipeLoadMoreFooter.setloadMoreState(LoadMoreFooterView.REFRESH_STATE_NONE);
            swipeToLoadLayout.setLoadingMore(false);
        }
        stopRefreshing();
    }

    @OnClick({R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:

                break;
        }
    }

    /**
     * 配置列表相关
     */
    private void setupRecyclerView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        //初始化数据列表
        goodsOutList = new ArrayList<>();
        //创建数据适配器
        adapter = new ApproveListAdapter(this, goodsOutList);
        swipeTarget.setAdapter(adapter);
        swipeTarget.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 清空历史数据
                goodsOutList.clear();
                mPresenter.getApproveList(0, 0, -1, state);
                swipeLoadMoreFooter.onReset();
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getApproveList(lastID, 0, -1, state);
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
    public void onItemClick(View v, MessageEntity.MsgBean bean, int position) {
//        Intent intent = new Intent(this, UseGoodsDetailActivity.class);
//        intent.putExtra(UseGoodsDetailActivity.EXTRA_KEY_ID, bean.getId());
//        startActivity(intent);
        ToastUtil.showToast(bean.getTypeName());
    }

    @Override
    protected void reloadData() {
        //请求网络
        mPresenter.getApproveList(0, 0, -1, state);
    }
}
