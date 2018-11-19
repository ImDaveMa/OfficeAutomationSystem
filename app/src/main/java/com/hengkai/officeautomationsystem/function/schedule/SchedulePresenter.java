package com.hengkai.officeautomationsystem.function.schedule;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.CommonStringListEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SchedulePresenter extends BasePresenter<ScheduleActivity> {

    private final ScheduleModel model;

    public SchedulePresenter() {
        model = new ScheduleModel();
    }

    public void getCalendarList(int year, int month) {
        view.showDialog();
        model.getCalendarList(year, month, new Observer<CommonStringListEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.SCHEDULE_ACTIVITY_GET_LIST, d);
            }

            @Override
            public void onNext(CommonStringListEntity reportEntity) {
                view.dismissDialog();
                switch (reportEntity.CODE) {
                    case 1:
                        List<String> data = reportEntity.DATE;
                        view.renderCalendar(data);
                        break;
                    case 0:
                        view.showLoginDialog(view);
                        break;
                    default:
                        ToastUtil.showToast(reportEntity.MES);
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                view.dismissDialog();
                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void getCalendarListWithDay(int year, int month, int day) {
        view.showDialog();
        model.getCalendarListWithDay(year, month, day, new Observer<CommonStringListEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.SCHEDULE_ACTIVITY_GET_LIST_WITH_DAY, d);
            }

            @Override
            public void onNext(CommonStringListEntity reportEntity) {
                view.dismissDialog();
                switch (reportEntity.CODE) {
                    case 1:
                        List<String> data = reportEntity.DATE;
                        view.renderDayCalendars(data);
                        break;
                    case 0:
                        view.showLoginDialog(view);
                        break;
                    default:
                        ToastUtil.showToast(reportEntity.MES);
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                view.dismissDialog();
                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
