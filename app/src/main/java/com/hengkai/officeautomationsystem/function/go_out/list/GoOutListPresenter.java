package com.hengkai.officeautomationsystem.function.go_out.list;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.GetAskForLeaveListEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/22.
 */
public class GoOutListPresenter extends BasePresenter<GoOutListActivity> {

    private final GoOutListModel model;

    public GoOutListPresenter() {
        model = new GoOutListModel();
    }

    public void getAskForLeaveList(final int searchID) {
        model.getAskForLeaveList(searchID, new Observer<GetAskForLeaveListEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.ASK_FOR_LEAVE_LIST_ACTIVITY_GET_LIST, d);
            }

            @Override
            public void onNext(GetAskForLeaveListEntity getAskForLeaveListEntity) {
                switch (getAskForLeaveListEntity.CODE) {
                    case 1:
                        List<GetAskForLeaveListEntity.DATABean> data = getAskForLeaveListEntity.DATA;
                        if (searchID == 0 && (data == null || data.size() == 0)) {
                            view.noData();
                        } else {
                            if (data != null && data.size() != 0) {
                                view.getAskForLeaveList(data);
                            } else {
                                ToastUtil.showToast("没有更多数据了");
                            }
                        }
                        break;
                    case 0:
                        view.showLoginDialog(view);
                        break;
                    default:
                        ToastUtil.showToast("获取失败: " + getAskForLeaveListEntity.MES);
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                if (searchID == 0) {
                    view.noData();
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
