package com.hengkai.officeautomationsystem.function.message;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.approve.ApproveListActivity;
import com.hengkai.officeautomationsystem.function.approve.ApproveListModel;
import com.hengkai.officeautomationsystem.network.entity.MessageEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MessageListPresenter extends BasePresenter<MessageFragment> {

    private final MessageListModel model;

    public MessageListPresenter() {
        model = new MessageListModel();
    }

    public void getMsgList(final int id, int state) {
        model.getMsgList(new Observer<MessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.MESSAGE_LIST_ACTIVITY_GET_LIST, d);
            }

            @Override
            public void onNext(MessageEntity msgEntity) {
                view.stopRefreshing();
                if (msgEntity.getCODE() == 1) {
                    List<MessageEntity.MsgBean> beans = msgEntity.getDATE();
                    if(id == 0 && (beans == null || beans.size() <= 0)){
                        view.noData();
                    } else {
                        view.prepareData(beans);
                    }
                } else if (msgEntity.getCODE() == 0) {//TOKEN失效
                    view.showLoginDialog(view.getActivity());
                } else {
                    ToastUtil.showToast(msgEntity.getMES());
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
        }, id, state);

    }
}
