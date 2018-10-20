package com.hengkai.officeautomationsystem.function.visit_record.select_visit_person;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.contacts_library.add.AddContactActivity;
import com.hengkai.officeautomationsystem.function.new_unit.NewUnitActivity;
import com.hengkai.officeautomationsystem.function.visit_record.select_visit_project.SelectVisitProjectActivity;
import com.hengkai.officeautomationsystem.function.visit_record.select_visit_unit.SelectVisitUnitAdapter;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailGetVisitUnitEntity;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/30.
 */
public class SelectVisitPersonActivity extends BaseActivity<SelectVisitPersonPresenter> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_operation)
    TextView tvOperation;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    private List<VisitRecordDetailGetVisitUnitEntity.DATABean> mList;
    private SelectVisitPersonAdapter adapter;
    private int unitID;
    private String unitName;

    private final Intent resultIntent = new Intent(); // 返回结果

    /**
     * 是否为拜访跟进
     */
    private boolean openByVisit;

    @Override
    protected int setupView() {
        return R.layout.activity_select_visit_person;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        mList = new ArrayList<>();

        // 获取配置
        Intent intent = getIntent();
        unitID = intent.getIntExtra("unitID", 0);
        if(intent.hasExtra(CommonFinal.EXTRA_KEY_OPEN_BY_VISIT)) {
            openByVisit = intent.getBooleanExtra(CommonFinal.EXTRA_KEY_OPEN_BY_VISIT,false);
            unitName = intent.getStringExtra("unitName");
        }

        initTitleBar();
        setupRecyclerView();

        mPresenter.getVisitCustomerList(unitID);
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_GET_VISIT_UNIT_CUSTOMER_LIST);
        return tags;
    }

    @Override
    protected SelectVisitPersonPresenter bindPresenter() {
        return new SelectVisitPersonPresenter();
    }


    public void getVisitCustomerList(List<VisitRecordDetailGetVisitUnitEntity.DATABean> list) {
        mList.clear();
        mList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.iv_back, R.id.tv_operation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_operation:
                startActivityForResult(new Intent(this, AddContactActivity.class),
                        CommonFinal.SELECT_VISIT_PERSON_REQUEST_CODE);
                break;
        }
    }

    private void initTitleBar() {
        tvTitle.setText("拜访人");
        tvOperation.setVisibility(View.VISIBLE);
        tvOperation.setText("新增");
    }

    /**
     * 配置列表相关
     */
    private void setupRecyclerView() {
        swipeTarget.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SelectVisitPersonAdapter(mList);
        swipeTarget.setAdapter(adapter);
        swipeTarget.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter.setOnItemClickListener(new SelectVisitPersonAdapter.OnItemClickListener() {
            @Override
            public void onClick(final int ID, final String name, String department) {
                resultIntent.putExtra("ID", String.valueOf(ID));
                resultIntent.putExtra("name", name);
                resultIntent.putExtra("department", department);

                if(openByVisit){
                    new AlertDialog.Builder(SelectVisitPersonActivity.this)
                            .setTitle("提示")
                            .setMessage("是否继续选择项目？")
                            .setCancelable(false)
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                    // 选择项目
                                    Intent intent = new Intent(SelectVisitPersonActivity.this, SelectVisitProjectActivity.class);
                                    intent.putExtra("currentCustomerID", String.valueOf(ID));
                                    intent.putExtra("unitID", unitID);
                                    intent.putExtra("customerName", name);
                                    intent.putExtra("unitName", unitName);
                                    startActivityForResult(intent, CommonFinal.SELECT_VISIT_PROJECT_REQUEST_CODE);
                                }
                            })
                            .setNegativeButton("否", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    setResult(CommonFinal.SELECT_VISIT_PERSON_RESULT_CODE, resultIntent);
                                    finish();
                                }
                            }).show();
                } else {
                    setResult(CommonFinal.SELECT_VISIT_PERSON_RESULT_CODE, resultIntent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonFinal.SELECT_VISIT_PERSON_REQUEST_CODE && resultCode == RESULT_OK) {
            mPresenter.getVisitCustomerList(unitID);
        } else if (requestCode == CommonFinal.SELECT_VISIT_PROJECT_REQUEST_CODE && resultCode == CommonFinal.SELECT_VISIT_PROJECT_RESULT_CODE){ // 继续选择项目返回
            // 接着选择过的拜访人继续添加参数
            resultIntent.putExtra("projectID", data.getIntExtra("ID",0));
            resultIntent.putExtra("projectName", data.getStringExtra("name"));

            setResult(CommonFinal.SELECT_VISIT_PERSON_RESULT_CODE, resultIntent);
            finish();
        }
    }

    @Override
    protected void reloadData() {
        mPresenter.getVisitCustomerList(unitID);
    }
}
