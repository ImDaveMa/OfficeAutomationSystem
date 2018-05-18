package com.hengkai.officeautomationsystem.function.report;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.ReportEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/18.
 */
public class ReportPresenter extends BasePresenter<ReportActivity> {

    private final ReportModel model;

    public ReportPresenter() {
        model = new ReportModel();
    }

    public void getReportList(int type, int searchID) {
        model.getReportList(type, searchID, new Observer<ReportEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.REPORT_ACTIVITY_GET_REPORT_LIST, d);
            }

            @Override
            public void onNext(ReportEntity reportEntity) {
                switch (reportEntity.CODE) {
                    case 1:
                        view.getReportList(reportEntity.DATE);
                        break;
                    case 0:
                        view.showLoginDialog(view);
                        break;
                    case -2:
                        ToastUtil.showToast("没有更多数据了");
                        break;
                    default:
                        ToastUtil.showToast(reportEntity.MES);
                        break;
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
