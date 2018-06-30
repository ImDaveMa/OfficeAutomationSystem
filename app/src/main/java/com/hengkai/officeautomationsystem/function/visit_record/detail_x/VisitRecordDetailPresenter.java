package com.hengkai.officeautomationsystem.function.visit_record.detail_x;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/6/15.
 */
public class VisitRecordDetailPresenter extends BasePresenter<VisitRecordDetailActivity> {

    private final VisitRecordDetailModel model;

    public VisitRecordDetailPresenter() {
        model = new VisitRecordDetailModel();
    }

    /**
     * @param requestType          拜访的动作(visit_save, visit_start, visit_end, visit_submission
     * @param currentVisitRecordID 当前拜访的ID, 新增的时候没有这个ID, 如果是(从列表进入, 保存, 开始或者结束), 这个时候ID是有值的
     * @param companyId            拜访跟进的单位ID
     * @param type                 0：跟进 1：拜访 2：招待
     * @param projectId            拜访跟进的项目ID
     * @param contactsId           被拜访人的ID
     * @param department           被拜访人的的部门
     * @param summary              拜访总结
     * @param startLongitude       开始经度
     * @param startLatitude        开始纬度
     * @param startPhone           开始手机
     * @param startAddress         开始地址
     * @param endLongitude         结束经度
     * @param endLatitude          结束纬度
     * @param endPhone             结束手机
     * @param endAddress           结束地址
     */
    public void submissionData(@NonNull final String requestType, @Nullable String currentVisitRecordID,
                               @Nullable String companyId, @NonNull String type, @Nullable String projectId,
                               @Nullable String contactsId, @Nullable String department,
                               @Nullable String summary, @Nullable String startLongitude,
                               @Nullable String startLatitude, @Nullable String startPhone,
                               @Nullable String startAddress, @Nullable String endLongitude,
                               @Nullable String endLatitude, @Nullable String endPhone, @Nullable String endAddress) {
        model.submissionData(requestType, currentVisitRecordID, companyId, type, projectId, contactsId, department,
                summary, startLongitude, startLatitude, startPhone, startAddress, endLongitude, endLatitude,
                endPhone, endAddress, new Observer<CommonReceiveMessageEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        RxApiManager.get().add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY, d);
                    }

                    @Override
                    public void onNext(CommonReceiveMessageEntity commonReceiveMessageEntity) {
                        view.dismissDialog();
                        if (commonReceiveMessageEntity.CODE == 1) {
                            view.callbackSubmissionData(commonReceiveMessageEntity.DATA);
                            switch (requestType) {
                                case "visit_start": //开始
                                    view.setupStartButton();
                                    view.setupLinearLayout();
                                    view.setAddressState();
                                    break;
                                case "visit_end": //结束
                                    view.setupEndButton();
                                    view.setAddressState();
                                    break;
                                case "visit_save": //保存
                                    ToastUtil.showToast("保存成功");
                                    break;
                                case "visit_submission": //提交
                                    ToastUtil.showToast("操作成功");
                                    view.setResult(CommonFinal.VISIT_RECORD_RESULT_CODE);
                                    view.finish();
                                    break;
                            }
                        } else if (commonReceiveMessageEntity.CODE == 0) {
                            view.showLoginDialog(view);
                        } else {
                            //传入ID为空
                            ToastUtil.showToast(commonReceiveMessageEntity.MES);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.dismissDialog();
                        ToastUtil.showToast("网络连接错误");
                    }

                    @Override
                    public void onComplete() {
                        view.dismissDialog();
                    }
                });
    }

    /**
     * 从列表item进入到当前页面查看之前保存的
     */
    public void getVisitRecordDetail(int currentVisitRecordID) {
        model.getVisitRecordDetail(currentVisitRecordID, new Observer<VisitRecordDetailEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_GET, d);
            }

            @Override
            public void onNext(VisitRecordDetailEntity visitRecordDetailEntity) {
                if (visitRecordDetailEntity.CODE == 1) {
                    view.setupCurrentPage(visitRecordDetailEntity.DATA);
                } else if (visitRecordDetailEntity.CODE == 0) {
                    view.showLoginDialog(view);
                } else {
                    //传入ID为空
                    ToastUtil.showToast(visitRecordDetailEntity.MES);
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("网络连接错误");
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
