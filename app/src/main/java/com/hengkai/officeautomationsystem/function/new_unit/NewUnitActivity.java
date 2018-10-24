package com.hengkai.officeautomationsystem.function.new_unit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.contacts_library.add.AddContactActivity;
import com.hengkai.officeautomationsystem.function.new_unit.search_keyword.SearchKeywordActivity;
import com.hengkai.officeautomationsystem.function.new_unit.search_keyword.SearchKeywordAdapter;
import com.hengkai.officeautomationsystem.function.visit_record.select_visit_person.SelectVisitPersonActivity;
import com.hengkai.officeautomationsystem.network.entity.NewUnitKeywordEntity;
import com.hengkai.officeautomationsystem.network.entity.NewUnitTypeEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Harry on 2018/5/11.
 * 项目 - 新增单位
 */
public class NewUnitActivity extends BaseActivity<NewUnitPresenter> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tit_name)
    TextInputEditText titName;
    @BindView(R.id.tv_unit_type)
    TextView tvUnitType;
    @BindView(R.id.iv_unit_type)
    ImageView ivUnitType;
    @BindView(R.id.et_keyword)
    EditText etKeyword;
    @BindView(R.id.tit_number)
    TextInputEditText titNumber;
    @BindView(R.id.tit_address)
    TextInputEditText titAddress;
    @BindView(R.id.tit_website)
    TextInputEditText titWebsite;
    @BindView(R.id.tit_description)
    TextInputEditText titDescription;
    /**
     * 单位类型ID
     */
    private int typeID;
    /**
     * 单位关键字ID
     */
    private int keywordID;
    /**
     * 当前添加的单位ID
     */
    private int currentID;
    /**
     * 是否为拜访跟进
     */
    private boolean openByVisit;

    private Intent resultIntent = new Intent();

    @Override
    protected int setupView() {
        return R.layout.activity_new_unit;
    }

    @Override
    protected void initView() {
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        ButterKnife.bind(this);

        tvTitle.setText("新增单位");

        // 获取配置
        Intent intent = getIntent();
        if(intent.hasExtra(CommonFinal.EXTRA_KEY_OPEN_BY_VISIT)) {
            openByVisit = intent.getBooleanExtra(CommonFinal.EXTRA_KEY_OPEN_BY_VISIT,false);
        }

        setEditTextListener();
    }

    private void setEditTextListener() {
        etKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keywordID = 0;
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.NEW_UNIT_ACTIVITY_COMMIT);
        tags.add(NetworkTagFinal.NEW_UNIT_ACTIVITY_GET_KEYWORD_LIST);
        tags.add(NetworkTagFinal.NEW_UNIT_ACTIVITY_GET_TYPE_LIST);
        return tags;
    }

    @Override
    protected NewUnitPresenter bindPresenter() {
        return new NewUnitPresenter();
    }

    @OnClick({R.id.iv_back, R.id.ll_unit_type, R.id.ll_keyword, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_unit_type:
                mPresenter.getTypeList();
                showDialog();
                break;
            case R.id.ll_keyword:
                Intent intent = new Intent(this, SearchKeywordActivity.class);
                startActivityForResult(intent, CommonFinal.COMMON_REQUEST_CODE);
                break;
            case R.id.btn_commit:
                commit();
                break;
        }
    }

    private void commit() {
        String unitType = tvUnitType.getText().toString().trim();
        String keyword = etKeyword.getText().toString().trim();
        String name = titName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast("填写单位名称");
            return;
        } else if (TextUtils.isEmpty(unitType) || unitType.equals("请选择")) {
            ToastUtil.showToast("请选择单位类型");
            return;
        } else if (TextUtils.isEmpty(keyword)) {
            ToastUtil.showToast("请选择单位关键字");
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("type", unitType);
        params.put("keywordId", String.valueOf(keywordID));
        params.put("keywordName", keyword);
        if (!TextUtils.isEmpty(titAddress.getText().toString().trim())) {
            params.put("address", titAddress.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(titDescription.getText().toString().trim())) {
            params.put("text", titDescription.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(titWebsite.getText().toString().trim())) {
            params.put("website", titWebsite.getText().toString().trim());
        }
        if (!TextUtils.isEmpty(titNumber.getText().toString().trim())) {
            params.put("creditCode", titNumber.getText().toString().trim());
        }
        mPresenter.commit(params);
        showDialog();
    }

    protected void commitResultSuccess(final int currentUnitID){
        currentID = currentUnitID;

        resultIntent.putExtra("unitID", currentUnitID);
        resultIntent.putExtra("unitName", titName.getText().toString().trim());

        if(openByVisit){
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("是否继续添加拜访人？")
                    .setCancelable(false)
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            // 打开新增联系人界面
                            Intent intent = new Intent(NewUnitActivity.this, AddContactActivity.class);
                            intent.putExtra("unitID", currentUnitID);
                            intent.putExtra("unitName", titName.getText().toString().trim());
                            intent.putExtra(CommonFinal.EXTRA_KEY_OPEN_BY_VISIT, true);
                            startActivityForResult(intent, CommonFinal.SELECT_VISIT_PERSON_REQUEST_CODE);
                        }
                    })
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            setResult(CommonFinal.SELECT_VISIT_UNIT_RESULT_CODE, resultIntent);
                            finish();
                        }
                    }).show();
        } else {
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }

    /**
     * 获取底部弹窗并且弹出
     */
    private BottomSheetDialog getBottomSheetDialog(RecyclerView.Adapter adapter) {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setCanceledOnTouchOutside(true);
        View view = View.inflate(this, R.layout.dialog_visit_record_detail, null);
        dialog.setContentView(view);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        return dialog;
    }

    /**
     * @param list 获取单位类型的数据
     */
    public void getTypeList(List<NewUnitTypeEntity.DATABean> list) {
        String unitType = tvUnitType.getText().toString().trim();
        TypeBottomDialogAdapter adapter = new TypeBottomDialogAdapter(list, unitType);
        final BottomSheetDialog dialog = getBottomSheetDialog(adapter);

        adapter.setOnItemClickListener(new TypeBottomDialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(NewUnitTypeEntity.DATABean bean) {
                tvUnitType.setText(bean.paramValue);
                ivUnitType.setVisibility(View.GONE);
                tvUnitType.setTextColor(getResources().getColor(R.color.black1));
                typeID = bean.id;
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonFinal.COMMON_REQUEST_CODE && resultCode == CommonFinal.COMMON_RESULT_CODE) {
            etKeyword.setText(data.getStringExtra("name"));
            etKeyword.setTextColor(getResources().getColor(R.color.black1));
            keywordID = data.getIntExtra("ID", 0);
        } else
        // 从选择拜访人返回
        if(requestCode == CommonFinal.SELECT_VISIT_PERSON_REQUEST_CODE && resultCode == CommonFinal.SELECT_VISIT_PERSON_RESULT_CODE){
            Intent intent = new Intent();
            // 单位信息
            intent.putExtra("unitID", currentID);
            intent.putExtra("unitName", titName.getText().toString().trim());

            if(data.hasExtra("personID")) {
                // 拜访人信息
                intent.putExtra("personID", data.getIntExtra("personID", 0));
                intent.putExtra("personName", data.getStringExtra("personName"));
                intent.putExtra("personDepartment", data.getStringExtra("personDepartment"));
            }

            // 项目信息
            if(data.hasExtra("projectID")){
                intent.putExtra("projectID", data.getIntExtra("projectID",0));
                intent.putExtra("projectName", data.getStringExtra("projectName"));
            }

            setResult(CommonFinal.SELECT_VISIT_UNIT_RESULT_CODE, intent);
            finish();
        }
    }
}
