package com.hengkai.officeautomationsystem.function.goods_in;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.GoodsInEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ManagementGoodsInPresenter extends BasePresenter<ManagementGoodsInActivity> {

    private final ManagementGoodsInModel model;

    public ManagementGoodsInPresenter() {
        model = new ManagementGoodsInModel();
    }

    public void getGoodsInList(final int id) {
        model.getGoodsList(new Observer<GoodsInEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.MANAGEMENT_GOODS_IN_ACTIVITY_GET_GOODS_LIST, d);
            }

            @Override
            public void onNext(GoodsInEntity goodsInEntity) {
                view.stopRefreshing();
                if (goodsInEntity.getCODE() == 1) {
                    List<GoodsInEntity.InStorageBean> beans = goodsInEntity.getInStorage();
                    if(id == 0 && (beans == null || beans.size() <= 0)){
                        view.noData();
                    } else {
                        view.prepareData(beans);
                    }
                } else if (goodsInEntity.getCODE() == 0) {//TOKEN失效
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(goodsInEntity.getMES());
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
