package com.hengkai.officeautomationsystem.function.contacts_library;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.ContactsLibraryEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/11.
 */
public class ContactsLibraryPresenter extends BasePresenter<ContactsLibraryActivity> {

    private final ContactsLibraryModel model;

    public ContactsLibraryPresenter() {
        model = new ContactsLibraryModel();
    }

    public void getContactsList(final int ID, int unitID) {
        model.getContactsList(ID, unitID, new Observer<ContactsLibraryEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.CONTACTS_LIBRARY_ACTIVITY_GET_CONTACTS_LIST, d);
            }

            @Override
            public void onNext(ContactsLibraryEntity contactsLibraryEntity) {
                if (contactsLibraryEntity.CODE == 1) {
                    List<ContactsLibraryEntity.DATABean> data = contactsLibraryEntity.DATA;
                    if (ID == 0 && (data == null || data.size() == 0)) {
                        view.noData();
                    } else {
                        if (data != null && data.size() != 0) {
                            view.getContactsList(data);
                        } else {
                            ToastUtil.showToast("没有更多数据了");
                        }
                    }
                } else if (contactsLibraryEntity.CODE == 0) {
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast("获取失败: " + contactsLibraryEntity.MES);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (ID == 0) {
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
}
