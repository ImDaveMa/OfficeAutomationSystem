package com.hengkai.officeautomationsystem.function.management_of_goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.GoodsDetailEntity;
import com.hengkai.officeautomationsystem.network.entity.GoodsInDetailEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 物品领用Activity
 */
public class GoodsDetailActivity extends BaseActivity<GoodsDetailPresenter> {
    public static final String EXTRA_KEY_ID = "EXTRA_KEY_ID";

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_type)
    TextView tvGoodsType;
    @BindView(R.id.tv_goods_specifications)
    TextView tvGoodsSpecifications;
    @BindView(R.id.tv_goods_brand)
    TextView tvGoodsBrand;
    @BindView(R.id.tv_goods_cost)
    TextView tvGoodsCost;
    @BindView(R.id.tv_goods_num)
    TextView tvGoodsNum;
    @BindView(R.id.tv_goods_total_price)
    TextView tvGoodsTotalPrice;
    @BindView(R.id.tv_goods_supplier)
    TextView tvGoodsSupplier;
    @BindView(R.id.tv_goods_create_user)
    TextView tvGoodsCreateUser;
    @BindView(R.id.tv_goods_create_time)
    TextView tvGoodsCreateTime;
    @BindView(R.id.tv_goods_summary)
    TextView tvGoodsSummary;
    @BindView(R.id.tv_state)
    TextView tvState;


    @Override
    protected int setupView() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        tvTitle.setText("物品详情");

        // 获取页面传参
        Intent intent = getIntent();
        int id = intent.getIntExtra(EXTRA_KEY_ID, 0);

        // 请求页面参数
        mPresenter.getGoodsDetail(id);
    }

    @Override
    protected GoodsDetailPresenter bindPresenter() {
        return new GoodsDetailPresenter();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.GOODS_DETAIL_ACTIVITY_GET_DETAIL);
        return tags;
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /**
     * 请求成功回调方法
     * 给页面赋值
     * @param bean
     */
    protected void getSuccess(GoodsDetailEntity.DetailBean bean) {
        if(bean != null) {
            DecimalFormat df = new DecimalFormat("#.00");

            tvGoodsName.setText(bean.getName());
            tvGoodsType.setText(bean.getGoodsTypeName());
            tvGoodsSpecifications.setText(bean.getSpecifications());
            tvGoodsBrand.setText(bean.getBrand());
            tvGoodsCost.setText(df.format(bean.getCost()) + "￥");
            tvGoodsNum.setText(String.format("%d%s", bean.getNum(), bean.getUnit()));
            tvGoodsTotalPrice.setText(df.format(bean.getTotal()) + "￥");
            tvGoodsSupplier.setText(bean.getSupplier());
            tvGoodsCreateUser.setText(bean.getCreateUserName());
            tvGoodsCreateTime.setText(DateFormatUtils.getFormatedNewsTime(bean.getCreateTime()));
            tvGoodsSummary.setText(bean.getRemark());
        } else {
            ToastUtil.showToast("物品不存在或者已经被删除");
            finish();
        }
    }

}
