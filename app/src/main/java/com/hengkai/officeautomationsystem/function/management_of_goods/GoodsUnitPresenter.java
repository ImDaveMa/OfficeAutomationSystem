package com.hengkai.officeautomationsystem.function.management_of_goods;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
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
            public void onNext(GoodsUnitEntity unitEntity) {
                view.stopRefreshing();
                if (unitEntity.getCODE() == 1) {
                    List<GoodsUnitEntity.UnitBean> list = unitEntity.getList();
                    view.prepareData(list);
                } else if (unitEntity.getCODE() == -2) {
                    // 返回的数据是空，所以不能处理列表
                } else if (unitEntity.getCODE() == 0) {//TOKEN失效
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