package com.hengkai.officeautomationsystem.function.notice;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.approve.ApproveFragment;
import com.hengkai.officeautomationsystem.function.approve.ApproveListModel;
import com.hengkai.officeautomationsystem.network.entity.MessageEntity;
import com.hengkai.officeautomationsystem.network.entity.NoticeEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NoticeListPresenter extends BasePresenter<NoticeFragment> {

    private final NoticeListModel model;

    public NoticeListPresenter() {
        model = new NoticeListModel();
    }

    public void getNoticeList(final int id, int state) {
        model.getNoticeList(id, state, new Observer<NoticeEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.NOTICE_FRAGMENT_GET_LIST, d);
            }

            @Override
            public void onNext(NoticeEntity mEntity) {
                view.stopRefreshing();
                if (mEntity.CODE == 1) {
                    List<NoticeEntity.DATEBean> beans = mEntity.DATE;
                    if(id == 0 && (beans == null || beans.size() <= 0)){
                        view.noData();
                    } else {
                        if(beans != null && beans.size() > 0){
                            view.prepareData(beans);
                        } else {
                            ToastUtil.showToast("没有更多数据");
                        }
                    }
                } else if (mEntity.CODE == 0) {//TOKEN失效
                    view.showLoginDialog(view.getContext());
                } else {
                    ToastUtil.showToast(mEntity.MES);
                }
            }

            @Override
            public void onError(Throwable e) {
                view.stopRefreshing();
                if(id == 0){
                    view.noData();
                }
                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
