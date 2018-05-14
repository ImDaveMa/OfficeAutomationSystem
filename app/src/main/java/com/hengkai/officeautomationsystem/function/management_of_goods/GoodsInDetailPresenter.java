package com.hengkai.officeautomationsystem.function.management_of_goods;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.GoodsInDetailEntity;
import com.hengkai.officeautomationsystem.network.entity.GoodsParamsEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class GoodsInDetailPresenter extends BasePresenter<GoodsInDetailActivity> {

    private final GoodsInDetailModel model;

    public GoodsInDetailPresenter() {
        model = new GoodsInDetailModel();
    }

    public void getGoodsInDetail(int id) {
        view.showDialog();
        model.getGoodsInDetail(new Observer<GoodsInDetailEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.GOODS_IN_DETAIL_ACTIVITY_GET_DETAIL, d);
            }

            @Override
            public void onNext(GoodsInDetailEntity msgEntity) {
                if (msgEntity.getCODE() == 1) {
                    view.dismissDialog();
                    view.getSuccess(msgEntity.getInStorage());
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
