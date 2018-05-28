package com.hengkai.officeautomationsystem.function.unit_library.detail;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.contacts_library.ContactsLibraryActivity;
import com.hengkai.officeautomationsystem.network.entity.UnitLibraryDetailEntity;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/12.
 * 单位库详情(和我的单位列表共用当前页面)
 */
public class UnitLibraryDetailActivity extends BaseActivity<UnitLibraryDetailPresenter> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_operation)
    TextView tvOperation;
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
        tvOperation.setText("联系人");
        tvOperation.setVisibility(View.VISIBLE);

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

    @OnClick({R.id.iv_back,R.id.tv_operation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_operation:
                int ID = getIntent().getIntExtra("ID", 0);
                Intent intent = new Intent(this, ContactsLibraryActivity.class);
                intent.putExtra(ContactsLibraryActivity.EXTRA_KEY_UNIT_ID, ID);
                startActivity(intent);
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
        if(TextUtils.isEmpty(bean.text)) {
            tvDescription.setText("");
        } else {
            tvDescription.setText(Html.fromHtml(bean.text));
        }
    }

}
