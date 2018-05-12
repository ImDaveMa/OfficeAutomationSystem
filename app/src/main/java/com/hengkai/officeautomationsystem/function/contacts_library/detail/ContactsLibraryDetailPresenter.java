package com.hengkai.officeautomationsystem.function.contacts_library.detail;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.ContactsLibraryDetailEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/12.
 */
public class ContactsLibraryDetailPresenter extends BasePresenter<ContactsLibraryDetailActivity> {

    private final ContactsLibraryDetailModel model;

    public ContactsLibraryDetailPresenter() {
        model = new ContactsLibraryDetailModel();
    }

    public void getContactsDetail(int ID) {
        model.getContactsDetail(ID, new Observer<ContactsLibraryDetailEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.CONTACTS_LIBRARY_ACTIVITY_GET_CONTACTS_LIST, d);
            }

            @Override
            public void onNext(ContactsLibraryDetailEntity contactsLibraryDetailEntity) {
                if (contactsLibraryDetailEntity.CODE == 1) {
                    view.setContactsDetail(contactsLibraryDetailEntity.DATA);
                } else if (contactsLibraryDetailEntity.CODE == 0) {
                    view.showLoginDialog(view);
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("请求网络失败");
                view.dismissDialog();
            }

            @Override
            public void onComplete() {
                view.dismissDialog();
            }
        });
    }
}
