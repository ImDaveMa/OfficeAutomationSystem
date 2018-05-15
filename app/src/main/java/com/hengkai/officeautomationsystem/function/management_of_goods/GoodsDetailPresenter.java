package com.hengkai.officeautomationsystem.function.management_of_goods;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.GoodsDetailEntity;
import com.hengkai.officeautomationsystem.network.entity.GoodsInDetailEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class GoodsDetailPresenter extends BasePresenter<GoodsDetailActivity> {

    private final GoodsDetailModel model;

    public GoodsDetailPresenter() {
        model = new GoodsDetailModel();
    }

    public void getGoodsDetail(int id) {
        view.showDialog();
        model.getGoodsDetail(new Observer<GoodsDetailEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.GOODS_DETAIL_ACTIVITY_GET_DETAIL, d);
            }

            @Override
            public void onNext(GoodsDetailEntity msgEntity) {
                if (msgEntity.getCODE() == 1) {
                    view.dismissDialog();
                    view.getSuccess(msgEntity.getList().get(0));
                } else if (msgEntity.getCODE() == 3) {
                    view.dismissDialog();
                    ToastUtil.showToast("系统异常，获取内容失败");
                } else if (msgEntity.getCODE() == 0) {//TOKEN失效
                    view.dismissDialog();
                    ToastUtil.showToast("登录失效，请重新登录");
                    view.showLoginDialog(view);
                }
            }

            @Override
            public void onError(Throwable e) {
                view.dismissDialog();
                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {

            }
        }, id);
    }

}
