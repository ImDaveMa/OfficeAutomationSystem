package com.hengkai.officeautomationsystem.function.contacts;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.view.docking_expandable_list_view.adapter.DockingExpandableListViewAdapter;
import com.hengkai.officeautomationsystem.view.docking_expandable_list_view.controller.IDockingHeaderUpdateListener;
import com.hengkai.officeautomationsystem.view.docking_expandable_list_view.view.DockingExpandableListView;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/4/26.
 * 联系人列表
 */
public class ContactsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private String[] groupNames = {"技术部", "销售部", "孵化器", "行政部"};

    @Override
    protected int setupView() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
        ButterKnife.bind(this);

        initTitle();
        initExpandableListView();
    }

    /**
     * 初始化标题栏
     */
    private void initTitle() {
        tvTitle.setText("通讯录");
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        return null;
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    /**
     * 初始化列表
     */
    private void initExpandableListView() {
        ContactsDockingAdapterDataSource listData = prepareData();
        DockingExpandableListView listView = findViewById(R.id.docking_list_view);
        listView.setGroupIndicator(null);
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        listView.setAdapter(new DockingExpandableListViewAdapter(this, listView, listData));
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                } else {
                    parent.expandGroup(groupPosition);
                }
                return true;
            }
        });

        View headerView = getLayoutInflater().inflate(R.layout.group_view_item, listView, false);
        listView.setDockingHeader(headerView, new IDockingHeaderUpdateListener() {
            @Override
            public void onUpdate(View headerView, int groupPosition, boolean expanded) {
                TextView titleView = headerView.findViewById(R.id.group_view_title);
                titleView.setText(groupNames[groupPosition]);
            }
        });
    }

    /**
     * @return 准备列表的数据
     */
    private ContactsDockingAdapterDataSource prepareData() {
        ContactsDockingAdapterDataSource listData = new ContactsDockingAdapterDataSource(this);
        listData.addGroup("技术部")
                .addChild("技术部-孙红1")
                .addChild("技术部-孙红2")
                .addChild("技术部-孙红3")
                .addChild("技术部-孙红4")
                .addChild("技术部-孙红5")

                .addGroup("销售部")
                .addChild("销售部-孙红1")
                .addChild("销售部-孙红2")
                .addChild("销售部-孙红3")
                .addChild("销售部-孙红4")
                .addChild("销售部-孙红5")

                .addGroup("孵化器")
                .addChild("孵化器-孙红1")
                .addChild("孵化器-孙红2")
                .addChild("孵化器-孙红3")
                .addChild("孵化器-孙红4")
                .addChild("孵化器-孙红5")

                .addGroup("行政部")
                .addChild("行政部-孙红1")
                .addChild("行政部-孙红2")
                .addChild("行政部-孙红3")
                .addChild("行政部-孙红4")
                .addChild("行政部-孙红5");
        return listData;

    }

    @OnClick({R.id.iv_back, R.id.tv_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_title:
                break;
        }
    }
}
