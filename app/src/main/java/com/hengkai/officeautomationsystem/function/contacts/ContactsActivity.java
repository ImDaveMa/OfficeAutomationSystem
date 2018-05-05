package com.hengkai.officeautomationsystem.function.contacts;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.ContactsEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;
import com.hengkai.officeautomationsystem.view.docking_expandable_list_view.adapter.DockingExpandableListViewAdapter;
import com.hengkai.officeautomationsystem.view.docking_expandable_list_view.controller.IDockingHeaderUpdateListener;
import com.hengkai.officeautomationsystem.view.docking_expandable_list_view.view.DockingExpandableListView;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/4/26.
 * 联系人列表
 */
public class ContactsActivity extends BaseActivity<ContactsActivityPresenter> {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private List<String> groupNames;
    private DockingExpandableListViewAdapter adapter;
    private ContactsDockingAdapterDataSource listData;
    private DockingExpandableListView listView;


    @Override
    protected int setupView() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
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
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.CONTACTS_ACTIVITY_GET_CONTACTS_LIST);
        return tags;
    }

    @Override
    protected ContactsActivityPresenter bindPresenter() {
        return new ContactsActivityPresenter();
    }

    /**
     * 初始化列表
     */
    private void initExpandableListView() {
        listData = new ContactsDockingAdapterDataSource(this);
//        ContactsDockingAdapterDataSource listData = prepareData();
        listView = findViewById(R.id.docking_list_view);
        listView.setGroupIndicator(null);
        listView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        adapter = new DockingExpandableListViewAdapter(this, listView, listData);
        listView.setAdapter(adapter);
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
                titleView.setText(groupNames.get(groupPosition));
            }
        });

        //请求网络获取数据
        mPresenter.getContactsList();
    }

    /**
     * @return 准备列表的数据
     */
    public void prepareData(List<ContactsEntity.DIRECTORIESBean> list) {
        groupNames = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            listData.addGroup(list.get(i).departmentName);
            groupNames.add(list.get(i).departmentName);
            for (int j = 0; j < list.get(i).departmentUserList.size(); j++) {
                listData.addChild(list.get(i).departmentUserList.get(j));
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 设置子列表每一项的点击事件
     */
    public void initListChildClickListener(final List<ContactsEntity.DIRECTORIESBean> list) {
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ContactsActivity.this);
                View view = View.inflate(ContactsActivity.this, R.layout.dialog_contacts_detail, null);

                TextView tvName = view.findViewById(R.id.tv_name);
                TextView tvPosition = view.findViewById(R.id.tv_position);
                TextView tvPhone = view.findViewById(R.id.tv_phone);
                TextView tvQQ = view.findViewById(R.id.tv_qq);
                TextView tvWeiXin = view.findViewById(R.id.tv_wei_xin);
                TextView tvDing = view.findViewById(R.id.tv_ding);
                TextView tvMail = view.findViewById(R.id.tv_mail);
                TextView tvEntryTime = view.findViewById(R.id.tv_entry_time);
                TextView tvQuitTime = view.findViewById(R.id.tv_quit_time);
                ContactsEntity.DIRECTORIESBean.DepartmentUserListBean bean = list.get(groupPosition).departmentUserList.get(childPosition);

                tvName.setText(bean.name);
                tvPosition.setText(bean.position);
                tvPhone.setText(bean.phone);
                tvQQ.setText(bean.qqNumber);
                tvWeiXin.setText(bean.weixinNumber);
                tvDing.setText(bean.dingNumber);
                tvMail.setText(bean.email);

                if (bean.createTime == 0) {
                    tvEntryTime.setText("");
                } else {
                    String entryTime = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_1, bean.createTime);
                    tvEntryTime.setText(entryTime);
                }
                if (bean.correctionTime == 0) {
                    tvQuitTime.setText("");
                } else {
                    String quitTime = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_1, bean.correctionTime);
                    tvQuitTime.setText(quitTime);
                }

                builder.setView(view);
                builder.show();
                return true;
            }
        });
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
