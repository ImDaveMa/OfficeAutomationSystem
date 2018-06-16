package com.hengkai.officeautomationsystem.function.visit_record.select_visit_unit;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.donkingliang.labels.LabelsView;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.new_unit.NewUnitActivity;
import com.hengkai.officeautomationsystem.network.entity.NewUnitKeywordEntity;
import com.hengkai.officeautomationsystem.network.entity.UnitLibraryEntity;
import com.hengkai.officeautomationsystem.view.refreshing.LoadMoreFooterView;
import com.hengkai.officeautomationsystem.view.refreshing.RefreshHeaderView;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/30.
 * 拜访跟进详情页面点击选择拜访单位, 跳转到此页面
 */
public class SelectVisitUnitActivity extends BaseActivity<SelectVisitUnitPresenter> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_operation)
    TextView tvOperation;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.labels_view)
    LabelsView labelsView;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;

    private List<UnitLibraryEntity.DATABean> mList;
    private boolean isLoadMore = false;
    private SelectVisitUnitAdapter adapter;
    /**
     * 搜索关键字 - 1
     * 搜索名字 - 2
     * 空搜索 - 3
     */
    private int requestCode = -1;
    private List<NewUnitKeywordEntity.DATABean> mKeywordList;
    private int keywordId;
    private String searchContent;

    @Override
    protected int setupView() {
        return R.layout.activity_select_visit_unit;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        mList = new ArrayList<>();

        initTitleBar();
        setupRecyclerView();

        mPresenter.getKeywordList();
    }

    /**
     * 配置关键词标签
     */
    private void setupKeywordList() {
        labelsView.setLabels(mKeywordList, new LabelsView.LabelTextProvider<NewUnitKeywordEntity.DATABean>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, NewUnitKeywordEntity.DATABean data) {
                return data.name;
            }
        });

        labelsView.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                //label是被点击的标签，data是标签所对应的数据，position是标签的位置。
                keywordId = mKeywordList.get(position).id;
                mPresenter.getUnitListWithKeyword(true, keywordId, 0);
                requestCode = 1;
            }
        });
    }

    private void initTitleBar() {
        tvTitle.setText("拜访单位");
        tvOperation.setVisibility(View.VISIBLE);
        tvOperation.setText("新增");
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.SEARCH_UNIT_ACTIVITY_GET_KEYWORD_LIST);
        tags.add(NetworkTagFinal.SEARCH_UNIT_ACTIVITY_GET_UNIT_LIST);
        tags.add(NetworkTagFinal.SEARCH_UNIT_ACTIVITY_GET_UNIT_LIST_WITH_KEYWORD);
        tags.add(NetworkTagFinal.SEARCH_UNIT_ACTIVITY_GET_UNIT_LIST_WITH_NAME);
        return tags;
    }

    @Override
    protected SelectVisitUnitPresenter bindPresenter() {
        return new SelectVisitUnitPresenter();
    }

    @OnClick({R.id.iv_back, R.id.tv_operation, R.id.tv_search, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_cancel:
                removeNoDataLayout(3);
                setViewState(false);
                break;
            case R.id.tv_operation:
                Intent intent = new Intent(this, NewUnitActivity.class);
                startActivityForResult(intent, CommonFinal.COMMON_REQUEST_CODE);
                break;
            case R.id.tv_search:
                searchContent = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(searchContent)) {
                    mPresenter.getUnitList(0);
                    requestCode = 3;
                } else {
                    mPresenter.getUnitListWithName(searchContent, 0);
                    requestCode = 2;
                }
                break;
        }
    }

    public void getKeywordList(List<NewUnitKeywordEntity.DATABean> list) {
        mKeywordList = list;
        setupKeywordList();
    }

    /**
     * 配置列表相关
     */
    private void setupRecyclerView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SelectVisitUnitAdapter(mList);
        swipeTarget.setAdapter(adapter);
        swipeTarget.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        swipeToLoadLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                switch (requestCode) {
                    case 1:
                        mPresenter.getUnitListWithKeyword(false, keywordId, 0);
                        break;
                    case 2:
                        mPresenter.getUnitListWithName(searchContent, 0);
                        break;
                    case 3:
                        mPresenter.getUnitList(0);
                        break;
                    default:
                        break;
                }
                isLoadMore = false;
            }
        });
        swipeToLoadLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                switch (requestCode) {
                    case 1:
                        mPresenter.getUnitListWithKeyword(false, keywordId, mList.get(mList.size() - 1).id);
                        break;
                    case 2:
                        mPresenter.getUnitListWithName(searchContent, mList.get(mList.size() - 1).id);
                        break;
                    case 3:
                        mPresenter.getUnitList(mList.get(mList.size() - 1).id);
                        break;
                    default:
                        break;
                }
                isLoadMore = true;
            }
        });

        adapter.setOnItemClickListener(new SelectVisitUnitAdapter.OnItemClickListener() {
            @Override
            public void onClick(int ID, String name) {
                Intent intent = new Intent();
                intent.putExtra("ID", ID);
                intent.putExtra("name", name);
                setResult(CommonFinal.SELECT_VISIT_UNIT_RESULT_CODE, intent);
                finish();
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

    public void getUnitList(List<UnitLibraryEntity.DATABean> list) {
        setViewState(true);
        if (!isLoadMore) {
            mList.clear();
        }
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    /**
     * @param state true 隐藏标签控件,显示列表, false 隐藏列表, 显示标签控件
     */
    public void setViewState(boolean state) {
        if (state) {
            swipeToLoadLayout.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        } else {
            swipeToLoadLayout.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonFinal.COMMON_REQUEST_CODE && resultCode == RESULT_OK) {
            if (swipeToLoadLayout.getVisibility() == View.VISIBLE) {
                swipeToLoadLayout.setRefreshing(true);
            }
        }
    }

    @Override
    protected void reloadData() {
        mPresenter.getKeywordList();
        setViewState(false);
    }
}
