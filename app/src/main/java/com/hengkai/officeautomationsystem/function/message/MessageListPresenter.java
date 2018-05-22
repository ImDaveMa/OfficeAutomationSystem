package com.hengkai.officeautomationsystem.function.message;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.approve.ApproveListActivity;
import com.hengkai.officeautomationsystem.function.approve.ApproveListModel;
import com.hengkai.officeautomationsystem.network.entity.MessageEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MessageListPresenter extends BasePresenter<MessageListActivity> {

    private final MessageListModel model;

    public MessageListPresenter() {
        model = new MessageListModel();
    }

    public void getMsgList(int id) {
        model.getMsgList(new Observer<MessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.MESSAGE_LIST_ACTIVITY_GET_LIST, d);
            }

            @Override
            public void onNext(MessageEntity msgEntity) {
                view.stopRefreshing();
                if (msgEntity.getCODE() == 1) {
                    view.prepareData(msgEntity.getDATE());
                } else if (msgEntity.getCODE() == -2) {
                    // 返回的数据是空，所以不能处理列表
                } else if (msgEntity.getCODE() == 0) {//TOKEN失效
                    view.showLoginDialog(view);
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
        }, id);

    }
}
