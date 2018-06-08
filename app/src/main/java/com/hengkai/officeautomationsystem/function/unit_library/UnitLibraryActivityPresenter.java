package com.hengkai.officeautomationsystem.function.unit_library;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.UnitLibraryEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/5.
 */
public class UnitLibraryActivityPresenter extends BasePresenter<UnitLibraryActivity> {

    private final UnitLibraryActivityModel model;

    public UnitLibraryActivityPresenter() {
        model = new UnitLibraryActivityModel();
    }

    public void getUnitList(final int ID) {
        model.getUnitList(ID, new Observer<UnitLibraryEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.UNIT_LIBRARY_ACTIVITY_GET_UNIT_LIST, d);
            }

            @Override
            public void onNext(UnitLibraryEntity unitLibraryEntity) {
                if (unitLibraryEntity.CODE == 1) {
                    List<UnitLibraryEntity.DATABean> data = unitLibraryEntity.DATA;
                    if (ID == 0 && (data == null || data.size() == 0)) {
                        view.noData(2);
                    } else {
                        if (data != null && data.size() != 0) {
                            view.getUnitList(unitLibraryEntity.DATA);
                        } else {
                            ToastUtil.showToast("没有更多数据了");
                        }
                    }
                } else if (unitLibraryEntity.CODE == 0) {
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast(unitLibraryEntity.MES);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (ID == 0) {
                    view.noData(2);
                }
                ToastUtil.showToast("请求网络失败");
                view.stopRefreshing();
            }

            @Override
            public void onComplete() {
                view.stopRefreshing();
            }
        });
    }
}
