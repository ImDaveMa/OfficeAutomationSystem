package com.hengkai.officeautomationsystem.function.goods_unit;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.management_of_goods.ManagementOfGoodsActivity;
import com.hengkai.officeautomationsystem.function.management_of_goods.ManagementOfGoodsModel;
import com.hengkai.officeautomationsystem.network.entity.GoodsEntity;
import com.hengkai.officeautomationsystem.network.entity.GoodsUnitEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class GoodsUnitPresenter extends BasePresenter<GoodsUnitActivity> {

    private final GoodsUnitModel model;

    public GoodsUnitPresenter() {
        model = new GoodsUnitModel();
    }

    public void getGoodsUnitList() {
        model.getGoodsUnitList(new Observer<GoodsUnitEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.GOODS_UNIT_ACTIVITY_LIST, d);
            }

            @Override
            public void onNext(GoodsUnitEntity goodsEntity) {
                view.stopRefreshing();
                if (goodsEntity.getCODE() == 1) {
                    List<GoodsUnitEntity.UnitBean> list = goodsEntity.getList();
                    view.prepareData(list);
                } else if (goodsEntity.getCODE() == -2) {
                    List<GoodsUnitEntity.UnitBean> list = goodsEntity.getList();
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
        });
    }
}
