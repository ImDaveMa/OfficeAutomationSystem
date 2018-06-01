package com.hengkai.officeautomationsystem.function.new_unit.search_keyword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.NewUnitKeywordEntity;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/6/1.
 * 搜索关键词页面
 */
public class SearchKeywordActivity extends BaseActivity<SearchKeywordPresenter> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;

    private List<NewUnitKeywordEntity.DATABean> mList;
    private SearchKeywordAdapter adapter;

    @Override
    protected int setupView() {
        return R.layout.activity_search_keyword;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        tvTitle.setText("关键词列表");
        mList = new ArrayList<>();

        setupRecyclerView();

        mPresenter.getKeywordList("");
    }

    private void setupRecyclerView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchKeywordAdapter(mList);
        swipeTarget.setAdapter(adapter);
        swipeTarget.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter.setOnItemClickListener(new SearchKeywordAdapter.OnItemClickListener() {
            @Override
            public void onClick(NewUnitKeywordEntity.DATABean bean) {
                Intent intent = new Intent();
                intent.putExtra("ID", bean.id);
                intent.putExtra("name", bean.name);
                setResult(CommonFinal.COMMON_RESULT_CODE, intent);
                finish();
            }
        });
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.NEW_UNIT_ACTIVITY_GET_KEYWORD_LIST);
        return tags;
    }

    @Override
    protected SearchKeywordPresenter bindPresenter() {
        return new SearchKeywordPresenter();
    }

    /**
     * @param list 获取单位关键字的数据
     */
    public void getKeywordList(List<NewUnitKeywordEntity.DATABean> list) {
        mList.clear();
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                String searchName = etSearch.getText().toString().trim();
                mPresenter.getKeywordList(searchName);
                break;
        }
    }
}
