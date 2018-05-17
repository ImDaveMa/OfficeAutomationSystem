package com.hengkai.officeautomationsystem.function.visit_record.comment;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Harry on 2018/5/17.
 */
public class GoToCommentPresenter extends BasePresenter<GoToCommentActivity> {

    private final GoToCommentModel model;

    public GoToCommentPresenter() {
        model = new GoToCommentModel();
    }

    public void comment(int currentID, String commentContent) {
        model.comment(currentID, commentContent, new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.GO_TO_COMMENT_ACTIVITY, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity commonReceiveMessageEntity) {
                switch (commonReceiveMessageEntity.CODE) {
                    case 1:
                        ToastUtil.showToast("提交评论成功");
                        view.setResult(CommonFinal.COMMON_RESULT_CODE);
                        view.finish();
                        break;
                    case 0:
                        view.showLoginDialog(view);
                        break;
                    default:
                        ToastUtil.showToast("提交评论失败");
                        break;
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
