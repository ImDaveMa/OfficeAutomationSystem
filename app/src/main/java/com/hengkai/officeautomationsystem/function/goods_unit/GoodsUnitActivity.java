package com.hengkai.officeautomationsystem.function.goods_unit;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
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
public class GoodsUnitActivity extends BaseActivity<GoodsUnitPresenter> implements OnItemClickListener<GoodsUnitEntity.UnitBean> {

    public static final String  REQUEST_EXTRA_KEY_TITLE = "REQUEST_EXTRA_KEY_TITLE";

    public static final String EXTRA_KEY_ID = "EXTRA_KEY_ID";
    public static final String  EXTRA_KEY_NAME = "EXTRA_KEY_NAME";

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_add)
    ImageView tvAdd;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    SwipeMenuRecyclerView swipeTarget;
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

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(REQUEST_EXTRA_KEY_TITLE)){
            tvTitle.setText(intent.getStringExtra(REQUEST_EXTRA_KEY_TITLE));
        } else {
            tvTitle.setText("物品单位管理");
        }
        tvAdd.setVisibility(View.VISIBLE);
        setupRecyclerView();

        //请求网络
        mPresenter.getGoodsUnitList();
    }

    /**
     * 初始化数据列表
     */
    private void setupRecyclerView() {

        // 创建菜单
        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
//                SwipeMenuItem editItem = new SwipeMenuItem(getApplicationContext());
//                // 各种文字和图标属性设置。
//                editItem.setText("编辑");
//                editItem.setTextColorResource(R.color.white);
//                editItem.setBackgroundColorResource(R.color.orange);
//                editItem.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
//                editItem.setWidth(200);
//                rightMenu.addMenuItem(editItem); // 在Item左侧添加一个菜单。

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

                final GoodsUnitEntity.UnitBean bean = unitList.get(adapterPosition);
                switch (menuPosition){
                    case 0:
                        AlertDialog.Builder builder = new AlertDialog.Builder(GoodsUnitActivity.this);
                        builder.setTitle("删除提示").setMessage(String.format("确定删除物品单位【%s】",bean.getName())).setNegativeButton("取消", null);
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


//        swipeTarget.setSwipeItemClickListener(new SwipeItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                // TODO 事件
//            }
//        });

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
        tags.add(NetworkTagFinal.GOODS_UNIT_ACTIVITY_ADD);
        tags.add(NetworkTagFinal.GOODS_UNIT_ACTIVITY_DELETE);
        return tags;
    }

    @Override
    protected GoodsUnitPresenter bindPresenter() {
        return new GoodsUnitPresenter();
    }

    /**
     * 加载数据
     *
     * @param list
     */
    public void prepareData(List<GoodsUnitEntity.UnitBean> list) {
        unitList.clear();
        unitList.addAll(list);
        gUnitAdapter.notifyDataSetChanged();
        stopRefreshing();
    }

    @OnClick({R.id.iv_back, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_add:
                addUnit();
                break;
        }
    }

    /**
     * 增加单位
     */
    private void addUnit() {
        LinearLayout llUnit = new LinearLayout(this);
        llUnit.setPadding(40, 15, 40, 15);
        final EditText etUnit = new EditText(this);
        etUnit.setHint("请填写物品单位");
        etUnit.setHintTextColor(getResources().getColor(R.color.black3));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        etUnit.setLayoutParams(params);
        llUnit.addView(etUnit);
        etUnit.setBackgroundResource(R.drawable.shape_edit_text_border);
        etUnit.setPadding(20, 20, 20, 20);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("增加单位").setView(llUnit)
                .setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                if (TextUtils.isEmpty(etUnit.getText())) {
                    ToastUtil.showToast("物品单位不能为空");
                    return;
                }

                // 网络请求
                mPresenter.addGoodsUnit(etUnit.getText().toString());
            }
        });
        builder.show();
    }

    /**
     * 添加物品单位成功
     */
    protected void addSuccess(){
        ToastUtil.showToast("添加物品单位成功");
        swipeToLoadLayout.setRefreshing(true);
    }

    /**
     * 删除物品单位成功
     */
    protected void deleteSuccess(int position){
        ToastUtil.showToast("物品单位删除成功");
        // 重载数据源
        unitList.remove(position);
        swipeTarget.getAdapter().notifyDataSetChanged();
//        // 刷新列表
//        swipeToLoadLayout.setRefreshing(true);
    }

    /**
     * 停止下拉刷新和上拉刷新
     */
    public void stopRefreshing() {
        swipeToLoadLayout.setRefreshing(false);
    }

    /**
     * 点击事件
     * 传出ID和名称
     * @param v
     * @param unitBean
     * @param position
     */
    @Override
    public void onItemClick(View v, GoodsUnitEntity.UnitBean unitBean, int position) {
        Intent requestIntent = getIntent();
        if(requestIntent != null && requestIntent.hasExtra(REQUEST_EXTRA_KEY_TITLE)) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_KEY_ID, unitBean.getId());
            intent.putExtra(EXTRA_KEY_NAME, unitBean.getName());
            setResult(Activity.RESULT_OK, intent);

            finish();
        }
    }

    @Override
    protected void reloadData() {
        mPresenter.getGoodsUnitList();
    }
}
