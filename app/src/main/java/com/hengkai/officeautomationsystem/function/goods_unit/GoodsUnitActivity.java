package com.hengkai.officeautomationsystem.function.goods_unit;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.listener.OnItemClickListener;
import com.hengkai.officeautomationsystem.network.entity.GoodsUnitEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.view.refreshing.RefreshHeaderView;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/4/28.
 * 物品管理(物品列表)页面
 */
public class GoodsUnitActivity extends BaseActivity<GoodsUnitPresenter> implements OnItemClickListener<GoodsUnitEntity.UnitBean> {

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
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private List<GoodsUnitEntity.UnitBean> unitList;
    private GoodsUnitAdapter gUnitAdapter;

    @Override
    protected int setupView() {
        return R.layout.activity_goods_unit;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        tvTitle.setText("物品单位管理");
        setupRecyclerView();

        //请求网络
        mPresenter.getGoodsUnitList();
    }

    /**
     *  初始化数据列表
     */
    private void setupRecyclerView() {
        //初始化数据列表
        unitList = new ArrayList<>();
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        gUnitAdapter = new GoodsUnitAdapter(this, unitList);
        swipeTarget.setAdapter(gUnitAdapter);
        swipeTarget.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getGoodsUnitList();
            }
        });
    }

    /**
     * 关闭页面时调用，用来取消指定的网络请求
     *
     * @return
     */
    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.GOODS_UNIT_ACTIVITY_LIST);
        return tags;
    }

    @Override
    protected GoodsUnitPresenter bindPresenter() {
        return new GoodsUnitPresenter();
    }

    /**
     * 加载数据
     * @param list
     */
    public void prepareData(List<GoodsUnitEntity.UnitBean> list) {
        unitList.clear();
        unitList.addAll(list);
        gUnitAdapter.notifyDataSetChanged();
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
     * 停止下拉刷新和上拉刷新
     */
    public void stopRefreshing() {
        swipeToLoadLayout.setRefreshing(false);
    }

    /**
     * 点击事假
     * @param v
     * @param unitBean
     * @param position
     */
    @Override
    public void onItemClick(View v, GoodsUnitEntity.UnitBean unitBean, int position) {
        ToastUtil.showToast(unitBean.getName());
    }
}
