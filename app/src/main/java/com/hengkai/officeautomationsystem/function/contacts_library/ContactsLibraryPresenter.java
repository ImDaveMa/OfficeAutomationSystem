package com.hengkai.officeautomationsystem.function.contacts_library;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.ContactsLibraryEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

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

    public void getContactsList(int ID) {
        model.getContactsList(ID, new Observer<ContactsLibraryEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.CONTACTS_LIBRARY_ACTIVITY_GET_CONTACTS_LIST, d);
            }

            @Override
            public void onNext(ContactsLibraryEntity contactsLibraryEntity) {
                if (contactsLibraryEntity.CODE == 1) {
                    view.getContactsList(contactsLibraryEntity.DATA);
                } else if (contactsLibraryEntity.CODE == 0) {
                    view.showLoginDialog(view);
                }
            }

            @Override
            public void onError(Throwable e) {
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
