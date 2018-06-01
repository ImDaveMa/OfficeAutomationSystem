package com.hengkai.officeautomationsystem.function.visit_record.detail;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailEntity;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailGetVisitUnitEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by Harry on 2018/5/8.
 */
public class VisitRecordDetailActivityPresenter extends BasePresenter<VisitRecordDetailActivity> {

    private final VisitRecordDetailActivityModel model;
    /**
     * 当前页面保存或者开始后, 获取到的ID, 用于结束拜访的传值
     */
    private int currentVisitRecordID;

    public VisitRecordDetailActivityPresenter() {
        model = new VisitRecordDetailActivityModel();
    }

//    public void getVisitCustomerList(int unitID) {
//        model.getVisitCustomerList(unitID, new Observer<VisitRecordDetailGetVisitUnitEntity>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                RxApiManager.get().add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_GET_VISIT_UNIT_CUSTOMER_LIST, d);
//            }
//
//            @Override
//            public void onNext(VisitRecordDetailGetVisitUnitEntity visitRecordDetailGetVisitUnitEntity) {
//                if (visitRecordDetailGetVisitUnitEntity.CODE == 1) {
//                    view.getVisitCustomerList(visitRecordDetailGetVisitUnitEntity.DATA);
//                } else if (visitRecordDetailGetVisitUnitEntity.CODE == 0) {
//                    view.showLoginDialog(view);
//                } else {
//                    //code=-2
//                    ToastUtil.showToast("暂无相关联系人");
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                view.dismissDialog();
//                ToastUtil.showToast("网络连接错误");
//            }
//
//            @Override
//            public void onComplete() {
//                view.dismissDialog();
//            }
//        });
//    }

    public void getVisitProjectList(String customerID) {
        model.getVisitProjectList(customerID, new Observer<VisitRecordDetailGetVisitUnitEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_GET_VISIT_UNIT_PROJECT_LIST, d);
            }

            @Override
            public void onNext(VisitRecordDetailGetVisitUnitEntity visitRecordDetailGetVisitUnitEntity) {
                if (visitRecordDetailGetVisitUnitEntity.CODE == 1) {
                    view.getVisitProjectList(visitRecordDetailGetVisitUnitEntity.DATA);
                } else if (visitRecordDetailGetVisitUnitEntity.CODE == 0) {
                    view.showLoginDialog(view);
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
     * 新增开始
     */
    public void toStartCurrentPageOnNewAdd(Map<String, String> params) {
        model.toSaveCurrentPageOnNewAdd(params, new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_TO_SAVE_ON_NEW_ADD, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity commonReceiveMessageEntity) {
                switch (commonReceiveMessageEntity.CODE) {
                    case 1:
                        currentVisitRecordID = commonReceiveMessageEntity.DATA;
                        view.setNewState(commonReceiveMessageEntity.DATA);
                        view.setupStartButton();
                        view.setupLinearLayout();
                        break;
                    case -1:
                        ToastUtil.showToast("服务器异常");
                        break;
                    case -2:
                        ToastUtil.showToast("信息已提交，禁止修改");
                        break;
                    case -3:
                        ToastUtil.showToast("单位名称不能为空");
                        break;
                    case -4:
                        ToastUtil.showToast("数据参数不完整");
                        break;

                    default:
                        break;
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
     * 新增保存
     */
    public void toSaveCurrentPageOnNewAdd(Map<String, String> params) {
        model.toSaveCurrentPageOnNewAdd(params, new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_TO_SAVE_ON_NEW_ADD, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity commonReceiveMessageEntity) {
                switch (commonReceiveMessageEntity.CODE) {
                    case 1:
                        ToastUtil.showToast("保存成功");
                        currentVisitRecordID = commonReceiveMessageEntity.DATA;
                        view.setNewState(commonReceiveMessageEntity.DATA);
                        break;
                    case -1:
                        ToastUtil.showToast("服务器异常");
                        break;
                    case -2:
                        ToastUtil.showToast("信息已提交，禁止修改");
                        break;
                    case -3:
                        ToastUtil.showToast("单位名称不能为空");
                        break;
                    case -4:
                        ToastUtil.showToast("数据参数不完整");
                        break;

                    default:
                        break;
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
     * 从列表item进入到当前页面的
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
                    ToastUtil.showToast("获取信息出错了");
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

    /**
     * 列表项进入点击开始拜访按钮
     */
    public void toStart(Map<String, String> params) {
        model.toEnd(params, new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_TO_END, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity commonReceiveMessageEntity) {
                switch (commonReceiveMessageEntity.CODE) {
                    case 1:
                        ToastUtil.showToast("操作成功");
                        view.setupStartButton();
                        view.setupLinearLayout();
                        break;
                    case 2:
                        ToastUtil.showToast("服务器繁忙，请稍后重试");
                        break;
                    case -2:
                        ToastUtil.showToast("该数据已提交 （提交）");
                        break;
                    case -3:
                        ToastUtil.showToast("数据已提交，禁止修改 (开始结束)");
                        break;
                    default:
                        break;
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
     * 结束
     */
    public void toEnd(Map<String, String> params) {
        if (params.get("ID").equals("-1")) {//如果为-1 则说明是从新增进入到当前页面, 并点击结束的
            params.put("ID", String.valueOf(currentVisitRecordID));
        }
        model.toEnd(params, new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_TO_END, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity commonReceiveMessageEntity) {
                switch (commonReceiveMessageEntity.CODE) {
                    case 1:
                        view.setupEndButton();
                        break;
                    case 2:
                        ToastUtil.showToast("服务器繁忙，请稍后重试");
                        break;
                    case -2:
                        ToastUtil.showToast("该数据已提交 （提交）");
                        break;
                    case -3:
                        ToastUtil.showToast("数据已提交，禁止修改 (开始结束)");
                        break;
                    default:
                        break;
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
     * 保存或者提交
     */
    public void toSaveOrCommit(final boolean isCommit, Map<String, String> params) {
        if (params.get("ID").equals("-1")) {//如果为-1 则说明是从新增进入到当前页面, 并点击结束的
            params.put("ID", String.valueOf(currentVisitRecordID));
        }
        model.toSaveOrCommit(params, new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.VISIT_RECORD_DETAIL_ACTIVITY_TO_END, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity commonReceiveMessageEntity) {
                switch (commonReceiveMessageEntity.CODE) {
                    case 1:
                        ToastUtil.showToast("操作成功");
                        if (isCommit) {
                            view.setResult(CommonFinal.VISIT_RECORD_RESULT_CODE);
                            view.finish();
                        }
                        break;
                    case 2:
                        ToastUtil.showToast("服务器繁忙，请稍后重试");
                        break;
                    case -1:
                        ToastUtil.showToast("拜访还未开始 （结束）");
                        break;
                    case -2:
                        ToastUtil.showToast("该数据已提交 （提交）");
                        break;
                    case -3:
                        ToastUtil.showToast("数据已提交，禁止修改 (开始结束)");
                        break;
                    case 0:
                        view.showLoginDialog(view);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("网络连接错误");
                view.dismissDialog();
            }

            @Override
            public void onComplete() {
                view.dismissDialog();
            }
        });
    }

}
