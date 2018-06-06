package com.hengkai.officeautomationsystem.function.new_project;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.NewProjectGetPersonEntity;
import com.hengkai.officeautomationsystem.network.entity.NewProjectGetTypeEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/6/5.
 */
public class NewProjectPresenter extends BasePresenter<NewProjectActivity> {

    private final NewProjectModel model;

    public NewProjectPresenter() {
        model = new NewProjectModel();
    }

    public void getTypeList() {
        model.getTypeList(new Observer<NewProjectGetTypeEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.NEW_PROJECT_ACTIVITY_GET_TYPE_LIST, d);
            }

            @Override
            public void onNext(NewProjectGetTypeEntity newProjectGetTypeEntity) {
                switch (newProjectGetTypeEntity.CODE) {
                    case 1:
                        if (newProjectGetTypeEntity.DATE.size() == 0) {
                            ToastUtil.showToast("暂无项目类型");
                        } else {
                            view.getTypeList(newProjectGetTypeEntity.DATE);
                        }
                        break;
                    case 0:
                        view.showLoginDialog(view);
                        break;
                    case 2:
                        ToastUtil.showToast("查询失败:" + newProjectGetTypeEntity.MES);
                        break;
                    case 3:
                        ToastUtil.showToast("提交参数有误");
                        break;
                    case 4:
                        ToastUtil.showToast("出现异常");
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("网络请求错误");
                view.dismissDialog();
            }

            @Override
            public void onComplete() {
                view.dismissDialog();
            }
        });
    }

    public void getPersonList() {
        model.getPersonList(new Observer<NewProjectGetPersonEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.NEW_PROJECT_ACTIVITY_GET_PERSON_LIST, d);
            }

            @Override
            public void onNext(NewProjectGetPersonEntity newProjectGetPersonEntity) {
                switch (newProjectGetPersonEntity.CODE) {
                    case 1:
                        if (newProjectGetPersonEntity.DATE.size() == 0) {
                            ToastUtil.showToast("暂无项目负责人");
                        } else {
                            view.getPersonList(newProjectGetPersonEntity.DATE);
                        }
                        break;
                    case 0:
                        view.showLoginDialog(view);
                        break;
                    case 2:
                        ToastUtil.showToast("查询失败:" + newProjectGetPersonEntity.MES);
                        break;
                    case 3:
                        ToastUtil.showToast("提交参数有误");
                        break;
                    case 4:
                        ToastUtil.showToast("出现异常");
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("网络请求错误");
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
                RxApiManager.get().add(NetworkTagFinal.NEW_PROJECT_ACTIVITY_ADD_PROJECT, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity commonReceiveMessageEntity) {
                switch (commonReceiveMessageEntity.CODE) {
                    case 1:
                        ToastUtil.showToast("新增成功");
                        view.setResult(CommonFinal.COMMON_RESULT_CODE);
                        view.finish();
                        break;
                    case 0:
                        view.showLoginDialog(view);
                        break;
                    case 2:
                        ToastUtil.showToast("操作失败:" + commonReceiveMessageEntity.MES);
                        break;
                    case 3:
                        ToastUtil.showToast("提交参数有误");
                        break;
                    case 4:
                        ToastUtil.showToast("出现异常");
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("网络请求错误");
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
