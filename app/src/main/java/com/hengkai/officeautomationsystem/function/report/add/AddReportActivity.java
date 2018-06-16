package com.hengkai.officeautomationsystem.function.report.add;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.ReportContactsEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.WindowUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/18.
 * 新增日报或者周报
 */
public class AddReportActivity extends BaseActivity<AddReportPresenter> {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_today)
    TextView tvToday;
    @BindView(R.id.et_today)
    EditText etToday;
    @BindView(R.id.tv_tomorrow)
    TextView tvTomorrow;
    @BindView(R.id.tv_send_someone)
    TextView tvSendSomeone;
    @BindView(R.id.et_tomorrow)
    EditText etTomorrow;
    @BindView(R.id.et_need_help)
    EditText etNeedHelp;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private int type;
    private AddReportAdapter adapter;
    /**
     * 保存点击了哪一些要发送的人的状态, 用来显示在当前页面里面和传递到下一个页面来赋值状态
     */
    private ArrayList<ReportContactsEntity.DATEBean> statusList;

    @Override
    protected int setupView() {
        return R.layout.activity_add_report;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        type = getIntent().getIntExtra("type", -1);

        if (type == 0) {
            tvTitle.setText("新增日报");
            tvToday.setText("今日完成");
            tvTomorrow.setText("下步计划");
        } else {
            tvTitle.setText("新增周报");
            tvToday.setText("本周完成");
            tvTomorrow.setText("下周计划");
        }
        SpannableString spannableString = new SpannableString("通知给 (点击头像删除)");
        RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(1.0f);
        RelativeSizeSpan sizeSpan02 = new RelativeSizeSpan(0.8f);
        spannableString.setSpan(sizeSpan01, 0, 3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(sizeSpan02, 3, 12, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        tvSendSomeone.setText(spannableString);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5) {
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
        adapter = new AddReportAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AddReportAdapter.OnItemClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(AddReportActivity.this, GroupMemberActivity.class);
                intent.putParcelableArrayListExtra("status", statusList);
                startActivityForResult(intent, CommonFinal.COMMON_REQUEST_CODE);
            }

            @Override
            public void onHeaderClick(int position) {
                statusList.remove(position - 1);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.ADD_REPORT_ACTIVITY_ADD_REPORT);
        return tags;
    }

    @Override
    protected AddReportPresenter bindPresenter() {
        return new AddReportPresenter();
    }

    @OnClick({R.id.iv_back, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_commit:
                String ids = "";
                if (statusList != null) {
                    for (int i = 0; i < statusList.size(); i++) {
                        if (ids != "") {
                            ids += ",";
                        }
                        ids += statusList.get(i).userId;
                    }
                }
                String today = etToday.getText().toString().trim();
                String tomorrow = etTomorrow.getText().toString().trim();
                String needHelp = etNeedHelp.getText().toString().trim();
                if (type == 0) {    //日报
                    if (TextUtils.isEmpty(today) && TextUtils.isEmpty(tomorrow) && TextUtils.isEmpty(needHelp)) {
                        ToastUtil.showToast("请至少填写一项内容");
                        return;
                    } else if (TextUtils.isEmpty(ids)) {
                        ToastUtil.showToast("您还未选择通知人, 请选择");
                        return;
                    }
                } else {    //周报
                    if (TextUtils.isEmpty(today) && TextUtils.isEmpty(tomorrow) && TextUtils.isEmpty(needHelp)) {
                        ToastUtil.showToast("请至少填写一项内容");
                        return;
                    } else if (TextUtils.isEmpty(ids)) {
                        ToastUtil.showToast("您还未选择通知人, 请选择");
                        return;
                    }
                }

                Map<String, String> params = new HashMap<>();
                params.put("REMARK", ids);
                params.put("PRESENTCONTENT", today);
                params.put("NEXTCONTENT", tomorrow);
                params.put("DEMANDCONTENT", needHelp);
                params.put("TYPE", String.valueOf(type));

                showDialog();
                mPresenter.addReport(params);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CommonFinal.COMMON_RESULT_CODE && requestCode == CommonFinal.COMMON_REQUEST_CODE) {
            statusList = data.getParcelableArrayListExtra("data");

            adapter.addItems(statusList);
        }
    }
}
