package com.hengkai.officeautomationsystem.function.visit_record.select_visit_project;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailGetVisitUnitEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/6/15.
 */
public class SelectVisitProjectPresenter extends BasePresenter<SelectVisitProjectActivity> {

    private final SelectVisitProjectModel model;

    public SelectVisitProjectPresenter() {
        model = new SelectVisitProjectModel();
    }

    public void getVisitProjectList(int unitID) {
        model.getVisitProjectList(unitID, new Observer<VisitRecordDetailGetVisitUnitEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_GET_VISIT_UNIT_PROJECT_LIST, d);
            }

            @Override
            public void onNext(VisitRecordDetailGetVisitUnitEntity visitRecordDetailGetVisitUnitEntity) {
                if (visitRecordDetailGetVisitUnitEntity.CODE == 1) {
                    List<VisitRecordDetailGetVisitUnitEntity.DATABean> data = visitRecordDetailGetVisitUnitEntity.DATA;
                    if (data == null || data.size() == 0) {
                        view.noData();
                    } else {
                        view.removeNoDataLayout(2);
                        view.getVisitProjectList(visitRecordDetailGetVisitUnitEntity.DATA);
                    }
                } else if (visitRecordDetailGetVisitUnitEntity.CODE == 0) {
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(visitRecordDetailGetVisitUnitEntity.MES);
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("网络连接错误");
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
