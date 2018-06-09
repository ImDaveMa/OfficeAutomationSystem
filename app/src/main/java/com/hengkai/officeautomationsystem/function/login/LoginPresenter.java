package com.hengkai.officeautomationsystem.function.login;

import android.util.Log;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.LoginEntity;
import com.hengkai.officeautomationsystem.utils.SPUtils;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/2.
 */
public class LoginPresenter extends BasePresenter<LoginActivity> {

    private final LoginModel model;

    public LoginPresenter() {
        model = new LoginModel();
    }

    public void login(String loginName, String password) {
        model.login(loginName, password, new Observer<LoginEntity>() {

            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.LOGIN_ACTIVITY_LOGIN, d);
            }

            @Override
            public void onNext(LoginEntity loginEntity) {
                if (loginEntity.CODE == 1) {
                    saveUserInfo(loginEntity);
                    view.loginSuccess();
                } else {
                    ToastUtil.showToast(loginEntity.MES);
                    view.setLoginButtonStatus(true);
                }
            }

            @Override
            public void onError(Throwable e) {
                view.dismissDialog();
                view.setLoginButtonStatus(true);
                ToastUtil.showToast("登陆失败:" + e.getMessage());
            }

            @Override
            public void onComplete() {
                view.setLoginButtonStatus(true);
                view.dismissDialog();
            }
        });
    }

    /**
     * 保存用户登录信息
     */
    private void saveUserInfo(LoginEntity loginEntity) {

        Log.i("666", loginEntity.toString());

        //保存登录状态
        SPUtils.putBoolean(UserInfo.IS_LOGIN.name(), true);

        SPUtils.putString(UserInfo.TOKEN.name(), loginEntity.TOKEN);
        SPUtils.putString(UserInfo.USER_ID.name(), String.valueOf(loginEntity.USER.id));
        SPUtils.putString(UserInfo.LOGIN_NAME.name(), loginEntity.USER.loginName);
        SPUtils.putString(UserInfo.PHONE.name(), loginEntity.USER.phone);
        SPUtils.putString(UserInfo.JOB_NUMBER.name(), loginEntity.USER.jobNumber);
        SPUtils.putLong(UserInfo.LAST_LOGIN_TIME.name(), loginEntity.USER.lastLoginTime);
        SPUtils.putInt(UserInfo.STATUS.name(), loginEntity.USER.status);
        SPUtils.putInt(UserInfo.CREATE_USER_ID.name(), loginEntity.USER.createUserId);
        SPUtils.putLong(UserInfo.CREATE_TIME.name(), loginEntity.USER.createTime);
        SPUtils.putString(UserInfo.ICON_LINK.name(), loginEntity.USER.iconLink);
        SPUtils.putString(UserInfo.POSITION.name(), loginEntity.USER.position);
        SPUtils.putString(UserInfo.DEPARTMENT_NAME.name(), loginEntity.USER.departmentName);
        SPUtils.putString(UserInfo.DEPARTMENT_ID.name(), String.valueOf(loginEntity.USER.departmentId));
        SPUtils.putString(UserInfo.DEPARTMENT_PERMISSION.name(), loginEntity.USER.departmentPermission);
        SPUtils.putString(UserInfo.REAL_NAME.name(), loginEntity.USER.realName);
    }
}
