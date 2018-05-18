package com.hengkai.officeautomationsystem.function.project_library.detail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.ProjectLibraryDetailEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/17.
 */
public class ProjectLibraryDetailActivity extends BaseActivity<ProjectLibraryDetailPresenter> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_person)
    TextView tvPerson;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_person_in_charge)
    TextView tvPersonInCharge;
    @BindView(R.id.tv_reception_time)
    TextView tvReceptionTime;
    @BindView(R.id.tv_deal_money)
    TextView tvDealMoney;
    @BindView(R.id.tv_belonged_to_unit)
    TextView tvBelongedToUnit;
    @BindView(R.id.tv_head_of_party_A)
    TextView tvHeadOfPartyA;
    @BindView(R.id.tv_team_leader)
    TextView tvTeamLeader;
    @BindView(R.id.tv_team_member)
    TextView tvTeamMember;

    @Override
    protected int setupView() {
        return R.layout.activity_project_library_detail;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        tvTitle.setText("项目详情");

        mPresenter.getProjectDetail(getIntent().getIntExtra("projectID", 0));
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.PROJECT_LIBRARY_DETAIL_ACTIVITY_GET_PROJECT_DETAIL);
        return tags;
    }

    @Override
    protected ProjectLibraryDetailPresenter bindPresenter() {
        return new ProjectLibraryDetailPresenter();
    }


    public void getProjectDetail(ProjectLibraryDetailEntity.DATEBean bean) {
        tvName.setText(bean.name);
        tvPerson.setText(bean.CreateUserName);
        String createTime = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_1, bean.createTime);
        tvCreateTime.setText(createTime);
        tvDescription.setText(bean.projectSummarize);
        tvType.setText(bean.typeName);
        tvPersonInCharge.setText(bean.principalUserName);
        String receptionTime = bean.qualifiedTime;
        if (!TextUtils.isEmpty(receptionTime)) {
            receptionTime = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_1, Long.valueOf(bean.qualifiedTime));
        } else {
            receptionTime = "暂无";
        }
        tvReceptionTime.setText(receptionTime);
        tvDealMoney.setText(bean.dealMoney);
        tvBelongedToUnit.setText(bean.pojectDw);
        tvHeadOfPartyA.setText(bean.pojectJf);
        tvTeamLeader.setText(bean.pojectXzZ);
        tvTeamMember.setText(bean.pojectXz);
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
