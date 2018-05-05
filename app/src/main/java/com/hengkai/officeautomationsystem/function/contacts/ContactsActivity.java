package com.hengkai.officeautomationsystem.function.contacts;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
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
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Harry on 2018/4/26.
 * 联系人列表
 */
public class ContactsActivity extends BaseActivity<ContactsActivityPresenter> implements EasyPermissions.PermissionCallbacks {

    /**
     * 拨打电话的请求码
     */
    private static final int CALL_PHONE_REQUEST_CODE = 1002;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private List<String> groupNames;
    private DockingExpandableListViewAdapter adapter;
    private ContactsDockingAdapterDataSource listData;
    private DockingExpandableListView listView;
    /**
     * 需要拨打电话的电话号码
     */
    private String phoneNum;


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

    public void easyPermission(String phoneNum) {
        this.phoneNum = phoneNum;
        String[] permissionList = new String[]{Manifest.permission.CALL_PHONE};
        if (Build.VERSION.SDK_INT >= 23) {
            if (EasyPermissions.hasPermissions(this, permissionList)) {
                callPhone(phoneNum);
            } else {
                EasyPermissions.requestPermissions(this, "需要拨打电话的权限", CALL_PHONE_REQUEST_CODE, permissionList);
            }
        } else {
            callPhone(phoneNum);
        }
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        //检测并且注册权限
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
        }
        startActivity(intent);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //同意了授权
        callPhone(phoneNum);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //拒绝了授权
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            //拒绝授权后，从系统设置了授权后，返回APP进行相应的操作
            callPhone(phoneNum);
        }

    }
}
