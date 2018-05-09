package com.hengkai.officeautomationsystem.function.visit_record;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/7.
 */
public class VisitRecordActivityPresenter extends BasePresenter<VisitRecordActivity> {

    private final VisitRecordActivityModel model;

    public VisitRecordActivityPresenter() {
        model = new VisitRecordActivityModel();
    }

    public void getVisitRecordList() {
        model.getVisitRecordList(new Observer<VisitRecordEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.VISIT_RECORD_ACTIVITY_GET_VISIT_RECORD_LIST, d);
            }

            @Override
            public void onNext(VisitRecordEntity visitRecordEntity) {
                if (visitRecordEntity.CODE == 1) {
                    view.getVisitRecordList(visitRecordEntity.DATA);
                } else if (visitRecordEntity.CODE == 0) {
                    view.showLoginDialog(view);
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("请求网络失败");
                view.stopRefreshing();
            }

            @Override
            public void onComplete() {
                view.stopRefreshing();
            }
        });
    }
}