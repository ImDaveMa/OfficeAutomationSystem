package com.hengkai.officeautomationsystem.function.goods_out;

import android.app.Activity;
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
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.listener.OnItemClickListener;
import com.hengkai.officeautomationsystem.network.entity.UseGoodsEntity;
import com.hengkai.officeautomationsystem.view.refreshing.LoadMoreFooterView;
import com.hengkai.officeautomationsystem.view.refreshing.RefreshHeaderView;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagementUseGoodsActivity extends BaseActivity<ManagementUseGoodsPresenter> implements OnItemClickListener<UseGoodsEntity.OutStorageBean> {

    private static final int REQUEST_CODE_FOR_ADD = 1000;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
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

    private List<UseGoodsEntity.OutStorageBean> goodsOutList;
    private ManagementUseGoodsAdapter adapter;
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

        tvTitle.setText("领用记录");
        ivAdd.setVisibility(View.VISIBLE);
        setupRecyclerView();

        //请求网络
        mPresenter.getUseGoodsList(0);
    }

    /**
     * 关闭页面时调用，用来取消指定的网络请求
     *
     * @return
     */
    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.MANAGEMENT_USE_GOODS_ACTIVITY_GET_USE_GOODS_LIST);
        return tags;
    }

    @Override
    protected ManagementUseGoodsPresenter bindPresenter() {
        return new ManagementUseGoodsPresenter();
    }

    /**
     * @param list
     */
    public void prepareData(List<UseGoodsEntity.OutStorageBean> list) {
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

    @OnClick({R.id.iv_back, R.id.tv_search, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:

                break;
            case R.id.iv_add:
                Intent intent = new Intent(this, UseGoodsActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FOR_ADD);
                break;
        }
    }

    /**
     * 配置列表相关
     */
    private void setupRecyclerView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        //初始化数据列表
        goodsOutList = new ArrayList<>();
        //创建数据适配器
        adapter = new ManagementUseGoodsAdapter(this, this, goodsOutList);
        swipeTarget.setAdapter(adapter);
        swipeTarget.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 清空历史数据
                goodsOutList.clear();
                mPresenter.getUseGoodsList(0);
                swipeLoadMoreFooter.onReset();
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getUseGoodsList(lastID);
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
    public void onItemClick(View v, UseGoodsEntity.OutStorageBean bean, int position) {
        Intent intent = new Intent(this, UseGoodsDetailActivity.class);
        intent.putExtra(UseGoodsDetailActivity.EXTRA_KEY_ID, bean.getId());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_FOR_ADD){
            if(resultCode == Activity.RESULT_OK){
                swipeToLoadLayout.setRefreshing(true);
            }
        }
    }
}
