package com.hengkai.officeautomationsystem.function.management_of_goods;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.goods_unit.GoodsUnitActivity;
import com.hengkai.officeautomationsystem.listener.OnItemClickListener;
import com.hengkai.officeautomationsystem.network.entity.GoodsEntity;
import com.hengkai.officeautomationsystem.network.entity.GoodsUnitEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.view.refreshing.LoadMoreFooterView;
import com.hengkai.officeautomationsystem.view.refreshing.RefreshHeaderView;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/4/28.
 * 物品管理(物品列表)页面
 */
public class ManagementOfGoodsActivity extends BaseActivity<ManagementOfGoodsPresenter> implements OnItemClickListener<GoodsEntity.GoodsBean> {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    SwipeMenuRecyclerView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private List<GoodsEntity.GoodsBean> goodsList;
    private ManagementOfGoodsAdapter adapter;
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

        tvTitle.setText("物品管理");
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
        tags.add(NetworkTagFinal.MANAGEMENT_OF_GOODS_ACTIVITY_DELETE);
        return tags;
    }

    @Override
    protected ManagementOfGoodsPresenter bindPresenter() {
        return new ManagementOfGoodsPresenter();
    }

    /**
     * @param list
     */
    public void prepareData(List<GoodsEntity.GoodsBean> list) {
        if (goodsList != null && list != null && list.size() > 0) {
            goodsList.addAll(list);
            adapter.notifyDataSetChanged();
            // 获取最后一个ID
            lastID = list.get(list.size()-1).getId();
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

        // 创建菜单
        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
                SwipeMenuItem editItem = new SwipeMenuItem(getApplicationContext());
                // 各种文字和图标属性设置。
                editItem.setText("编辑");
                editItem.setTextColorResource(R.color.white);
                editItem.setBackgroundColorResource(R.color.orange);
                editItem.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
                editItem.setWidth(200);
                rightMenu.addMenuItem(editItem); // 在Item左侧添加一个菜单。

                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                // 各种文字和图标属性设置。
                deleteItem.setText("删除");
                deleteItem.setTextColorResource(R.color.white);
                deleteItem.setBackgroundColorResource(R.color.red1);
                deleteItem.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
                deleteItem.setWidth(200);
                rightMenu.addMenuItem(deleteItem); // 在Item右侧添加一个菜单。

            }
        };
        swipeTarget.setSwipeMenuCreator(mSwipeMenuCreator);

        // 菜单点击监听。
        SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();

                // int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                final int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

                final GoodsEntity.GoodsBean bean = goodsList.get(adapterPosition);
                switch (menuPosition){
                    case 0:
                        ToastUtil.showToast("编辑物品：" + bean.getName());
                        break;
                    case 1:
                        AlertDialog.Builder builder = new AlertDialog.Builder(ManagementOfGoodsActivity.this);
                        builder.setTitle("删除提示").setMessage(String.format("确定删除物品【%s】",bean.getName())).setNegativeButton("取消", null);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // 网络请求
                                mPresenter.deleteGoodsUnit(bean.getId(), adapterPosition);
                            }
                        });
                        builder.show();
                        break;
                }
            }
        };
        swipeTarget.setSwipeMenuItemClickListener(mMenuItemClickListener);


        // 设置布局管理器
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        //初始化数据列表
        goodsList = new ArrayList<>();
        //创建数据适配器
        adapter = new ManagementOfGoodsAdapter(this, goodsList);
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
     * 删除物品成功
     */
    protected void deleteSuccess(int position){
        ToastUtil.showToast("物品单位删除成功");
        // 重载数据源
        goodsList.remove(position);
        swipeTarget.getAdapter().notifyDataSetChanged();
    }


    /**
     * 停止下拉刷新和上拉刷新
     */
    public void stopRefreshing() {
        swipeToLoadLayout.setLoadingMore(false);
        swipeToLoadLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(View v, GoodsEntity.GoodsBean goodsBean, int position) {
        Intent intent = new Intent(this, GoodsDetailActivity.class);
        intent.putExtra(GoodsDetailActivity.EXTRA_KEY_ID, goodsBean.getId());
        startActivity(intent);
    }
}
