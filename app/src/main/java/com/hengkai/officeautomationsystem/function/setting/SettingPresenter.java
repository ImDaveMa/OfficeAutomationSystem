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

public class SettingPresenter extends BasePresenter<SettingActivity> {

    private final SettingModel model;

    public SettingPresenter() {
        model = new SettingModel();
    }

    public void logout() {
        Map<String,String> params = new HashMap<>();
        view.showDialog();
        model.request(URLFinal.LOGOUT_URL, params, new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.SETTING_ACTIVITY_LOGOUT_URL, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity commonEntity) {
                view.dismissDialog();
                if (commonEntity.CODE == 1) {
                    // 登出成功
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
