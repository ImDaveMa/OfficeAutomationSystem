package com.hengkai.officeautomationsystem.function.unit_library.search;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.donkingliang.labels.LabelsView;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.unit_library.UnitLibraryActivityAdapter;
import com.hengkai.officeautomationsystem.function.visit_record.select_visit_unit.KeywordAdapter;
import com.hengkai.officeautomationsystem.network.entity.NewUnitKeywordEntity;
import com.hengkai.officeautomationsystem.network.entity.UnitLibraryEntity;
import com.hengkai.officeautomationsystem.utils.PinYinUtil;
import com.hengkai.officeautomationsystem.view.QuickIndexBar;
import com.hengkai.officeautomationsystem.view.refreshing.LoadMoreFooterView;
import com.hengkai.officeautomationsystem.view.refreshing.RefreshHeaderView;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/28.
 * 单位库的搜索页面
 */
public class SearchUnitActivity extends BaseActivity<SearchUnitPresenter> {

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.swipe_refresh_header)
    RefreshHeaderView swipeRefreshHeader;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.rl_keyword)
    RelativeLayout rlKeyword;
    @BindView(R.id.quick_index_bar)
    QuickIndexBar quickIndexBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private boolean isLoadMore = false;
    private List<NewUnitKeywordEntity.DATABean> mKeywordList;
    private UnitLibraryActivityAdapter adapter;
    private List<UnitLibraryEntity.DATABean> mList;
    /**
     * 搜索关键字 - 1
     * 搜索名字 - 2
     * 空搜索 - 3
     */
    private int requestCode = -1;
    private String searchContent;
    private int keywordId;
    private LinearLayoutManager layoutManager;
    private KeywordAdapter keywordAdapter;

    @Override
    protected int setupView() {
        return R.layout.activity_search_unit;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.black3), 0);
        ButterKnife.bind(this);

        mList = new ArrayList<>();
        mKeywordList = new ArrayList<>();
        setupKeywordList();

        mPresenter.getKeywordList();
        setupSwipeRefresh();
    }

    /**
     * 配置关键词标签
     */
    private void setupKeywordList() {
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        keywordAdapter = new KeywordAdapter(mKeywordList);
        recyclerView.setAdapter(keywordAdapter);

        keywordAdapter.setOnItemClickListener(new KeywordAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                keywordId = mKeywordList.get(position).id;
                mPresenter.getUnitListWithKeyword(true, keywordId, 0);
                requestCode = 1;
            }
        });

        quickIndexBar.setOnTouchLetterListener(new QuickIndexBar.OnTouchLetterListener() {
            @Override
            public void onTouchLetter(String letter) {
                //根据当前触摸的字母, 去集合中找那个item的首字母和letter一样
                //然后将对应的item放到屏幕顶端
                for (int i = 0; i < mKeywordList.size(); i++) {
                    String firstWord = mKeywordList.get(i).pinyin.charAt(0) + "";
                    if (letter.equals(firstWord)) {
                        layoutManager.scrollToPositionWithOffset(i, 0);
                        break;
                    }
                }
            }

            @Override
            public void onTouchLeave() {

            }
        });
    }

    /**
     * 配置列表的滑动监听
     */
    private void setupSwipeRefresh() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UnitLibraryActivityAdapter(mList);
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
    protected SearchUnitPresenter bindPresenter() {
        return new SearchUnitPresenter();
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if(swipeToLoadLayout.isShown()){
                    setViewState(false);
                } else {
                    finish();
                }
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
        mKeywordList.clear();
        mKeywordList.addAll(list);
        for (int i = 0; i < mKeywordList.size(); i++) {
            mKeywordList.get(i).pinyin = PinYinUtil.getPinYin(mKeywordList.get(i).name);
        }
        //对数据进行排序
        Collections.sort(mKeywordList);
        keywordAdapter.notifyDataSetChanged();
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
            rlKeyword.setVisibility(View.GONE);
        } else {
            swipeToLoadLayout.setVisibility(View.GONE);
            rlKeyword.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void reloadData() {
        mPresenter.getKeywordList();
        setViewState(false);
    }
}
