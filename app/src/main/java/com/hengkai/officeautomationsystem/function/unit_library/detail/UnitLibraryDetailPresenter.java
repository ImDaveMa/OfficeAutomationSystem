package com.hengkai.officeautomationsystem.function.unit_library.detail;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.UnitLibraryDetailEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/12.
 */
public class UnitLibraryDetailPresenter extends BasePresenter<UnitLibraryDetailActivity> {

    private final UnitLibraryDetailModel model;

    public UnitLibraryDetailPresenter() {
        model = new UnitLibraryDetailModel();
    }

    public void getUnitDetail(int ID) {
        model.getUnitDetail(ID, new Observer<UnitLibraryDetailEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.UNIT_LIBRARY_DETAIL_ACTIVITY_GET_UNIT_DETAIL, d);
            }

            @Override
            public void onNext(UnitLibraryDetailEntity unitLibraryDetailEntity) {
                if (unitLibraryDetailEntity.CODE == 1) {
                    view.setUnitDetail(unitLibraryDetailEntity.DATA);
                } else if (unitLibraryDetailEntity.CODE == 0) {
                    view.showLoginDialog(view);
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("请求网络失败");
                view.dismissDialog();
            }

            @Override
            public void onComplete() {
                view.dismissDialog();
            }
        });
    }
}
