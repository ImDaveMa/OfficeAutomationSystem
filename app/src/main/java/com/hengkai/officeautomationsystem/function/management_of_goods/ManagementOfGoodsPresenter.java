package com.hengkai.officeautomationsystem.function.management_of_goods;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
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
                } else if (goodsEntity.getCODE() == -2) {
                    // 返回的数据是空，所以不能处理列表
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

    public void deleteGoodsUnit(int id, final int position) {
        view.showDialog();
        model.deleteGoodsUnit(new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.MANAGEMENT_OF_GOODS_ACTIVITY_DELETE, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity entity) {
                view.dismissDialog();
                if (entity.CODE == 1) {
                    view.deleteSuccess(position);
                } else if (entity.CODE == -2 || entity.CODE == 3) {
                    ToastUtil.showToast(entity.MES);
                } else if (entity.CODE == 0) {//TOKEN失效
                    view.showLoginDialog(view);
                }
            }

            @Override
            public void onError(Throwable e) {
                view.dismissDialog();
                ToastUtil.showToast("请求网络失败:" + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        }, id);
    }
}
