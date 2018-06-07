package com.hengkai.officeautomationsystem.function.goods_unit;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
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
                    List<GoodsUnitEntity.UnitBean> beans = unitEntity.getList();
                    if(beans == null || beans.size() <= 0){
                        view.noData();
                    } else {
                        view.prepareData(beans);
                    }
                } else if (unitEntity.getCODE() == 0) {//TOKEN失效
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(unitEntity.getMES());
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

    public void addGoodsUnit(String name) {
        view.showDialog();
        model.addGoodsUnit(new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.GOODS_UNIT_ACTIVITY_ADD, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity entity) {
                view.dismissDialog();
                if (entity.CODE == 1) {
                    view.addSuccess();
                } else if (entity.CODE == 0) {//TOKEN失效
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(entity.MES);
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
        }, name);
    }

    public void deleteGoodsUnit(int id, final int position) {
        view.showDialog();
        model.deleteGoodsUnit(new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.GOODS_UNIT_ACTIVITY_DELETE, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity entity) {
                view.dismissDialog();
                if (entity.CODE == 1) {
                    view.deleteSuccess(position);
                } else if (entity.CODE == 0) {//TOKEN失效
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(entity.MES);
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
