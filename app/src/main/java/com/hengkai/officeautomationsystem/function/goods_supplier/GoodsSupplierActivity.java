package com.hengkai.officeautomationsystem.function.goods_supplier;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.management_of_goods.AddGoodsActivity;
import com.hengkai.officeautomationsystem.function.management_of_goods.ManagementOfGoodsActivity;
import com.hengkai.officeautomationsystem.listener.OnItemClickListener;
import com.hengkai.officeautomationsystem.network.entity.GoodsEntity;
import com.hengkai.officeautomationsystem.network.entity.GoodsSupplierEntity;
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
public class GoodsSupplierActivity extends BaseActivity<GoodsSupplierPresenter> implements OnItemClickListener<GoodsSupplierEntity.SupplierBean> {

    private static final int REQUEST_CODE_ADD_OR_EDIT_GOODS = 10003;

    public static final String  REQUEST_EXTRA_KEY_TITLE = "REQUEST_EXTRA_KEY_TITLE";

    public static final String EXTRA_KEY_ID = "EXTRA_KEY_ID";
    public static final String  EXTRA_KEY_NAME = "EXTRA_KEY_NAME";

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
    SwipeMenuRecyclerView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;

    private List<GoodsSupplierEntity.SupplierBean> supplierList;
    private GoodsSupplierAdapter gUnitAdapter;

    @Override
    protected int setupView() {
        return R.layout.activity_goods_supplier;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(REQUEST_EXTRA_KEY_TITLE)){
            tvTitle.setText(intent.getStringExtra(REQUEST_EXTRA_KEY_TITLE));
        } else {
            tvTitle.setText("供应商管理");
        }

        ivAdd.setVisibility(View.VISIBLE);
        setupRecyclerView();

        //请求网络
        mPresenter.getGoodsSupplierList("",0,0);
    }

    /**
     *  初始化数据列表
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

//                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
//                // 各种文字和图标属性设置。
//                deleteItem.setText("删除");
//                deleteItem.setTextColorResource(R.color.white);
//                deleteItem.setBackgroundColorResource(R.color.red1);
//                deleteItem.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
//                deleteItem.setWidth(200);
//                rightMenu.addMenuItem(deleteItem); // 在Item右侧添加一个菜单。

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

                final GoodsSupplierEntity.SupplierBean bean = supplierList.get(adapterPosition);
                switch (menuPosition){
                    case 0:
                        Intent intent = new Intent(GoodsSupplierActivity.this, AddGoodsSupplierActivity.class);
                        intent.putExtra(AddGoodsSupplierActivity.EXTRA_KEY_ID, bean.getId());
                        startActivityForResult(intent, REQUEST_CODE_ADD_OR_EDIT_GOODS);
                        break;
//                    case 1:
//                        AlertDialog.Builder builder = new AlertDialog.Builder(GoodsSupplierActivity.this);
//                        builder.setTitle("删除提示").setMessage(String.format("确定删除物品【%s】",bean.getName())).setNegativeButton("取消", null);
//                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int which) {
//                                // 网络请求
//                                mPresenter.deleteGoodsUnit(bean.getId(), adapterPosition);
//                            }
//                        });
//                        builder.show();
//                        break;
                }
            }
        };
        swipeTarget.setSwipeMenuItemClickListener(mMenuItemClickListener);


        //初始化数据列表
        supplierList = new ArrayList<>();
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        gUnitAdapter = new GoodsSupplierAdapter(this, supplierList);
        swipeTarget.setAdapter(gUnitAdapter);
        swipeTarget.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getGoodsSupplierList("",0,0);
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
    protected GoodsSupplierPresenter bindPresenter() {
        return new GoodsSupplierPresenter();
    }

    /**
     * 加载数据
     * @param list
     */
    public void prepareData(List<GoodsSupplierEntity.SupplierBean> list, List<GoodsSupplierEntity.ParamBean> paramList) {
        // 保存参数 paramList


        // 展示列表
        supplierList.clear();
        supplierList.addAll(list);
        gUnitAdapter.notifyDataSetChanged();
        stopRefreshing();
    }

    @OnClick({R.id.iv_back, R.id.iv_add, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_add:
                Intent intent = new Intent(this, AddGoodsSupplierActivity.class);

                startActivityForResult(intent, REQUEST_CODE_ADD_OR_EDIT_GOODS);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD_OR_EDIT_GOODS && resultCode == RESULT_OK){
            swipeToLoadLayout.setRefreshing(true);
        }
    }

    /**
     * 点击事件
     * @param v
     * @param unitBean
     * @param position
     */
    @Override
    public void onItemClick(View v, GoodsSupplierEntity.SupplierBean unitBean, int position) {
        Intent requestIntent = getIntent();
        if(requestIntent != null && requestIntent.hasExtra(REQUEST_EXTRA_KEY_TITLE)) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_KEY_ID, unitBean.getId());
            intent.putExtra(EXTRA_KEY_NAME, unitBean.getName());
            setResult(Activity.RESULT_OK, intent);

            finish();
        } else {
            Intent intent = new Intent(this, GoodsSupplierDetailActivity.class);
            startActivity(intent);
        }
    }
}
