package com.hengkai.officeautomationsystem.function.contacts_library.add;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.contacts_library.ContactsLibraryActivity;
import com.hengkai.officeautomationsystem.function.new_project.NewProjectActivity;
import com.hengkai.officeautomationsystem.function.new_unit.NewUnitActivity;
import com.hengkai.officeautomationsystem.function.visit_record.select_visit_unit.SelectVisitUnitActivity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 物品领用Activity
 */
public class AddContactActivity extends BaseActivity<AddContactPresenter> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.iv_unit)
    ImageView ivUnit;
    @BindView(R.id.rl_unit)
    RelativeLayout rlUnit;
    @BindView(R.id.et_department)
    EditText etDepartment;
    @BindView(R.id.et_position)
    EditText etPosition;
    @BindView(R.id.et_birthday)
    EditText etBirthday;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.et_wechat)
    EditText etWechat;
    @BindView(R.id.et_tel)
    EditText etTel;
    @BindView(R.id.et_mail)
    EditText etMail;
    @BindView(R.id.et_car_number)
    EditText etCarNumber;
    @BindView(R.id.et_hobby)
    EditText etHobby;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    private int unitID;
    private String unitName;
    private int personID;

    /**
     * 是否为拜访跟进
     */
    private boolean openByVisit;

    private Intent resultIntent = new Intent();

    @Override
    protected int setupView() {
        return R.layout.activity_add_contact;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);
        tvTitle.setText("添加联系人");

        // 获取配置
        Intent intent = getIntent();

        if(intent.hasExtra(CommonFinal.EXTRA_KEY_OPEN_BY_VISIT)) {
            openByVisit = intent.getBooleanExtra(CommonFinal.EXTRA_KEY_OPEN_BY_VISIT,false);
            if(openByVisit){
                unitID = intent.getIntExtra("unitID", 0);
                unitName = getIntent().getStringExtra("unitName");
                if (unitID != 0 && !TextUtils.isEmpty(unitName)) {
                    tvUnit.setText(unitName);
                }
                // 禁止选择单位
                rlUnit.setClickable(false);
            } else {
                unitID = intent.getIntExtra(ContactsLibraryActivity.EXTRA_KEY_UNIT_ID, 0);
                unitName = getIntent().getStringExtra("unitName");
                if (unitID != 0 && !TextUtils.isEmpty(unitName)) {
                    tvUnit.setText(unitName);
                }
            }
        } else {
            unitID = intent.getIntExtra(ContactsLibraryActivity.EXTRA_KEY_UNIT_ID, 0);
            unitName = getIntent().getStringExtra("unitName");
            if (unitID != 0 && !TextUtils.isEmpty(unitName)) {
                tvUnit.setText(unitName);
            }
        }
    }

    @Override
    protected AddContactPresenter bindPresenter() {
        return new AddContactPresenter();
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.ADD_CONTACT_ACTIVITY_ADD_CONTACT);
        return tags;
    }

    /**
     * 保存方法
     */
    private void commit() {
        String name = etName.getText().toString().trim();
        String unit = tvUnit.getText().toString().trim();
        String department = etDepartment.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast("请输入联系人名称");
            etName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(unit) || unit.equals("请选择所属单位")) {
            ToastUtil.showToast("请选择所属单位");
            return;
        }

        if (TextUtils.isEmpty(department)) {
            ToastUtil.showToast("请输入部门");
            etDepartment.requestFocus();
            return;
        }

        String phone = etPhone.getText().toString().trim();
        String position = etPosition.getText().toString().trim();
        String birthday = etBirthday.getText().toString().trim();
        String sex = tvSex.getText().toString().trim();
        String weChat = etWechat.getText().toString().trim();
        String tel = etTel.getText().toString().trim();
        String mail = etMail.getText().toString().trim();
        String carNumber = etCarNumber.getText().toString().trim();
        String hobby = etHobby.getText().toString().trim();

        mPresenter.saveContact(name, phone, unitID, birthday, sex.equals("男"), department, position, weChat, tel, mail, hobby, carNumber);
    }

    /**
     * 添加成功
     */
    protected void addSuccess(final int personID) {
        this.personID = personID;
        resultIntent.putExtra("personID", personID);
        resultIntent.putExtra("personName", etName.getText().toString().trim());
        resultIntent.putExtra("personDepartment", etDepartment.getText().toString().trim());

        if(openByVisit){
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("是否继续添加项目？")
                    .setCancelable(false)
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            Intent intent = new Intent(AddContactActivity.this, NewProjectActivity.class);
                            intent.putExtra(CommonFinal.EXTRA_KEY_OPEN_BY_VISIT, openByVisit);
                            intent.putExtra("currentCustomerID", personID);
                            intent.putExtra("customerName", etName.getText().toString().trim());
                            intent.putExtra("unitID", unitID);
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
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }

    @OnClick({R.id.rl_unit, R.id.rl_sex, R.id.tv_submit, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_unit:
                Intent intent = new Intent(this, SelectVisitUnitActivity.class);
                startActivityForResult(intent, CommonFinal.SELECT_VISIT_UNIT_REQUEST_CODE);
                break;
            case R.id.rl_sex:
                showBottomDialog();
                break;
            case R.id.tv_submit:
                commit();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void showBottomDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = View.inflate(this, R.layout.dialog_select_sex, null);
        TextView tvMan = view.findViewById(R.id.tv_man);
        TextView tvWoman = view.findViewById(R.id.tv_woman);
        dialog.setContentView(view);

        tvMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSex.setText("男");
                dialog.dismiss();
            }
        });

        tvWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSex.setText("女");
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CommonFinal.SELECT_VISIT_UNIT_REQUEST_CODE && resultCode == CommonFinal.SELECT_VISIT_UNIT_RESULT_CODE) {
            unitID = data.getIntExtra("ID", 0);
            tvUnit.setText(data.getStringExtra("name"));
        } else
        // 从新增项目返回
        if(requestCode == CommonFinal.SELECT_VISIT_PROJECT_REQUEST_CODE && resultCode == CommonFinal.SELECT_VISIT_PROJECT_RESULT_CODE){
            Intent intent = new Intent();

            // 拜访人信息
            intent.putExtra("personID", personID);
            intent.putExtra("personName", etName.getText().toString().trim());
            intent.putExtra("personDepartment", etDepartment.getText().toString().trim());


            // 项目信息
            if(data.hasExtra("projectID")){
                intent.putExtra("projectID", data.getIntExtra("projectID",0));
                intent.putExtra("projectName", data.getStringExtra("projectName"));
            }

            setResult(CommonFinal.SELECT_VISIT_PERSON_RESULT_CODE, intent);
            finish();
        }
    }
}
