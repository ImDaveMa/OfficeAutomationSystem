package com.hengkai.officeautomationsystem.function.report.add;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.ReportContactsEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/19.
 */
public class GroupMemberPresenter extends BasePresenter<GroupMemberActivity> {

    private final GroupMemberModel model;

    public GroupMemberPresenter() {
        model = new GroupMemberModel();
    }

    public void getGroupMemberList() {
        model.getGroupMemberList(new Observer<ReportContactsEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.GROUP_MEMBER_ACTIVITY_GET_GROUP_MEMBER_LIST, d);
            }

            @Override
            public void onNext(ReportContactsEntity reportContactsEntity) {
                switch (reportContactsEntity.CODE) {
                    case 1:
                        view.getGroupMemberList(reportContactsEntity.DATE);
                        break;
                    case 0:
                        view.showLoginDialog(view);
                        break;
                    case -2:
                        ToastUtil.showToast("没有更多数据了");
                        break;
                    default:
                        ToastUtil.showToast(reportContactsEntity.MES);
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
