package com.hengkai.officeautomationsystem.function.contacts;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.donkingliang.groupedadapter.widget.StickyHeaderLayout;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.ContactsEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;
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
 * 通讯录
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
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.sticky_layout)
    StickyHeaderLayout stickyLayout;
    /**
     * 需要拨打电话的电话号码
     */
    private String phoneNum;

    private List<ContactsEntity.DIRECTORIESBean> mList;
    private ContactsAdapter adapter;

    @Override
    protected int setupView() {
        return R.layout.activity_contacts;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        mList = new ArrayList<>();

        initTitle();
        setupRecyclerView();

        mPresenter.getContactsList();
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

    private void setupRecyclerView() {
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactsAdapter(this, mList);
        rvList.setAdapter(adapter);
        adapter.setOnCallPhoneClickListener(new ContactsAdapter.OnCallPhoneClickListener() {
            @Override
            public void callPhone(String phone) {
                easyPermission(phone);
            }
        });

        adapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition, int childPosition) {
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
                ContactsEntity.DIRECTORIESBean.DepartmentUserListBean bean = mList.get(groupPosition).departmentUserList.get(childPosition);

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
            }
        });

        adapter.setOnHeaderClickListener(new GroupedRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder, int groupPosition) {
                ContactsAdapter contactsAdapter = (ContactsAdapter) adapter;
                if (contactsAdapter.isExpand(groupPosition)) {
                    contactsAdapter.collapseGroup(groupPosition);
                } else {
                    contactsAdapter.expandGroup(groupPosition);
                }
            }
        });

        //是否吸顶，默认为true。
//        stickyLayout.setSticky(true);
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

    public void prepareData(List<ContactsEntity.DIRECTORIESBean> list) {
        mList.clear();
        mList.addAll(list);
        adapter.changeDataSet();
    }
}
