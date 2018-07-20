package com.hengkai.officeautomationsystem.function.message;

import android.content.Intent;
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
import com.hengkai.officeautomationsystem.base.BaseFragment;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.notice.NoticeDetailActivity;
import com.hengkai.officeautomationsystem.function.notice.NoticeListAdapter;
import com.hengkai.officeautomationsystem.function.notice.NoticeListModel;
import com.hengkai.officeautomationsystem.function.notice.NoticeListPresenter;
import com.hengkai.officeautomationsystem.function.visit_record.comment.CommentVisitActivity;
import com.hengkai.officeautomationsystem.listener.OnItemClickListener;
import com.hengkai.officeautomationsystem.network.entity.MessageEntity;
import com.hengkai.officeautomationsystem.network.entity.NoticeEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.view.refreshing.LoadMoreFooterView;
import com.hengkai.officeautomationsystem.view.refreshing.RefreshHeaderView;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageFragment extends BaseFragment<MessageListPresenter> implements OnItemClickListener<MessageEntity.MsgBean> {

    public static final String BUNDLE_KEY_STATE = "BUNDLE_KEY_STATE";

    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private List<MessageEntity.MsgBean> mList;
    private MessageListAdapter adapter;
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

        state = getArguments().getInt(BUNDLE_KEY_STATE, MessageListModel.STATE_APPROVE); // 默认为审批
        //请求网络
        mPresenter.getMsgList(0, state);
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.MESSAGE_LIST_ACTIVITY_GET_LIST);
        return tags;
    }

    @Override
    protected MessageListPresenter bindPresenter() {
        return new MessageListPresenter();
    }

    /**
     * @param list
     */
    public void prepareData(List<MessageEntity.MsgBean> list) {
        if (mList != null && list != null && list.size() > 0) {
            mList.addAll(list);
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

    /**
     * 配置列表相关
     */
    private void setupRecyclerView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(getContext()));
        //初始化数据列表
        mList = new ArrayList<>();
        //创建数据适配器
        adapter = new MessageListAdapter(this, mList);
        swipeTarget.setAdapter(adapter);
        swipeTarget.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 清空历史数据
                mList.clear();
                mPresenter.getMsgList(0, state);
                swipeLoadMoreFooter.onReset();
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getMsgList(lastID, state);
            }
        });
    }

    @Override
    protected void reloadData() {
        mPresenter.getMsgList(0, state);
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
//        Intent intent = new Intent(getContext(), MessageDetailActivity.class);
//        intent.putExtra(MessageDetailActivity.EXTRA_KEY_ID, bean.id);
//        startActivityForResult(intent, 1000);


        switch (state){
            case MessageListModel.STATE_APPROVE: // 审批
                if(bean.getProject_name().equals("sd_visit")) {//拜访跟进审批
                    Intent toIntent = new Intent(mActivity, CommentVisitActivity.class);
                    toIntent.putExtra("currentID", bean.getProject_id());
                    startActivity(toIntent);
                } else {
                    ToastUtil.showToast("暂无其它审批");
                }
                break;
            case MessageListModel.STATE_MESSAGE: // 消息
                ToastUtil.showToast(bean.getTypeName());
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            swipeToLoadLayout.setRefreshing(true);
        }
    }
}
