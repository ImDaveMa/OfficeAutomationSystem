package com.hengkai.officeautomationsystem.function.go_out;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.DurationEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/22.
 */
public class GoOutPresenter extends BasePresenter<GoOutActivity> {

    private final GoOutModel model;

    public GoOutPresenter() {
        model = new GoOutModel();
    }

    public void add(Map<String, String> params) {
        model.add(params, new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.ASK_FOR_LEAVE_ACTIVITY_ADD, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity commonReceiveMessageEntity) {
                switch (commonReceiveMessageEntity.CODE) {
                    case 1:
                        ToastUtil.showToast("操作成功");
                        view.finish();
                        break;
                    case 0:
                        view.showLoginDialog(view);
                        break;
                    case -1:
                        ToastUtil.showToast("服务器异常");
                        break;
                    case -2:
                        ToastUtil.showToast("时间参数格式错误");
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
                ToastUtil.showToast("请求网络失败");
                view.dismissDialog();
            }

            @Override
            public void onComplete() {
                view.dismissDialog();
            }
        });
    }

    public void duration(String startTime, String endTime) {
        model.duration(startTime, endTime, new Observer<DurationEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.ASK_FOR_LEAVE_ACTIVITY_DURATION, d);
            }

            @Override
            public void onNext(DurationEntity durationEntity) {
                switch (durationEntity.CODE) {
                    case 1:
                        view.setDuration(durationEntity.TIME);
                        break;
                    case 0:
                        view.showLoginDialog(view);
                        break;
                    default:
                        ToastUtil.showToast("获取时长失败");
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
