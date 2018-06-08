package com.hengkai.officeautomationsystem.function.report;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.ReportEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

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

    public void getReportList(int type, final int searchID) {
        model.getReportList(type, searchID, new Observer<ReportEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.REPORT_ACTIVITY_GET_REPORT_LIST, d);
            }

            @Override
            public void onNext(ReportEntity reportEntity) {
                switch (reportEntity.CODE) {
                    case 1:
                        List<ReportEntity.DATEBean> data = reportEntity.DATE;
                        if (searchID == 0 && (data == null || data.size() == 0)) {
                            view.noData();
                        } else {
                            if (data != null && data.size() != 0) {
                                view.getReportList(data);
                            } else {
                                ToastUtil.showToast("没有更多数据了");
                            }
                        }
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
                if (searchID == 0) {
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

    public void comment(final int position, int currentID, String commentContent) {
        model.comment(currentID, commentContent, new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.REPORT_ACTIVITY_COMMENT, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity commonReceiveMessageEntity) {
                switch (commonReceiveMessageEntity.CODE) {
                    case 1:
                        ToastUtil.showToast("提交评论成功");
                        view.refreshItem(position);
                        break;
                    case 0:
                        view.showLoginDialog(view);
                        break;
                    default:
                        ToastUtil.showToast(commonReceiveMessageEntity.MES);
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("网络连接错误");
                view.dismissDialog();
            }

            @Override
            public void onComplete() {
                view.dismissDialog();
            }
        });
    }
}
