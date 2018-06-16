package com.hengkai.officeautomationsystem.function.contacts_library.detail;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.visit_record.detail_x.VisitRecordDetailActivity;
import com.hengkai.officeautomationsystem.network.entity.ContactsLibraryDetailEntity;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/12.
 * 联系人库 - 联系人详情
 */
public class ContactsLibraryDetailActivity extends BaseActivity<ContactsLibraryDetailPresenter> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_operation)
    TextView tvOperation;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.tv_department)
    TextView tvDepartment;
    @BindView(R.id.tv_position)
    TextView tvPosition;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_mail)
    TextView tvMail;
    @BindView(R.id.tv_wei_xin)
    TextView tvWeiXin;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_hobby)
    TextView tvHobby;
    @BindView(R.id.tv_car_number)
    TextView tvCarNumber;

    private ContactsLibraryDetailEntity.DATABean mBean;

    @Override
    protected int setupView() {
        return R.layout.activity_contacts_library_detail;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        tvTitle.setText("联系人详情");
        tvOperation.setText("拜访跟进");
        tvOperation.setVisibility(View.VISIBLE);

        int ID = getIntent().getIntExtra("ID", 0);
        showDialog();
        mPresenter.getContactsDetail(ID);
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.CONTACTS_LIBRARY_ACTIVITY_GET_CONTACTS_LIST);
        return tags;
    }

    @Override
    protected ContactsLibraryDetailPresenter bindPresenter() {
        return new ContactsLibraryDetailPresenter();
    }

    public void setContactsDetail(ContactsLibraryDetailEntity.DATABean bean) {
        this.mBean = bean;

        tvName.setText(bean.name);
        tvUnit.setText(bean.companyName);
        tvDepartment.setText(bean.department);
        tvPosition.setText(bean.rank);
        tvPhone.setText(bean.phone);
        tvTel.setText(bean.landline);
        tvMail.setText(bean.mailbox);
        tvWeiXin.setText(bean.wechat);
        if (bean.sex) {
            tvSex.setText("男");
        } else {
            tvSex.setText("女");
        }
        tvBirthday.setText(bean.birthday);
        tvHobby.setText(bean.hobby);
        tvCarNumber.setText(bean.licensePlate);
    }

    @OnClick({R.id.iv_back,R.id.tv_operation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_operation:
                Intent intent = new Intent(this, VisitRecordDetailActivity.class);
                intent.putExtra("type", "add");
                if(mBean != null){
                    intent.putExtra("companyId", mBean.companyId);
                    intent.putExtra("companyName", mBean.companyName);
                    intent.putExtra("userId", mBean.id);
                    intent.putExtra("userName", mBean.name);
                    intent.putExtra("department", mBean.department);
                }
                startActivity(intent);
                break;
        }
    }
}
