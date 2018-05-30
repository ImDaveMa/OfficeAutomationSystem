package com.hengkai.officeautomationsystem.function.notice;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.approve.ApproveFragment;
import com.hengkai.officeautomationsystem.function.approve.ApproveListModel;
import com.hengkai.officeautomationsystem.network.entity.MessageEntity;
import com.hengkai.officeautomationsystem.network.entity.NoticeEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NoticeListPresenter extends BasePresenter<NoticeFragment> {

    private final NoticeListModel model;

    public NoticeListPresenter() {
        model = new NoticeListModel();
    }

    public void getNoticeList(int id, int state) {
        model.getNoticeList(id, state, new Observer<NoticeEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.APPROVE_LIST_ACTIVITY_GET_LIST, d);
            }

            @Override
            public void onNext(NoticeEntity mEntity) {
                view.stopRefreshing();
                if (mEntity.CODE == 1) {
                    view.prepareData(mEntity.DATE);
                } else if (mEntity.CODE == -2) {
                    // 返回的数据是空，所以不能处理列表
                } else if (mEntity.CODE == 0) {//TOKEN失效
                    view.showLoginDialog(view.getContext());
                }
            }

            @Override
            public void onError(Throwable e) {
                view.stopRefreshing();
                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
