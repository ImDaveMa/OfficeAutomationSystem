package com.hengkai.officeautomationsystem.function.management_of_goods;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.GoodsEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ManagementOfGoodsPresenter extends BasePresenter<ManagementOfGoodsActivity> {

    private final ManagementOfGoodsModel model;

    public ManagementOfGoodsPresenter() {
        model = new ManagementOfGoodsModel();
    }

    public void getGoodsList(int id) {
        model.getGoodsList(new Observer<GoodsEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.MANAGEMENT_OF_GOODS_ACTIVITY_GET_GOODS_LIST, d);
            }

            @Override
            public void onNext(GoodsEntity goodsEntity) {
                view.stopRefreshing();
                if (goodsEntity.getCODE() == 1) {
                    List<GoodsEntity.GoodsBean> list = goodsEntity.getList();
                    view.prepareData(list);
                    view.initListChildClickListener(list);
                } else if (goodsEntity.getCODE() == -2) {
                    List<GoodsEntity.GoodsBean> list = goodsEntity.getList();
                    view.prepareData(list);
                } else if (goodsEntity.getCODE() == 0) {//TOKEN失效
                    view.showLoginDialog(view);
                }
            }

            @Override
            public void onError(Throwable e) {
                view.stopRefreshing();
                ToastUtil.showToast("请求网络失败:" + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }, id);
    }
}
