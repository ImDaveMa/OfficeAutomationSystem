package com.hengkai.officeautomationsystem.function.visit_record;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.visit_record.detail.VisitRecordDetailActivity;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.view.refreshing.LoadMoreFooterView;
import com.hengkai.officeautomationsystem.view.refreshing.RefreshHeaderView;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/7.
 * 拜访跟进记录页面
 */
public class VisitRecordActivity extends BaseActivity<VisitRecordActivityPresenter> {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.tv_title)
    TextView tvTitle;
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

    private boolean isLoadMore = false;
    private VisitRecordActivityAdapter adapter;
    private List<VisitRecordEntity.DATABean> mList;
    /**
     * 当前列表, 长按点击要删除的position
     */
    private int position;

    @Override
    protected int setupView() {
        return R.layout.activity_visit_record;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        mList = new ArrayList<>();

        setupRecyclerView();

        //请求网络 获取列表数据
        mPresenter.getVisitRecordList();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.VISIT_RECORD_ACTIVITY_GET_VISIT_RECORD_LIST);
        tags.add(NetworkTagFinal.VISIT_RECORD_ACTIVITY_DELETE_ITEM);
        return tags;
    }

    @Override
    protected VisitRecordActivityPresenter bindPresenter() {
        return new VisitRecordActivityPresenter();
    }

    /**
     * 配置列表相关
     */
    private void setupRecyclerView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VisitRecordActivityAdapter(mList);
        swipeTarget.setAdapter(adapter);
        swipeTarget.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter.setOnItemClickListener(new VisitRecordActivityAdapter.OnItemClickListener() {
            @Override
            public void onClick(VisitRecordEntity.DATABean bean) {
                Intent intent = new Intent(VisitRecordActivity.this, VisitRecordDetailActivity.class);
                intent.putExtra("type", "item");
                intent.putExtra("currentID", bean.id);
                startActivityForResult(intent, CommonFinal.VISIT_RECORD_REQUEST_CODE);
            }

            @Override
            public void onLongClick(final VisitRecordEntity.DATABean bean, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VisitRecordActivity.this);
                if (!bean.isSubmission) {
                    builder.setMessage("已提交的工作不可删除!");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                } else {
                    builder.setMessage("是否删除当前项?");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            VisitRecordActivity.this.position = position;
                            mPresenter.deleteItem(bean.id);
                            dialog.dismiss();
                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
            }
        });

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
//                pageNum = 1;
                // TODO: 2018/5/10 没有分页, 需要加入分页的逻辑
                mPresenter.getVisitRecordList();
                isLoadMore = false;
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
//                pageNum++;
                mPresenter.getVisitRecordList();
                isLoadMore = true;
            }
        });
    }

    /**
     * 跳转到详情页面
     * @param extra 需要携带的标识
     */
    private void goToDetailPage(String extra) {
        Intent intent = new Intent(VisitRecordActivity.this, VisitRecordDetailActivity.class);
        intent.putExtra("type", extra);
        startActivityForResult(intent, CommonFinal.VISIT_RECORD_REQUEST_CODE);
    }

    /**
     * 停止下拉刷新和上拉刷新
     */
    public void stopRefreshing() {
        swipeToLoadLayout.setLoadingMore(false);
        swipeToLoadLayout.setRefreshing(false);
    }

    public void getVisitRecordList(List<VisitRecordEntity.DATABean> list) {
        if (!isLoadMore) {
            mList.clear();
        }
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.iv_back, R.id.iv_add, R.id.iv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: //返回
                finish();
                break;
            case R.id.iv_add:   //新增拜访
                goToDetailPage("add");
                break;
            case R.id.iv_more:   //更多
                setupPopWindow();
                break;
        }
    }

    /**
     * 配置popupWindow相关
     */
    private void setupPopWindow() {
        View popView = View.inflate(this, R.layout.view_visit_record_pop_window, null);
        PopupWindow popupWindow = new PopupWindow(popView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        initPopupWindowListener(popView, popupWindow);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(8);//设置Z轴的高度
        }
        //设置动画
        popupWindow.setAnimationStyle(R.style.popupWindowAnimation);
        //设置背景颜色
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.white));
        //设置可以获取焦点
        popupWindow.setFocusable(true);
        //设置可以触摸弹出框以外的区域
        popupWindow.setOutsideTouchable(true);
        //更新popupwindow的状态
        popupWindow.update();
        //获取状态栏以及标题栏的高度
        TypedArray actionbarSizeTypedArray = obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        float actionBarHeight = actionbarSizeTypedArray.getDimension(0, 0);//actionbar高度
        int statusBarHeight = getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android"));

        popupWindow.showAtLocation(swipeTarget, Gravity.TOP | Gravity.END, 0, (int) actionBarHeight + statusBarHeight);
    }

    /**
     * 初始化PopupWindow内部控件的监听事件
     */
    private void initPopupWindowListener(View view, final PopupWindow popupWindow) {
        final TextView tv_pop_one_day = view.findViewById(R.id.tv_pop_one_day);
        final TextView tv_pop_seven_day = view.findViewById(R.id.tv_pop_seven_day);
        final TextView tv_pop_Fifteen_day = view.findViewById(R.id.tv_pop_Fifteen_day);

        tv_pop_one_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = tv_pop_one_day.getText().toString().trim();
                ToastUtil.showToast(str);
                popupWindow.dismiss();
            }
        });

        tv_pop_seven_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = tv_pop_seven_day.getText().toString().trim();
                ToastUtil.showToast(str);
                popupWindow.dismiss();
            }
        });

        tv_pop_Fifteen_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = tv_pop_Fifteen_day.getText().toString().trim();
                ToastUtil.showToast(str);
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CommonFinal.VISIT_RECORD_RESULT_CODE) {
            swipeToLoadLayout.setRefreshing(true);//保存或者新增后刷新列表
        }
    }

    /**
     * 删除长按的item, 更新数据刷新列表
     */
    public void deleteItem() {
        adapter.updateData(position);
    }
}
