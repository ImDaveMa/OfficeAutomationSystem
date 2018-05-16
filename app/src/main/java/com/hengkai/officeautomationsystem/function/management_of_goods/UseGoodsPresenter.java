package com.hengkai.officeautomationsystem.function.management_of_goods;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.GoodsEntity;
import com.hengkai.officeautomationsystem.network.entity.GoodsParamsEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UseGoodsPresenter extends BasePresenter<UseGoodsActivity> {

    private final UseGoodsModel model;

    public UseGoodsPresenter() {
        model = new UseGoodsModel();
    }

    public void saveGoods(double price, String reason, String details, int projectId) {
        view.showDialog();
        model.saveGoods(new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.USE_GOODS_ACTIVITY_SAVE_GOODS, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity msgEntity) {
                if (msgEntity.CODE == 1) {
                    view.dismissDialog();
                    view.submitSuccess();
                } else if (msgEntity.CODE == 3) {
                    view.dismissDialog();
                    ToastUtil.showToast("系统异常，申请提交失败");
                } else if (msgEntity.CODE == 0) {//TOKEN失效
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
        }, price, reason, details, projectId);
    }

    public void getParams() {
        view.showDialog();
        model.getParams(new Observer<GoodsParamsEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.USE_GOODS_ACTIVITY_GET_GOODS_PARAMS, d);
            }

            @Override
            public void onNext(GoodsParamsEntity goodsParamsEntity) {
                if (goodsParamsEntity.getCODE() == 1) {
                    view.dismissDialog();
                    view.getParamsSuccess(goodsParamsEntity);
                } else if (goodsParamsEntity.getCODE() == 3) {
                    view.dismissDialog();
                    ToastUtil.showToast("系统异常，获取参数失败");
                    view.finish();
                } else if (goodsParamsEntity.getCODE() == 0) {//TOKEN失效
                    view.dismissDialog();
                    ToastUtil.showToast("登录失效，请重新登录");
                    view.showLoginDialog(view);
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
