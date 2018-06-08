package com.hengkai.officeautomationsystem.function.project_library;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.ProjectEntity;
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

    public void getProjectList(final int id) {
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
                    if (id == 0 && (list == null || list.size() == 0)) {
                        view.noData(2);
                    } else {
                        if (list != null && list.size() != 0) {
                            view.prepareData(list);
                        } else {
                            ToastUtil.showToast("没有更多数据了");
                        }
                    }
                } else if (projectEntity.getCODE() == 0) {//TOKEN失效
                    view.showLoginDialog(view);
                } else { //缺少参数
                    ToastUtil.showToast(projectEntity.getMES());
                }
            }

            @Override
            public void onError(Throwable e) {
                if (id == 0) {
                    view.noData(2);
                }
                view.stopRefreshing();
                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {

            }
        }, id);

    }
}
