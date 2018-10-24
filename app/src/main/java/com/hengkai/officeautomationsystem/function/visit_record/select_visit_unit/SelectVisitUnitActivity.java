package com.hengkai.officeautomationsystem.function.visit_record.select_visit_unit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.new_unit.NewUnitActivity;
import com.hengkai.officeautomationsystem.function.visit_record.select_visit_person.SelectVisitPersonActivity;
import com.hengkai.officeautomationsystem.function.visit_record.select_visit_project.SelectVisitProjectActivity;
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
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_load_more_footer)
    LoadMoreFooterView swipeLoadMoreFooter;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rl_keyword)
    RelativeLayout rlKeyword;
    @BindView(R.id.quick_index_bar)
    QuickIndexBar quickIndexBar;

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
    private LinearLayoutManager layoutManager;
    private KeywordAdapter keywordAdapter;

    /**
     * 是否为拜访跟进
     */
    private boolean openByVisit;

    private final Intent resultIntent = new Intent(); // 返回结果

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
        mKeywordList = new ArrayList<>();

        // 获取配置
        Intent intent = getIntent();
        if(intent.hasExtra(CommonFinal.EXTRA_KEY_OPEN_BY_VISIT)) {
            openByVisit = intent.getBooleanExtra(CommonFinal.EXTRA_KEY_OPEN_BY_VISIT,false);
        }

        initTitleBar();
        setupRecyclerView();
        setupKeywordList();

        mPresenter.getKeywordList();
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

    private void initTitleBar() {
        tvTitle.setText("选择单位");
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
                intent.putExtra(CommonFinal.EXTRA_KEY_OPEN_BY_VISIT, openByVisit);
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
            public void onClick(final int ID, final String name) {
                resultIntent.putExtra("ID", ID);
                resultIntent.putExtra("name", name);

                if(openByVisit){
                    new AlertDialog.Builder(SelectVisitUnitActivity.this)
                            .setTitle("提示")
                            .setMessage("是否继续选择拜访人？")
                            .setCancelable(false)
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                    // 选择拜访人
                                    Intent intent = new Intent(SelectVisitUnitActivity.this, SelectVisitPersonActivity.class);
                                    intent.putExtra(CommonFinal.EXTRA_KEY_OPEN_BY_VISIT, openByVisit);
                                    intent.putExtra("unitID", ID);
                                    intent.putExtra("unitName", name);
                                    startActivityForResult(intent, CommonFinal.SELECT_VISIT_PERSON_REQUEST_CODE);
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    setResult(CommonFinal.SELECT_VISIT_UNIT_RESULT_CODE, resultIntent);
                                    finish();
                                }
                            }).show();
                } else {
                    setResult(CommonFinal.SELECT_VISIT_UNIT_RESULT_CODE, resultIntent);
                    finish();
                }
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
            rlKeyword.setVisibility(View.GONE);
        } else {
            swipeToLoadLayout.setVisibility(View.GONE);
            rlKeyword.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonFinal.SELECT_VISIT_PERSON_REQUEST_CODE && resultCode == CommonFinal.SELECT_VISIT_PERSON_RESULT_CODE) {
//            if (swipeToLoadLayout.getVisibility() == View.VISIBLE) {
//                swipeToLoadLayout.setRefreshing(true);
//            }

            if(openByVisit) {
                resultIntent.putExtra("personID", data.getIntExtra("personID", 0));
                resultIntent.putExtra("personName", data.getStringExtra("personName"));
                resultIntent.putExtra("personDepartment", data.getStringExtra("personDepartment"));

                // 项目信息
                if(data.hasExtra("projectID")){
                    resultIntent.putExtra("projectID", data.getIntExtra("projectID",0));
                    resultIntent.putExtra("projectName", data.getStringExtra("projectName"));
                }
            }

            // 单位信息
            setResult(CommonFinal.SELECT_VISIT_UNIT_RESULT_CODE, resultIntent);
            finish();
        } else
            // 添加时
        if (requestCode == CommonFinal.COMMON_REQUEST_CODE && resultCode == CommonFinal.SELECT_VISIT_UNIT_RESULT_CODE) {
            if(openByVisit) {
                resultIntent.putExtra("personID", data.getIntExtra("personID", 0));
                resultIntent.putExtra("personName", data.getStringExtra("personName"));
                resultIntent.putExtra("personDepartment", data.getStringExtra("personDepartment"));

                // 项目信息
                if(data.hasExtra("projectID")){
                    resultIntent.putExtra("projectID", data.getIntExtra("projectID",0));
                    resultIntent.putExtra("projectName", data.getStringExtra("projectName"));
                }
            }

            // 单位信息
            resultIntent.putExtra("ID", data.getIntExtra("unitID",0));
            resultIntent.putExtra("name", data.getStringExtra("unitName"));
            setResult(CommonFinal.SELECT_VISIT_UNIT_RESULT_CODE, resultIntent);
            finish();
        }
    }

    @Override
    protected void reloadData() {
        mPresenter.getKeywordList();
        setViewState(false);
    }
}
