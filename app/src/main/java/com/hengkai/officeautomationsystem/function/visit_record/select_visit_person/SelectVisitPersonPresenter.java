package com.hengkai.officeautomationsystem.function.visit_record.select_visit_person;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailGetVisitUnitEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/30.
 */
public class SelectVisitPersonPresenter extends BasePresenter<SelectVisitPersonActivity> {

    private final SelectVisitPersonModel model;

    public SelectVisitPersonPresenter() {
        model = new SelectVisitPersonModel();
    }

    public void getVisitCustomerList(int unitID) {
        model.getVisitCustomerList(unitID, new Observer<VisitRecordDetailGetVisitUnitEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_GET_VISIT_UNIT_CUSTOMER_LIST, d);
            }

            @Override
            public void onNext(VisitRecordDetailGetVisitUnitEntity visitRecordDetailGetVisitUnitEntity) {
                if (visitRecordDetailGetVisitUnitEntity.CODE == 1) {
                    view.getVisitCustomerList(visitRecordDetailGetVisitUnitEntity.DATA);
                } else if (visitRecordDetailGetVisitUnitEntity.CODE == 0) {
                    view.showLoginDialog(view);
                } else {
                    //code=-2
                    ToastUtil.showToast("暂无相关联系人");
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
