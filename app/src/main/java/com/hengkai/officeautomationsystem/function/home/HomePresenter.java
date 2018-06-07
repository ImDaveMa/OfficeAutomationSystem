package com.hengkai.officeautomationsystem.function.home;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.contacts.ContactsActivity;
import com.hengkai.officeautomationsystem.function.contacts.ContactsActivityModel;
import com.hengkai.officeautomationsystem.network.entity.ContactsEntity;
import com.hengkai.officeautomationsystem.network.entity.MessageEntity;
import com.hengkai.officeautomationsystem.network.entity.NoticeEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/4.
 */
public class HomePresenter extends BasePresenter<HomeFragment> {

    private final HomeModel model;

    public HomePresenter() {
        model = new HomeModel();
    }

    public void getApproveList() {
        model.getApproveList(new Observer<MessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.HOME_FRAGMENT_GET_APPROVE_DATA, d);
            }

            @Override
            public void onNext(MessageEntity contactsEntity) {
                if (contactsEntity.getCODE() == 1) {
                    List<MessageEntity.MsgBean> list = contactsEntity.getDATE();
                    view.prepareApproveList(list, contactsEntity.getTOTAL());
                } else if (contactsEntity.getCODE() == 0) {//TOKEN失效
                    view.showLoginDialog(view.getContext());
                } else {
                    ToastUtil.showToast(contactsEntity.getMES());
                }
            }

            @Override
            public void onError(Throwable e) {
//                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void getMsgList() {
        model.getMsgList(new Observer<MessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.HOME_FRAGMENT_GET_MSG_DATA, d);
            }

            @Override
            public void onNext(MessageEntity contactsEntity) {
                if (contactsEntity.getCODE() == 1) {
                    List<MessageEntity.MsgBean> list = contactsEntity.getDATE();
                    view.prepareMsgList(list);
                } else if (contactsEntity.getCODE() == 0) {//TOKEN失效
                    view.showLoginDialog(view.getContext());
                } else {
                    ToastUtil.showToast(contactsEntity.getMES());
                }
            }

            @Override
            public void onError(Throwable e) {
//                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void getNoticeList() {
        model.getNoticeList(new Observer<NoticeEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.HOME_FRAGMENT_GET_NOTICE_DATA, d);
            }

            @Override
            public void onNext(NoticeEntity noticeEntity) {
                if (noticeEntity.CODE == 1) {
                    List<NoticeEntity.DATEBean> list = noticeEntity.DATE;
                    int total = 0;
                    for (int i = 0; i < list.size(); i++){
                        if (!list.get(i).readStatus.equals("1")){
                            total++;
                        }
                    }
                    view.prepareNoticeList(list, total);
                } else if (noticeEntity.CODE == 0) {//TOKEN失效
                    view.showLoginDialog(view.getContext());
                } else {
                    ToastUtil.showToast(noticeEntity.MES);
                }
            }

            @Override
            public void onError(Throwable e) {
//                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
