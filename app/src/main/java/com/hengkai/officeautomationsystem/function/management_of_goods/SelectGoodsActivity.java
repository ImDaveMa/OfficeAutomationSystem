package com.hengkai.officeautomationsystem.function.management_of_goods;

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
import com.hengkai.officeautomationsystem.network.entity.GoodsEntity;
import com.hengkai.officeautomationsystem.view.refreshing.LoadMoreFooterView;
import com.hengkai.officeautomationsystem.view.refreshing.RefreshHeaderView;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectGoodsActivity extends BaseActivity<SelectGoodsPresenter> implements OnItemClickListener<GoodsEntity.GoodsBean> {

    private static final int REQUEST_CODE_ADD_OR_EDIT_GOODS = 10002;

    public static final String KEY_POSITION = "KEY_POSITION";
    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_ID = "KEY_ID";
    public static final String KEY_PRICE = "KEY_PRICE";
    public static final String KEY_NUM = "KEY_NUM";
    public static final String KEY_UNIT = "KEY_UNIT";

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private List<GoodsEntity.GoodsBean> goodsList;
    private SelectGoodsAdapter adapter;
    private int lastID;

    @Override
    protected int setupView() {
        return R.layout.activity_common_search_list;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        tvTitle.setText("物品选择");
        ivAdd.setVisibility(View.VISIBLE);
        setupRecyclerView();

        //请求网络
        mPresenter.getGoodsList(0);
    }

    /**
     * 关闭页面时调用，用来取消指定的网络请求
     *
     * @return
     */
    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.MANAGEMENT_OF_GOODS_ACTIVITY_GET_GOODS_LIST);
        return tags;
    }

    @Override
    protected SelectGoodsPresenter bindPresenter() {
        return new SelectGoodsPresenter();
    }

    /**
     * @param list
     */
    public void prepareData(List<GoodsEntity.GoodsBean> list) {
        if (goodsList != null && list != null && list.size() > 0) {
            goodsList.addAll(list);
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

    @OnClick({R.id.iv_back, R.id.iv_add, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:

                break;
            case R.id.iv_add:
                Intent intent = new Intent(this, AddGoodsActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_OR_EDIT_GOODS);
                break;
        }
    }

    /**
     * 配置列表相关
     */
    private void setupRecyclerView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        //初始化数据列表
        goodsList = new ArrayList<>();
        //创建数据适配器
        adapter = new SelectGoodsAdapter(this, goodsList);
        swipeTarget.setAdapter(adapter);
        swipeTarget.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 清空历史数据
                goodsList.clear();
                mPresenter.getGoodsList(0);
                swipeLoadMoreFooter.onReset();
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getGoodsList(lastID);
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
    public void onItemClick(View v, GoodsEntity.GoodsBean bean, int position) {
        // 获取父页列表中的位置
        int listPosition = getIntent().getIntExtra(KEY_POSITION, 0);

        // 构建Intent对象传值
        Intent intent = new Intent();
        intent.putExtra(KEY_NAME, bean.getName());
        intent.putExtra(KEY_ID, bean.getId());
        intent.putExtra(KEY_PRICE, bean.getCost());
        intent.putExtra(KEY_NUM, bean.getNum());
        intent.putExtra(KEY_UNIT, bean.getUnit());
        intent.putExtra(KEY_POSITION, listPosition);

        // 返回成功并关闭窗口
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE_ADD_OR_EDIT_GOODS){
                swipeToLoadLayout.setRefreshing(true);
            }
        }
    }
}
