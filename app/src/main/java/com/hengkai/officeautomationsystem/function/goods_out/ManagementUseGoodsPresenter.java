package com.hengkai.officeautomationsystem.function.goods_out;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
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

    public void getUseGoodsList(final int id) {
        model.getUseGoodsList(new Observer<UseGoodsEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.MANAGEMENT_USE_GOODS_ACTIVITY_GET_USE_GOODS_LIST, d);
            }

            @Override
            public void onNext(UseGoodsEntity goodsOutEntity) {
                view.stopRefreshing();
                if (goodsOutEntity.getCODE() == 1) {
                    List<UseGoodsEntity.OutStorageBean> beans = goodsOutEntity.getOutStorage();
                    if(id == 0 && (beans == null || beans.size() <= 0)){
                        view.noData();
                    } else {
                        if(beans != null && beans.size() > 0){
                            view.prepareData(beans);
                        } else {
                            ToastUtil.showToast("没有更多数据");
                        }
                    }
                } else if (goodsOutEntity.getCODE() == 0) {//TOKEN失效
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(goodsOutEntity.getMES());
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
