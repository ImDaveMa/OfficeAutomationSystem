package com.hengkai.officeautomationsystem.function.project_library.detail;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.ProjectLibraryDetailEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/17.
 */
public class ProjectLibraryDetailPresenter extends BasePresenter<ProjectLibraryDetailActivity> {

    private final ProjectLibraryDetailModel model;

    public ProjectLibraryDetailPresenter() {
        model = new ProjectLibraryDetailModel();
    }

    public void getProjectDetail(int projectID) {
        model.getProjectDetail(projectID, new Observer<ProjectLibraryDetailEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.PROJECT_LIBRARY_DETAIL_ACTIVITY_GET_PROJECT_DETAIL, d);
            }

            @Override
            public void onNext(ProjectLibraryDetailEntity projectLibraryDetailEntity) {
                //-1：缺少参数 1：查询成功 2：查询结果为空 3：操作失败
                switch (projectLibraryDetailEntity.CODE) {
                    case 1:
                        view.getProjectDetail(projectLibraryDetailEntity.DATE);
                        break;
                    case 0:
                        view.showLoginDialog(view);
                        break;
                    default:
                        ToastUtil.showToast(projectLibraryDetailEntity.MES);
                        break;
                }

            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
