package com.hengkai.officeautomationsystem.function.contacts;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.function.login.LoginActivity;
import com.hengkai.officeautomationsystem.network.entity.ContactsEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;
import com.hengkai.officeautomationsystem.utils.PicassoCircleTransform;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.WindowUtil;
import com.hengkai.officeautomationsystem.view.docking_expandable_list_view.adapter.IDockingAdapterDataSource;
import com.squareup.picasso.Picasso;
import com.unistrong.yang.zb_permission.ZbPermission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Harry on 2018/4/27.
 */
public class ContactsDockingAdapterDataSource implements IDockingAdapterDataSource {

    private Activity mContext;

    //标题栏数据源
    private HashMap<Integer, String> mGroups = new HashMap<>();
    //子项的数据源
    private SparseArray<List<ContactsEntity.DIRECTORIESBean.DepartmentUserListBean>> mGroupData = new SparseArray<>();
    private int mCurrentGroup = -1;

    public ContactsDockingAdapterDataSource(Activity context) {
        this.mContext = context;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        if (mGroupData.get(groupPosition) != null) {
            return mGroupData.get(groupPosition).size();
        }

        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        if (mGroupData.get(groupPosition) != null) {
            return mGroupData.get(groupPosition);
        }

        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (mGroupData.get(groupPosition) != null) {
            List<ContactsEntity.DIRECTORIESBean.DepartmentUserListBean> group = mGroupData.get(groupPosition);
            if (childPosition >= 0 && childPosition < group.size()) {
                return group.get(childPosition);
            }
        }

        return null;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (groupPosition < 0 || !mGroups.containsKey(groupPosition)) {
            return null;
        }
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.group_view_item, parent, false);
        }
        TextView titleView = convertView.findViewById(R.id.group_view_title);
        titleView.setText(mGroups.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        List<ContactsEntity.DIRECTORIESBean.DepartmentUserListBean> children = mGroupData.get(groupPosition);
        if (children == null || childPosition < 0 || childPosition > children.size()) {
            return null;
        }
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.child_view_item, parent, false);
        }

        setupChildData(childPosition, convertView, children);

        return convertView;
    }

    private void setupChildData(int childPosition, View convertView, List<ContactsEntity.DIRECTORIESBean.DepartmentUserListBean> children) {
        final ContactsEntity.DIRECTORIESBean.DepartmentUserListBean bean = children.get(childPosition);
        ImageView ivContactHeader = convertView.findViewById(R.id.iv_contact_header);
        ImageView ivCallPhone = convertView.findViewById(R.id.iv_call_phone);
        TextView tvContactName = convertView.findViewById(R.id.tv_contact_name);
        TextView tvContactPosition = convertView.findViewById(R.id.tv_contact_position);
        TextView tvPhone = convertView.findViewById(R.id.tv_phone);
        TextView tvEmail = convertView.findViewById(R.id.tv_email);
        TextView tvLeaveTime = convertView.findViewById(R.id.tv_leave_time);

        tvContactName.setText(bean.name);
        tvContactPosition.setText(bean.position);
        tvPhone.setText(bean.phone);
        tvEmail.setText(bean.email);

        if (bean.endTime != 0 && bean.startTime != 0) {
            long currentTime = System.currentTimeMillis();
            String startTime = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_5, bean.startTime);
            String endTime = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_5, bean.endTime);
            if (bean.startTime <= currentTime && currentTime <= bean.endTime) {
                if (bean.attendanceType == 1) {
                    //请假中
                    tvLeaveTime.setText(String.format("请假中(%s~%s)", startTime, endTime));
                } else if (bean.attendanceType == 2) {
                    //外出中
                    tvLeaveTime.setText(String.format("外出中(%s~%s)", startTime, endTime));
                } else {
                    //不显示
                    tvLeaveTime.setVisibility(View.GONE);
                }
            }
        }
        if (!TextUtils.isEmpty(bean.iconLink)) {
            Picasso.with(mContext)
                    .load(bean.iconLink)
                    .error(R.drawable.ic_user64)
                    .transform(new PicassoCircleTransform())
                    .resize(WindowUtil.dp2px(40, mContext), WindowUtil.dp2px(40, mContext))
                    .centerCrop()
                    .into(ivContactHeader);
        } else {
            ivContactHeader.setImageResource(R.drawable.ic_user64);
        }

        ivCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone(bean.phone);
            }
        });
    }

    // Helper method to add group
    public ContactsDockingAdapterDataSource addGroup(String group) {
        if (!mGroups.containsValue(group)) {
            mCurrentGroup++;
            mGroups.put(mCurrentGroup, group);
            mGroupData.put(mCurrentGroup, new ArrayList<ContactsEntity.DIRECTORIESBean.DepartmentUserListBean>());
        }

        return this;
    }

    // Helper method to add child into one group
    public ContactsDockingAdapterDataSource addChild(ContactsEntity.DIRECTORIESBean.DepartmentUserListBean child) {
        if (mGroupData.get(mCurrentGroup) != null) {
            mGroupData.get(mCurrentGroup).add(child);
        }

        return this;
    }

    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        //检测并且注册权限
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        mContext.startActivity(intent);
    }

    private void requestPermission() {
        ZbPermission.with(mContext)
                .addRequestCode(1002)
                .permissions(Manifest.permission.CALL_PHONE)
                .request(new ZbPermission.ZbPermissionCallback() {
                    @Override
                    public void permissionSuccess(int requestCode) {
                        ToastUtil.showToast("权限申请成功");
                    }

                    @Override
                    public void permissionFail(int requestCode) {
                        ToastUtil.showToast("权限申请失败, 有可能直接拨打电话");
                    }
                });
    }
}
