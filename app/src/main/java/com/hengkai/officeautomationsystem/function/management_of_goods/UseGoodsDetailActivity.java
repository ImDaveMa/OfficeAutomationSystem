package com.hengkai.officeautomationsystem.function.management_of_goods;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.GoodsInDetailEntity;
import com.hengkai.officeautomationsystem.network.entity.GoodsOutDetailEntity;
import com.hengkai.officeautomationsystem.network.entity.GoodsParamsEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 物品领用Activity
 */
public class UseGoodsDetailActivity extends BaseActivity<UseGoodsDetailPresenter> {
    public static final String EXTRA_KEY_ID = "EXTRA_KEY_ID";

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_goods_use_for)
    TextView tvGoodsUseFor;
    @BindView(R.id.ll_goods_list)
    LinearLayout llGoodsList;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.ll_approval_container)
    LinearLayout llApprovalContainer;
    @BindView(R.id.ll_copy_container)
    LinearLayout llCopyContainer;

    @Override
    protected int setupView() {
        return R.layout.activity_use_goods_detail;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        tvTitle.setText("领用详情");

        // 获取页面传参
        Intent intent = getIntent();
        int id = intent.getIntExtra(EXTRA_KEY_ID,0);

        // 请求页面参数
        mPresenter.getUseGoodsDetail(id);
    }

    @Override
    protected UseGoodsDetailPresenter bindPresenter() {
        return new UseGoodsDetailPresenter();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.USE_GOODS_DETAIL_ACTIVITY_GET_DETAIL);
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

    protected void getSuccess(GoodsOutDetailEntity.OutStorageBean bean){
        // 物品
        List<GoodsOutDetailEntity.OutStorageBean.OutAttachBean> oaBeans = bean.getOutAttach();
        if(oaBeans != null) {
            for (int i = 0; i < oaBeans.size(); i++) {
                GoodsOutDetailEntity.OutStorageBean.OutAttachBean oaBean = oaBeans.get(i);
                final View view = LayoutInflater.from(this).inflate(R.layout.item_goods_detail_item, null);

                // 物品名称
                TextView tvGoodsName = view.findViewById(R.id.tv_goods_name);
                tvGoodsName.setText(oaBean.getGoodsName());

                // 物品数量
                TextView tvGoodsNum = view.findViewById(R.id.tv_goods_num);
                tvGoodsNum.setText(String.format("%d%s", oaBean.getNum(), oaBean.getUnitName()));

                llGoodsList.addView(view);
            }
            //充值索引
            setGoodsIndex();
        }

        // 绑定审核人和抄送人
        List<GoodsOutDetailEntity.OutStorageBean.ApsBean> apsBeans = bean.getAps();
        if (apsBeans != null) {
            for (int i = 0; i < apsBeans.size(); i++) {
                GoodsOutDetailEntity.OutStorageBean.ApsBean apsBean = apsBeans.get(i);
                if (apsBean != null) {
                    View view = LayoutInflater.from(this).inflate(R.layout.item_person, null);
                    ImageView ivHeader = view.findViewById(R.id.iv_header);
                    // TODO: 2018/5/14 没有添加头像信息

                    TextView tvName = view.findViewById(R.id.tv_name);
                    tvName.setText(apsBean.getUserName());
                    // 审批人
                    if (apsBean.getType() == 1) {
                        llApprovalContainer.addView(view);
                    } else if (apsBean.getType() == 2) { // 抄送人
                        llCopyContainer.addView(view);
                    }
                }
            }
        }

        // 绑定领用用途
        tvGoodsUseFor.setText(bean.getPurpose());
        // 绑定项目信息
        tvProject.setText(bean.getProductName());
    }

    /**
     * 排序物品
     */
    private void setGoodsIndex() {
        for (int i = 0; i < llGoodsList.getChildCount(); i++) {
            View parentView = llGoodsList.getChildAt(i);
            parentView.setTag(i);
            TextView tvIndex = parentView.findViewById(R.id.tv_goods_index);
            tvIndex.setText(String.format("物品明细(%d)", i + 1));
        }
    }


}
