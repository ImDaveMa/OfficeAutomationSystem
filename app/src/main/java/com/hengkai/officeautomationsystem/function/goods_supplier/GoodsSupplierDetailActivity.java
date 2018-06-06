package com.hengkai.officeautomationsystem.function.goods_supplier;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.GoodsSupplierDetailEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dave on 2018/6/6.
 */
public class GoodsSupplierDetailActivity extends BaseActivity<GoodsSupplierDetailPresenter> {

    public static final String EXTRA_KEY_ID = "EXTRA_KEY_ID";

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_supper_type)
    TextView tvSupperType;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_postcode)
    TextView tvPostcode;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_fax)
    TextView tvFax;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    @BindView(R.id.tv_wechat)
    TextView tvWechat;
    @BindView(R.id.tv_from)
    TextView tvFrom;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.tv_state)
    TextView tvState;
    @BindView(R.id.tv_cooperative)
    TextView tvCooperative;
    @BindView(R.id.tv_bussiness)
    TextView tvBussiness;

    @Override
    protected int setupView() {
        return R.layout.activity_goods_supplier_detail;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        tvTitle.setText("供应商详情");

        mPresenter.getGoodsSupplier(getIntent().getIntExtra(EXTRA_KEY_ID, 0));
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.GOODS_SUPPLIER_DETAIL_ACTIVITY_GET_DETAIL);
        return tags;
    }

    @Override
    protected GoodsSupplierDetailPresenter bindPresenter() {
        return new GoodsSupplierDetailPresenter();
    }


    public void bindView(GoodsSupplierDetailEntity.SupplierDetailBean bean) {
        tvName.setText(bean.name);
        tvSupperType.setText(bean.supperType);
        tvCity.setText(bean.city);
        tvAddress.setText(bean.address);
        tvPostcode.setText(bean.postalCode);
        tvContact.setText(bean.contacts);
        tvPhone.setText(bean.phone);
        tvFax.setText(bean.fax);
        tvEmail.setText(bean.mailbox);
        tvQq.setText(bean.qq);
        tvWechat.setText(bean.wechat);
        tvFrom.setText(bean.source);
        tvDescription.setText(bean.description);
        tvRemark.setText(bean.remark);
        tvState.setText(bean.state == 0 ? "否" : "是");
        tvCooperative.setText(String.format("%d个", bean.cooperative));
        tvBussiness.setText(String.format("%d个", bean.bussiness));
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

}
