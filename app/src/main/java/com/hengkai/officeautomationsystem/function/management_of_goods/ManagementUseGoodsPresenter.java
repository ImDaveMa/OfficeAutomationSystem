package com.hengkai.officeautomationsystem.function.management_of_goods;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.GoodsInEntity;
import com.hengkai.officeautomationsystem.network.entity.UseGoodsEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ManagementUseGoodsPresenter extends BasePresenter<ManagementUseGoodsActivity> {

    private final ManagementUseGoodsModel model;

    public ManagementUseGoodsPresenter() {
        model = new ManagementUseGoodsModel();
    }

    public void getUseGoodsList(int id) {
        model.getUseGoodsList(new Observer<UseGoodsEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.MANAGEMENT_USE_GOODS_ACTIVITY_GET_USE_GOODS_LIST, d);
            }

            @Override
            public void onNext(UseGoodsEntity goodsOutEntity) {
                view.stopRefreshing();
                if (goodsOutEntity.getCODE() == 1) {
                    List<UseGoodsEntity.OutStorageBean> list = goodsOutEntity.getOutStorage();
                    view.prepareData(list);
                } else if (goodsOutEntity.getCODE() == -2) {
                    // 返回的数据是空，所以不能处理列表
                } else if (goodsOutEntity.getCODE() == 0) {//TOKEN失效
                    view.showLoginDialog(view);
                }
            }

            @Override
            public void onError(Throwable e) {
                view.stopRefreshing();
                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {

            }
        }, id);

    }
}