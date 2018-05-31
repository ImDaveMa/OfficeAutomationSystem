package com.hengkai.officeautomationsystem.function.contacts_library.add;

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

    @Override
    protected int setupView() {
        return R.layout.activity_add_contact;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);

        unitID = getIntent().getIntExtra(ContactsLibraryActivity.EXTRA_KEY_UNIT_ID, 0);
        String unitName = getIntent().getStringExtra("unitName");
        if (unitID != 0 && !TextUtils.isEmpty(unitName)) {
            tvUnit.setText(unitName);
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
    protected void addSuccess() {
        ToastUtil.showToast("物品供应商添加成功");
        setResult(RESULT_OK);
        finish();
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
        }
    }
}
