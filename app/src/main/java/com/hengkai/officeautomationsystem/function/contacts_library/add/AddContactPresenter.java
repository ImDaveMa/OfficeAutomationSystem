package com.hengkai.officeautomationsystem.function.contacts_library.add;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.goods_supplier.AddGoodsSupplierActivity;
import com.hengkai.officeautomationsystem.function.goods_supplier.AddGoodsSupplierModel;
import com.hengkai.officeautomationsystem.network.entity.AddGoodsSupplierEntity;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.EditGoodsSupplierEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class AddContactPresenter extends BasePresenter<AddContactActivity> {

    private final AddContactModel model;

    public AddContactPresenter() {
        model = new AddContactModel();
    }

    public void saveContact(String name, String phone, int companyId, String birthday, boolean sex,
                            String department, String rank, String weChat, String landline, String mailBox,
                            String hobby, String licensePlate) {
        view.showDialog();
        model.saveContact(name, phone, companyId, birthday, sex, department, rank, weChat, landline, mailBox,
                hobby, licensePlate, new Observer<CommonReceiveMessageEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        RxApiManager.get().add(NetworkTagFinal.ADD_CONTACT_ACTIVITY_ADD_CONTACT, d);
                    }

                    @Override
                    public void onNext(CommonReceiveMessageEntity msgEntity) {
                        if (msgEntity.CODE == 1) {
                            view.addSuccess();
                        } else if (msgEntity.CODE == 0) {//TOKEN失效
                            ToastUtil.showToast("登录失效，请重新登录");
                            view.showLoginDialog(view);
                        } else {
                            ToastUtil.showToast(msgEntity.MES);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                        ToastUtil.showToast("请求网络失败");
                    }

                    @Override
                    public void onComplete() {
                        view.dismissDialog();
                    }
                });
    }
}
