package com.hengkai.officeautomationsystem.function.notice;

import com.hengkai.officeautomationsystem.base.presenter.BasePresenter;
import com.hengkai.officeautomationsystem.final_constant.NetworkTagFinal;
import com.hengkai.officeautomationsystem.network.entity.CommentVisitEntity;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.NoticeDetailEntity;
import com.hengkai.officeautomationsystem.network.entity.NoticeEntity;
import com.hengkai.officeautomationsystem.utils.ToastUtil;
import com.hengkai.officeautomationsystem.utils.rx.RxApiManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NoticeDetailPresenter extends BasePresenter<NoticeDetailActivity> {

    private final NoticeDetailModel model;

    public NoticeDetailPresenter() {
        model = new NoticeDetailModel();
    }

    public void getNoticeDetail(int id) {
        view.showDialog();
        model.getNoticeDetail(id, new Observer<NoticeDetailEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.NOTICE_DETAIL_ACTIVITY_GET_NOTICE_LIST, d);
            }

            @Override
            public void onNext(NoticeDetailEntity mEntity) {
                view.dismissDialog();
                if (mEntity.CODE == -1) { // 缺少参数
                    ToastUtil.showToast("缺少参数");
                    view.finish();
                } else if (mEntity.CODE == 0) {//TOKEN失效
                    view.showLoginDialog(view);
                }  else if (mEntity.CODE == 1 || mEntity.CODE == 2) {
                    view.prepareData(mEntity.DATE);
                }else if(mEntity.CODE == 3){
                    ToastUtil.showToast("获取内容失败");
                    view.finish();
                }
            }

            @Override
            public void onError(Throwable e) {
                view.dismissDialog();
                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {}
        });
    }

    public void getNoticeComments(int currentID) {
        model.getNoticeComments(currentID, new Observer<CommentVisitEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.NOTICE_DETAIL_ACTIVITY_GET_COMMENT_LIST, d);
            }

            @Override
            public void onNext(CommentVisitEntity commentVisitEntity) {
                switch (commentVisitEntity.CODE) {
                    //查询结果不为空
                    case 1:
                    //查询结果为空
                    case 2:
                        view.prepareCommentList(commentVisitEntity.DATE);
                        break;
                    case 0:
                        view.showLoginDialog(view);
                        break;
                    case -1:
                        //缺少参数
                        ToastUtil.showToast("查询评论失败");
                        break;
                    case 3:
                        //操作失败
                        ToastUtil.showToast("查询评论失败");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showToast("网络连接错误");
            }

            @Override
            public void onComplete() {}
        });
    }

    public void comment(int id, String comment) {
        view.showDialog();
        model.comment(id, comment, new Observer<CommonReceiveMessageEntity>() {
            @Override
            public void onSubscribe(Disposable d) {
                RxApiManager.get().add(NetworkTagFinal.NOTICE_DETAIL_ACTIVITY_COMMENT_LIST, d);
            }

            @Override
            public void onNext(CommonReceiveMessageEntity mEntity) {
                view.dismissDialog();
                if (mEntity.CODE == -1) { // 缺少参数
                    ToastUtil.showToast("缺少参数");
                    view.finish();
                } else if (mEntity.CODE == 0) {//TOKEN失效
                    view.showLoginDialog(view);
                }  else if (mEntity.CODE == 1) {
                    view.addCommentSuccess();
                }else if(mEntity.CODE == 3){
                    ToastUtil.showToast("评论失败");
                    view.finish();
                }
            }

            @Override
            public void onError(Throwable e) {
                view.dismissDialog();
                ToastUtil.showToast("请求网络失败");
            }

            @Override
            public void onComplete() {}
        });
    }
}
