package com.hengkai.officeautomationsystem.function.setting;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.utils.SPUtils;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ChangePasswordPresenter extends BasePresenter<ChangePasswordActivity> {

    private final ChangePasswordModel model;

    public ChangePasswordPresenter() {
        model = new ChangePasswordModel();
    }

    public void changePassword(String oldPwd, String newPwd) {
        Map<String,String> params = new HashMap<>();
        params.put("OLDPWD", oldPwd);
        params.put("NEWPWD", newPwd);
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));

        view.showDialog();
        model.request(URLFinal.CHANGE_PASSWORD, params, new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.CHANGE_PASSWORD_ACTIVITY_URL, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity commonEntity) {
                view.dismissDialog();
                if (commonEntity.CODE == 1) {
                    ToastUtil.showToast("密码修改成功");
                    view.finish();
                } else if (commonEntity.CODE == 0) {
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(commonEntity.MES);
                }
            }

            @Override
            public void onError(Throwable e) {
                view.dismissDialog();
                ToastUtil.showToast("网络连接错误");
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
