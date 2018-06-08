package com.hengkai.officeautomationsystem.function.report.add;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.ReportContactsEntity;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/19.
 * 新增日报或者周报的时候, 选择发送给谁的界面
 */
public class GroupMemberActivity extends BaseActivity<GroupMemberPresenter> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    private ArrayList<ReportContactsEntity.DATEBean> mList;
    private GroupMemberAdapter adapter;

    private int count;
    private ArrayList<ReportContactsEntity.DATEBean> countList;

    @Override
    protected int setupView() {
        return R.layout.activity_group_member;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        tvTitle.setText("请选择");

        mList = new ArrayList<>();
        initRecyclerView();

        mPresenter.getGroupMemberList();
    }

    private void initRecyclerView() {
        ArrayList<ReportContactsEntity.DATEBean> statusList = getIntent().getParcelableArrayListExtra("status");
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GroupMemberAdapter(mList, statusList);
        swipeTarget.setAdapter(adapter);
        adapter.setOnNumberOfRecordListener(new GroupMemberAdapter.OnNumberOfRecordListener() {
            @Override
            public void onCheckNumber() {
                checkNumber();
            }
        });
        swipeTarget.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    /**
     * 计算总数和选择个数的方法
     */
    private void checkNumber() {
        count = 0;
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).checked) {
                count++;
            }
        }
        tvNumber.setText("已选择" + count + "/" + mList.size());
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.GROUP_MEMBER_ACTIVITY_GET_GROUP_MEMBER_LIST);
        return tags;
    }

    @Override
    protected GroupMemberPresenter bindPresenter() {
        return new GroupMemberPresenter();
    }

    public void getGroupMemberList(List<ReportContactsEntity.DATEBean> list) {
        mList.clear();
        mList.addAll(list);
        adapter.notifyDataSetChanged();

        ArrayList<ReportContactsEntity.DATEBean> statusList = getIntent().getParcelableArrayListExtra("status");
        if(statusList != null) {
            tvNumber.setText("已选择" + statusList.size() + "/" + mList.size());
        } else {
            tvNumber.setText("已选择0/" + mList.size());
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_commit:
                ArrayList<ReportContactsEntity.DATEBean> countList = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).checked) {
                        countList.add(mList.get(i));
                    }
                }
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra("data", countList);
                setResult(CommonFinal.COMMON_RESULT_CODE, intent);
                finish();
                break;
        }
    }

    @Override
    protected void reloadData() {
        mPresenter.getGroupMemberList();
    }
}
