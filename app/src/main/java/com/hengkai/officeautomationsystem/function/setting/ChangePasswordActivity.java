package com.hengkai.officeautomationsystem.function.setting;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.base.BaseActivity;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("Registered")
public class ChangePasswordActivity extends BaseActivity<ChangePasswordPresenter> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.tv_new_password)
    TextView tvNewPassword;
    @BindView(R.id.tv_confirm_password)
    TextView tvConfirmPassword;

    @Override
    protected int setupView() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //设置沉浸式状态栏, 参数2: 颜色, 参数3: 透明度(0-255, 0表示透明, 255不透明)
        StatusBarUtil.setColor(this, getResources().getColor(R.color.app_theme_color), 0);

        tvTitle.setText("修改密码");
    }

    @OnClick({R.id.iv_back, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                changePassword();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void changePassword() {
        String password = tvPassword.getText().toString().trim();
        String newPassword = tvNewPassword.getText().toString().trim();
        String confirmPassword = tvConfirmPassword.getText().toString().trim();
        if(TextUtils.isEmpty(password)){
            ToastUtil.showToast("请填写原密码");
            tvPassword.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(newPassword)){
            ToastUtil.showToast("请填写新密码");
            tvNewPassword.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(tvConfirmPassword.getText())){
            ToastUtil.showToast("请填写重复密码");
            tvConfirmPassword.requestFocus();
            return;
        }
        if(!newPassword.equals(confirmPassword)){
            ToastUtil.showToast("两次填写的密码不一致");
            tvConfirmPassword.requestFocus();
            return;
        }
        mPresenter.changePassword(password, newPassword);
    }

    @Override
    protected ArrayList<String> cancelNetWork() {
        ArrayList<String> tags = new ArrayList<>();
        tags.add(NetworkTagFinal.CHANGE_PASSWORD_ACTIVITY_URL);
        return tags;
    }

    @Override
    protected ChangePasswordPresenter bindPresenter() {
        return new ChangePasswordPresenter();
    }
}
