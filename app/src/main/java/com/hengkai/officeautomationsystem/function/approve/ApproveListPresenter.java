package com.hengkai.officeautomationsystem.function.approve;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.function.goods_out.ManagementUseGoodsActivity;
import com.hengkai.officeautomationsystem.function.goods_out.ManagementUseGoodsModel;
import com.hengkai.officeautomationsystem.network.entity.MessageEntity;
import com.hengkai.officeautomationsystem.network.entity.UseGoodsEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ApproveListPresenter extends BasePresenter<ApproveListActivity> {

    private final ApproveListModel model;

    public ApproveListPresenter() {
        model = new ApproveListModel();
    }

    public void getApproveList(int id, int operating, int searchDay) {
        model.getApproveList(new Observer<MessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.APPROVE_LIST_ACTIVITY_GET_LIST, d);
            }

            @Override
            public void onNext(MessageEntity msgEntity) {
                view.stopRefreshing();
                if (msgEntity.getCODE() == 1) {
                    view.prepareData(msgEntity.getDATE());
                } else if (msgEntity.getCODE() == -2) {
                    // 返回的数据是空，所以不能处理列表
                } else if (msgEntity.getCODE() == 0) {//TOKEN失效
                    view.showLoginDialog(view);
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
        }, id, operating, searchDay);

    }
}
