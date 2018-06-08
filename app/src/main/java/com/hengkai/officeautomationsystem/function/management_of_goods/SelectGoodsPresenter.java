package com.hengkai.officeautomationsystem.function.management_of_goods;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.GoodsEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SelectGoodsPresenter extends BasePresenter<SelectGoodsActivity> {

    private final ManagementOfGoodsModel model;

    public SelectGoodsPresenter() {
        model = new ManagementOfGoodsModel();
    }

    public void getGoodsList(final int id) {
        model.getGoodsList(new Observer<GoodsEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.MANAGEMENT_OF_GOODS_ACTIVITY_GET_GOODS_LIST, d);
            }

            @Override
            public void onNext(GoodsEntity goodsEntity) {
                view.stopRefreshing();
                if (goodsEntity.getCODE() == 1) {
                    List<GoodsEntity.GoodsBean> beans = goodsEntity.getList();
                    if(id == 0 && (beans == null || beans.size() <= 0)){
                        view.noData();
                    } else {
                        if(beans != null && beans.size() > 0){
                            view.prepareData(beans);
                        } else {
                            ToastUtil.showToast("没有更多数据");
                        }
                    }
                } else if (goodsEntity.getCODE() == 0) {//TOKEN失效
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(goodsEntity.getMES());
                }
            }

            @Override
            public void onError(Throwable e) {
                view.stopRefreshing();
                if(id == 0){
                    view.noData();
                }
                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {

            }
        }, id);

    }
}
