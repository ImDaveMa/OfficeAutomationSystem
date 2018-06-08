package com.hengkai.officeautomationsystem.function.visit_record;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

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

    public void getVisitRecordList(String userID, final int pageNum) {
        model.getVisitRecordList(userID, pageNum, new Observer<VisitRecordEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.VISIT_RECORD_ACTIVITY_GET_VISIT_RECORD_LIST, d);
            }

            @Override
            public void onNext(VisitRecordEntity visitRecordEntity) {
                if (visitRecordEntity.CODE == 1) {
                    List<VisitRecordEntity.DATABean> data = visitRecordEntity.DATA;
                    if (pageNum == 0 && (data == null || data.size() == 0)) {
                        view.noData();
                    } else {
                        if (data != null && data.size() != 0) {
                            view.getVisitRecordList(visitRecordEntity.DATA);
                        } else {
                            ToastUtil.showToast("没有更多数据了");
                        }
                    }
                } else if (visitRecordEntity.CODE == 0) {
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(visitRecordEntity.MES);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (pageNum == 0) {
                    view.noData();
                }
                ToastUtil.showToast("请求网络失败");
                view.stopRefreshing();
            }

            @Override
            public void onComplete() {
                view.stopRefreshing();
            }
        });
    }

    public void getVisitRecordListByDay(int day, String userID, final int pageNum) {
        model.getVisitRecordListByDay(day, userID, pageNum, new Observer<VisitRecordEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.VISIT_RECORD_ACTIVITY_GET_VISIT_RECORD_LIST, d);
            }

            @Override
            public void onNext(VisitRecordEntity visitRecordEntity) {
                if (visitRecordEntity.CODE == 1) {
                    List<VisitRecordEntity.DATABean> data = visitRecordEntity.DATA;
                    if (pageNum == 0 && (data == null || data.size() == 0)) {
                        view.noData();
                    } else {
                        if (data != null && data.size() != 0) {
                            view.getVisitRecordList(visitRecordEntity.DATA);
                        } else {
                            ToastUtil.showToast("没有更多数据了");
                        }
                    }

                } else if (visitRecordEntity.CODE == 0) {
                    view.showLoginDialog(view);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (pageNum == 0) {
                    view.noData();
                }
                ToastUtil.showToast("请求网络失败");
                view.stopRefreshing();
            }

            @Override
            public void onComplete() {
                view.stopRefreshing();
            }
        });
    }

    public void deleteItem(int ID) {
        model.deleteItem(ID, new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.VISIT_RECORD_ACTIVITY_GET_VISIT_RECORD_LIST, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity commonReceiveMessageEntity) {
                if (commonReceiveMessageEntity.CODE == 1) {
                    view.deleteItem();
                } else if (commonReceiveMessageEntity.CODE == 0) {
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(commonReceiveMessageEntity.MES);
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
