package com.hengkai.officeautomationsystem.function.goods_in;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.GoodsParamsEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class GoodsInPresenter extends BasePresenter<GoodsInActivity> {

    private final GoodsInModel model;

    public GoodsInPresenter() {
        model = new GoodsInModel();
    }

    public void saveGoodsIn(double price, String reason, String details) {
        view.showDialog();
        model.saveGoodsIn(new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.GOODS_IN_ACTIVITY_SAVE_GOODS_IN, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity msgEntity) {
                view.dismissDialog();
                if (msgEntity.CODE == 1) {
                    view.submitSuccess();
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

            }
        }, price, reason, details);
    }

    public void getParams() {
        view.showDialog();
        model.getParams(new Observer<GoodsParamsEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.GOODS_IN_ACTIVITY_GET_GOODS_PARAMS, d);
            }

            @Override
            public void onNext(GoodsParamsEntity goodsParamsEntity) {
                view.dismissDialog();
                if (goodsParamsEntity.getCODE() == 1) {
                    view.getParamsSuccess(goodsParamsEntity);
                } else if (goodsParamsEntity.getCODE() == 0) {//TOKEN失效
                    ToastUtil.showToast("登录失效，请重新登录");
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(goodsParamsEntity.getMES());
                }
            }

            @Override
            public void onError(Throwable e) {
                view.dismissDialog();
                ToastUtil.showToast("请求网络失败");
                view.finish();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
