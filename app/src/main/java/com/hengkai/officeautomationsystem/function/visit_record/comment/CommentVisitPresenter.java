package com.hengkai.officeautomationsystem.function.visit_record.comment;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/16.
 */
public class CommentVisitPresenter extends BasePresenter<CommentVisitActivity> {

    private final CommentVisitModel model;

    public CommentVisitPresenter() {
        model = new CommentVisitModel();
    }

    public void getVisitRecordDetail(int currentVisitRecordID) {
        model.getVisitRecordDetail(currentVisitRecordID, new Observer<VisitRecordDetailEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.COMMENT_VISIT_ACTIVITY_GET_VISIT_DETAIL, d);
            }

            @Override
            public void onNext(VisitRecordDetailEntity visitRecordDetailEntity) {
                if (visitRecordDetailEntity.CODE == 1) {
                    view.getVisitRecordDetail(visitRecordDetailEntity.DATA);
                } else if (visitRecordDetailEntity.CODE == 0) {
                    view.showLoginDialog(view);
                } else {
                    //传入ID为空
                    ToastUtil.showToast("获取信息出错了");
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
