package com.hengkai.officeautomationsystem.function.visit_record.comment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.CommentVisitEntity;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailEntity;
import com.hengkai.officeautomationsystem.utils.DateFormatUtils;
import com.hengkai.officeautomationsystem.utils.SPUtils;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/16.
 * 拜访跟进(提交后显示的评论页面)的评论页面
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
    @BindView(R.id.tv_start_address)
    TextView tvStartAddress;
    @BindView(R.id.tv_end_address)
    TextView tvEndAddress;
    @BindView(R.id.tv_state_of_approval)
    TextView tvStateOfApproval;
    @BindView(R.id.btn_approval)
    Button btnApproval;

    private List<CommentVisitEntity.DATEBean> mList;
    private CommentVisitAdapter adapter;
    private int currentID;
    /**
     * 审批ID
     */
    private int examineId;

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
        tags.add(NetworkTagFinal.COMMENT_VISIT_ACTIVITY_APPROVAL);
        return tags;
    }

    @Override
    protected CommentVisitPresenter bindPresenter() {
        return new CommentVisitPresenter();
    }

    @OnClick({R.id.iv_back, R.id.tv_comment, R.id.btn_approval})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (!TextUtils.isEmpty(tvStateOfApproval.getText().toString().trim())) {
                    setResult(CommonFinal.COMMON_RESULT_CODE);
                }
                finish();
                break;
            case R.id.tv_comment:
                //跳转到评论页面, 评论完成后返回并更新列表信息
                Intent intent = new Intent(this, GoToCommentActivity.class);
                intent.putExtra("currentID", currentID);
                startActivityForResult(intent, CommonFinal.COMMON_REQUEST_CODE);
                break;
            case R.id.btn_approval:
                if (examineId > 0) {
                    String btnName = btnApproval.getText().toString().trim();
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    final AlertDialog dialog = builder.create();
                    if (btnName.equals("撤销")) {// TODO: 2018/7/24 撤销功能暂时删除掉了, 这里暂时先不删除
                        builder.setCancelable(false);
                        builder.setMessage("确认撤销吗?");
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mPresenter.approval(examineId, 3, "");
                                dialog.dismiss();
                            }
                        }).show();
                    } else if (btnName.equals("审批")) {
                        builder.setCancelable(true);
                        View dialogView = View.inflate(this, R.layout.dialog_visit_approval, null);
                        final EditText etReason = dialogView.findViewById(R.id.et_reason);
                        Button btnAgree = dialogView.findViewById(R.id.btn_agree);
                        Button btnRefuse = dialogView.findViewById(R.id.btn_refuse);
                        btnAgree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String reason = etReason.getText().toString().trim();
                                mPresenter.approval(examineId, 1, reason);
                                dialog.dismiss();
                            }
                        });
                        btnRefuse.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String reason = etReason.getText().toString().trim();
                                if (TextUtils.isEmpty(reason)) {
                                    ToastUtil.showToast("请填写拒绝理由");
                                    return;
                                } else {
                                    mPresenter.approval(examineId, 2, reason);
                                    dialog.dismiss();
                                }
                            }
                        });
                        dialog.setView(dialogView);
                        dialog.show();
                    }
                }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!TextUtils.isEmpty(tvStateOfApproval.getText().toString().trim())) {
            setResult(CommonFinal.COMMON_RESULT_CODE);
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
     * @param bean   从服务器上获取到页面的详情信息
     * @param entity
     */
    public void getVisitRecordDetail(VisitRecordDetailEntity.DATABean bean, VisitRecordDetailEntity entity) {
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

        tvStartAddress.setText(bean.startAddress);
        tvEndAddress.setText(bean.endAddress);

        tvStateOfApproval.setText(entity.examineState);
        examineId = entity.examineId;

//        if (bean.userId == Integer.valueOf(SPUtils.getString(UserInfo.USER_ID.name(), ""))) {
//            btnApproval.setText("撤销");
//        } else {
//            btnApproval.setText("审批");
//        }
        if (entity.isOptionable == 1) {
            btnApproval.setVisibility(View.VISIBLE);
        } else {
            btnApproval.setVisibility(View.GONE);
        }

    }

    /**
     * @param approvalState 1同意2拒绝3撤销
     */
    public void setApprovalState(int approvalState) {
        btnApproval.setVisibility(View.GONE);
        switch (approvalState) {
            case 1:
                tvStateOfApproval.setText("同意");
                break;
            case 2:
                tvStateOfApproval.setText("拒绝");
                break;
            case 3:
                tvStateOfApproval.setText("撤销");
                break;
        }
        mPresenter.getCommentList(currentID);
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
