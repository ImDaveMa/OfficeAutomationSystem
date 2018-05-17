package com.hengkai.officeautomationsystem.function.visit_record.comment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.CommentVisitEntity;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/16.
 * 拜访跟进的评论页面
 */
public class CommentVisitActivity extends BaseActivity<CommentVisitPresenter> {

    @BindView(R.id.tv_visit_type)
    TextView tvVisitType;
    @BindView(R.id.tv_visit_unit)
    TextView tvVisitUnit;
    @BindView(R.id.tv_visit_customer)
    TextView tvVisitCustomer;
    @BindView(R.id.tv_visit_department)
    TextView tvVisitDepartment;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_visit_summary)
    TextView tvVisitSummary;
    @BindView(R.id.tv_not_comment)
    TextView tvNotComment;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<CommentVisitEntity.DATEBean> mList;
    private CommentVisitAdapter adapter;
    private int currentID;

    @Override
    protected int setupView() {
        return R.layout.activity_comment_visit;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        mList = new ArrayList<>();
        setupRecyclerView();
        currentID = getIntent().getIntExtra("currentID", 0);
        mPresenter.getVisitRecordDetail(currentID);
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.COMMENT_VISIT_ACTIVITY_GET_VISIT_DETAIL);
        tags.add(NetworkTagFinal.COMMENT_VISIT_ACTIVITY_GET_COMMENT_LIST);
        return tags;
    }

    @Override
    protected CommentVisitPresenter bindPresenter() {
        return new CommentVisitPresenter();
    }

    @OnClick({R.id.iv_back, R.id.tv_comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_comment:
                //跳转到评论页面, 评论完成后返回并更新列表信息
                Intent intent = new Intent(this, GoToCommentActivity.class);
                intent.putExtra("currentID", currentID);
                startActivityForResult(intent, CommonFinal.COMMON_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CommonFinal.COMMON_RESULT_CODE && requestCode == CommonFinal.COMMON_REQUEST_CODE) {
            mPresenter.getCommentList(currentID);
        }
    }

    /**
     * 配置列表
     */
    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentVisitAdapter(mList);
        recyclerView.setAdapter(adapter);
    }

    /**
     * @param bean 从服务器上获取到页面的详情信息
     */
    public void getVisitRecordDetail(VisitRecordDetailEntity.DATABean bean) {
        //优先获取信息, 在获取评价
        mPresenter.getCommentList(currentID);

        switch (bean.type) {
            case "0":
                tvVisitType.setText("跟进");
                break;
            case "1":
                tvVisitType.setText("拜访");
                break;
            default:
                tvVisitType.setText("招待");
                break;
        }
        tvVisitUnit.setText(bean.companyName);
        tvVisitCustomer.setText(bean.contactsName);
        tvVisitDepartment.setText(bean.department);
        tvProject.setText(bean.projectName);
        tvVisitSummary.setText(bean.summary);

        tvVisitType.setTextColor(getResources().getColor(R.color.black1));
        tvVisitUnit.setTextColor(getResources().getColor(R.color.black1));
        tvVisitCustomer.setTextColor(getResources().getColor(R.color.black1));
        tvVisitDepartment.setTextColor(getResources().getColor(R.color.black1));
        tvProject.setTextColor(getResources().getColor(R.color.black1));
        tvVisitSummary.setTextColor(getResources().getColor(R.color.black1));
        tvStartTime.setTextColor(getResources().getColor(R.color.black1));
        tvEndTime.setTextColor(getResources().getColor(R.color.black1));

        String startTime = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_5, bean.startTime);
        tvStartTime.setText(startTime);
        String endTime = DateFormatUtils.getFormatedDateTime(DateFormatUtils.PATTERN_5, bean.endTime);
        tvEndTime.setText(endTime);
    }

    public void getCommentList(List<CommentVisitEntity.DATEBean> list) {
        if (list == null || list.size() == 0) {
            tvNotComment.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvNotComment.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mList.clear();
            mList.addAll(list);
            adapter.notifyDataSetChanged();
        }

    }
}
