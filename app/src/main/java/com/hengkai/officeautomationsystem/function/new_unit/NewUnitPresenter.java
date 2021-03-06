package com.hengkai.officeautomationsystem.function.new_unit;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.NewUnitTypeEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/11.
 */
public class NewUnitPresenter extends BasePresenter<NewUnitActivity> {

    private final NewUnitModel model;

    public NewUnitPresenter() {
        model = new NewUnitModel();
    }

    public void getTypeList() {
        model.getTypeList(new Observer<NewUnitTypeEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.NEW_UNIT_ACTIVITY_GET_TYPE_LIST, d);
            }

            @Override
            public void onNext(NewUnitTypeEntity newUnitTypeEntity) {
                if (newUnitTypeEntity.CODE == 1) {
                    if (newUnitTypeEntity.DATA == null || newUnitTypeEntity.DATA.size() == 0) {
                        ToastUtil.showToast("暂无单位类型数据");
                    } else {
                        view.getTypeList(newUnitTypeEntity.DATA);
                    }
                } else if (newUnitTypeEntity.CODE == 0) {
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(newUnitTypeEntity.MES);
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("请求网络失败");
                view.dismissDialog();
            }

            @Override
            public void onComplete() {
                view.dismissDialog();
            }
        });
    }

    public void commit(Map<String, String> params) {
        model.commit(params, new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.NEW_UNIT_ACTIVITY_COMMIT, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity commonReceiveMessageEntity) {
                if (commonReceiveMessageEntity.CODE == 1) {
                    ToastUtil.showToast("提交成功");
                    view.commitResultSuccess(commonReceiveMessageEntity.DATA);
                } else if (commonReceiveMessageEntity.CODE == 0) {
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(commonReceiveMessageEntity.MES);
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("请求网络失败");
                view.dismissDialog();
            }

            @Override
            public void onComplete() {
                view.dismissDialog();
            }
        });
    }
}
