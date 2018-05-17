package com.hengkai.officeautomationsystem.function.goods_out;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.GoodsOutDetailEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class UseGoodsDetailPresenter extends BasePresenter<UseGoodsDetailActivity> {

    private final UseGoodsDetailModel model;

    public UseGoodsDetailPresenter() {
        model = new UseGoodsDetailModel();
    }

    public void getUseGoodsDetail(int id) {
        view.showDialog();
        model.getUseGoodsDetail(new Observer<GoodsOutDetailEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.USE_GOODS_DETAIL_ACTIVITY_GET_DETAIL, d);
            }

            @Override
            public void onNext(GoodsOutDetailEntity msgEntity) {
                if (msgEntity.getCODE() == 1) {
                    view.dismissDialog();
                    view.getSuccess(msgEntity.getOutStorage());
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
