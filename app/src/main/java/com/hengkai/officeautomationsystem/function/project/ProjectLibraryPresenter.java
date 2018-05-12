package com.hengkai.officeautomationsystem.function.project;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.management_of_goods.ManagementUseGoodsActivity;
import com.hengkai.officeautomationsystem.function.management_of_goods.ManagementUseGoodsModel;
import com.hengkai.officeautomationsystem.network.entity.ProjectEntity;
import com.hengkai.officeautomationsystem.network.entity.UseGoodsEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ProjectLibraryPresenter extends BasePresenter<ProjectLibraryActivity> {

    private final ProjectLibraryModel model;

    public ProjectLibraryPresenter() {
        model = new ProjectLibraryModel();
    }

    public void getProjectList(int id) {
        model.getProjectList(new Observer<ProjectEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.PROJECT_LIBRARY_ACTIVITY_GET_PROJECT_LIST, d);
            }

            @Override
            public void onNext(ProjectEntity projectEntity) {
                view.stopRefreshing();
                if (projectEntity.getCODE() == 1) {
                    List<ProjectEntity.ProjectBean> list = projectEntity.getDATE();
                    view.prepareData(list);
                } else if (projectEntity.getCODE() == -2) {
                    // 返回的数据是空，所以不能处理列表
                } else if (projectEntity.getCODE() == 0) {//TOKEN失效
                    view.showLoginDialog(view);
                }
            }

            @Override
            public void onError(Throwable e) {
                view.stopRefreshing();
                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {

            }
        }, id);

    }
}
