package com.hengkai.officeautomationsystem.function.my_unit;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.MyUnitEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/12.
 */
public class MyUnitPresenter extends BasePresenter<MyUnitActivity> {

    private final MyUnitModel model;

    public MyUnitPresenter() {
        model = new MyUnitModel();
    }

    public void getUnitList() {
        model.getUnitList(new Observer<MyUnitEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.MY_UNIT_ACTIVITY_GET_UNIT_LIST, d);
            }

            @Override
            public void onNext(MyUnitEntity myUnitEntity) {
                if (myUnitEntity.CODE == 1) {
                    List<MyUnitEntity.DATABean> data = myUnitEntity.DATA;
                    if (data != null && data.size() != 0) {
                        view.getUnitList(data);
                    } else {
                        view.noData();
                    }
                } else if (myUnitEntity.CODE == 0) {
                    view.showLoginDialog(view);
                } else {
                    ToastUtil.showToast("获取失败: " + myUnitEntity.MES);
                }
            }

            @Override
            public void onError(Throwable e) {
                view.noData();
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
