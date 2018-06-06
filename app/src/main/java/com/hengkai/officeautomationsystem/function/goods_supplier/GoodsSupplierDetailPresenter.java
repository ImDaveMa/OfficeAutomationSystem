package com.hengkai.officeautomationsystem.function.goods_supplier;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.project_library.detail.ProjectLibraryDetailActivity;
import com.hengkai.officeautomationsystem.function.project_library.detail.ProjectLibraryDetailModel;
import com.hengkai.officeautomationsystem.network.entity.GoodsSupplierDetailEntity;
import com.hengkai.officeautomationsystem.network.entity.ProjectLibraryDetailEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/17.
 */
public class GoodsSupplierDetailPresenter extends BasePresenter<GoodsSupplierDetailActivity> {

    private final GoodsSupplierDetailModel model;

    public GoodsSupplierDetailPresenter() {
        model = new GoodsSupplierDetailModel();
    }

    public void getGoodsSupplier(int id) {
        view.showDialog();
        model.getGoodsSupplier(id, new Observer<GoodsSupplierDetailEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.GOODS_SUPPLIER_DETAIL_ACTIVITY_GET_DETAIL, d);
            }

            @Override
            public void onNext(GoodsSupplierDetailEntity msgEntity) {
                view.dismissDialog();
                //-1：缺少参数 1：查询成功 2：查询结果为空 3：操作失败
                switch (msgEntity.CODE) {
                    case 1:
                        view.bindView(msgEntity.supplier);
                        break;
                    case 0: // TOKEN 失效
                        view.showLoginDialog(view);
                        break;
                    default:
                        ToastUtil.showToast(msgEntity.MES);
                        break;
                }

            }

            @Override
            public void onError(Throwable e) {
                view.dismissDialog();
                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
