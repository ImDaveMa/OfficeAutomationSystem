package com.hengkai.officeautomationsystem.function.contacts;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.donkingliang.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.network.entity.ContactsEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;
import com.hengkai.officeautomationsystem.utils.PicassoCircleTransform;
import com.hengkai.officeautomationsystem.utils.WindowUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Harry on 2018/5/29.
 */
public class ContactsAdapter extends GroupedRecyclerViewAdapter {

    private List<ContactsEntity.DIRECTORIESBean> groupList;

    public ContactsAdapter(Context context, List<ContactsEntity.DIRECTORIESBean> list) {
        super(context);
        groupList = list;
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //如果当前组收起，就直接返回0，否则才返回子项数。这是实现列表展开和收起的关键。
        if (!isExpand(groupPosition)) {
            return 0;
        }
        return groupList.get(groupPosition).departmentUserList.size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.group_view_item;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.child_view_item;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
        holder.setText(R.id.group_view_title, groupList.get(groupPosition).departmentName);
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        final ContactsEntity.DIRECTORIESBean.DepartmentUserListBean bean = groupList.get(groupPosition).departmentUserList.get(childPosition);
        holder.setText(R.id.tv_contact_name, bean.name)
                .setText(R.id.tv_contact_position, bean.position)
                .setText(R.id.tv_phone, "电话: " + bean.phone);
        if (TextUtils.isEmpty(bean.email)) {
            holder.setText(R.id.tv_email, "暂无邮箱");
        } else {
            holder.setText(R.id.tv_email, "邮箱: " + bean.email);
        }

        TextView tvLeaveTime = holder.get(R.id.tv_leave_time);
        ImageView ivContactHeader = holder.get(R.id.iv_contact_header);
        ImageView ivCallPhone = holder.get(R.id.iv_call_phone);

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
                }
            }
        } else {
            //不显示
            tvLeaveTime.setVisibility(View.GONE);
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
                if (mListener != null) {
                    mListener.callPhone(bean.phone);
                }
            }
        });
    }

    public interface OnCallPhoneClickListener {
        void callPhone(String phone);
    }

    private OnCallPhoneClickListener mListener;

    public void setOnCallPhoneClickListener(OnCallPhoneClickListener listener) {
        this.mListener = listener;
    }

    /**
     * 判断当前组是否展开
     *
     * @param groupPosition
     * @return
     */
    public boolean isExpand(int groupPosition) {
        ContactsEntity.DIRECTORIESBean bean = groupList.get(groupPosition);
        return bean.isExpand;
    }

    /**
     * 展开一个组
     *
     * @param groupPosition
     */
    public void expandGroup(int groupPosition) {
        expandGroup(groupPosition, false);
    }

    /**
     * 展开一个组
     *
     * @param groupPosition
     * @param animate
     */
    public void expandGroup(int groupPosition, boolean animate) {
        ContactsEntity.DIRECTORIESBean bean = groupList.get(groupPosition);
        bean.isExpand = true;
        if (animate) {
            insertChildren(groupPosition);
        } else {
            changeDataSet();
        }
    }

    /**
     * 收起一个组
     *
     * @param groupPosition
     */
    public void collapseGroup(int groupPosition) {
        collapseGroup(groupPosition, false);
    }

    /**
     * 收起一个组
     *
     * @param groupPosition
     * @param animate
     */
    public void collapseGroup(int groupPosition, boolean animate) {
        ContactsEntity.DIRECTORIESBean bean = groupList.get(groupPosition);
        bean.isExpand = false;
        if (animate) {
            removeChildren(groupPosition);
        } else {
            changeDataSet();
        }
    }
}
