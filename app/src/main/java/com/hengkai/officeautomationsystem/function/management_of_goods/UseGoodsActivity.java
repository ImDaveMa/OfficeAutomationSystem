package com.hengkai.officeautomationsystem.function.management_of_goods;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 物品领用Activity
 */
public class UseGoodsActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected int setupView() {
        return R.layout.activity_use_goods;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        tvTitle.setText("领用申请");
    }

    @Override
    protected BasePresenter bindPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        return null;
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
