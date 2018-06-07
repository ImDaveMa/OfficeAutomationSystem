package com.hengkai.officeautomationsystem.function.goods_supplier;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.GoodsSupplierEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class GoodsSupplierPresenter extends BasePresenter<GoodsSupplierActivity> {

    private final GoodsSupplierModel model;

    public GoodsSupplierPresenter() {
        model = new GoodsSupplierModel();
    }

    public void getGoodsSupplierList(String searchName, int searchType, int searchCity) {
        model.getGoodsSupplierList(new Observer<GoodsSupplierEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.GOODS_SUPPLIER_ACTIVITY_LIST, d);
            }

            @Override
            public void onNext(GoodsSupplierEntity goodsSupplierEntity) {
                view.stopRefreshing();
                if (goodsSupplierEntity.getCODE() == 1) {
                    List<GoodsSupplierEntity.SupplierBean> beans = goodsSupplierEntity.getSupplier();
                    if(beans == null || beans.size() <= 0){
                        view.noData();
                    } else {
                        List<GoodsSupplierEntity.ParamBean> paramList = goodsSupplierEntity.getParam();
                        view.prepareData(beans, paramList);
                    }
                } else if (goodsSupplierEntity.getCODE() == 0) {//TOKEN失效
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(goodsSupplierEntity.getMES());
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
        }, searchName, searchType, searchCity);
    }
}
