package com.hengkai.officeautomationsystem.function.unit_library.detail;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.UnitLibraryDetailEntity;
import com.hengkai.officeautomationsystem.utils.SPUtils;
import com.jaeger.library.StatusBarUtil;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Harry on 2018/5/12.
 * 单位库详情(和我的单位列表共用当前页面)
 */
public class UnitLibraryDetailActivity extends BaseActivity<UnitLibraryDetailPresenter> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_website)
    TextView tvWebsite;
    @BindView(R.id.tv_description)
    TextView tvDescription;

    @Override
    protected int setupView() {
        return R.layout.activity_unit_library_detail;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        tvTitle.setText("单位详情");

        int ID = getIntent().getIntExtra("ID", 0);
        showDialog();
        mPresenter.getUnitDetail(ID);
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.UNIT_LIBRARY_DETAIL_ACTIVITY_GET_UNIT_DETAIL);
        return tags;
    }

    @Override
    protected UnitLibraryDetailPresenter bindPresenter() {
        return new UnitLibraryDetailPresenter();
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
     * @param bean 从服务器上获取实体类数据
     */
    public void setUnitDetail(UnitLibraryDetailEntity.DATABean bean) {
        tvName.setText(bean.name);
        tvType.setText(bean.type);
        tvAddress.setText(bean.address);
        tvNumber.setText(bean.creditCode);
        tvWebsite.setText(bean.website);
        tvDescription.setText(bean.text);
    }

}
