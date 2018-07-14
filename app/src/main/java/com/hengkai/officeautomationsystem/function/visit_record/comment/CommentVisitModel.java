package com.hengkai.officeautomationsystem.function.visit_record.comment;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.CommentVisitEntity;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailEntity;
import com.hengkai.officeautomationsystem.network.service.CommentVisitService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Harry on 2018/5/16.
 */
public class CommentVisitModel extends BaseModel {

    private final CommentVisitService service;

    public CommentVisitModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(CommentVisitService.class);
    }

    public void getVisitRecordDetail(int currentVisitRecordID, Observer<VisitRecordDetailEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("ID", String.valueOf(currentVisitRecordID));

        service.getVisitRecordDetail(URLFinal.GET_VISIT_RECORD_DETAIL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getCommentList(int currentID, Observer<CommentVisitEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("OBJECTID", String.valueOf(currentID));
        params.put("FUNCTIONNAME", "sd_visit");

        service.getCommentList(URLFinal.GET_COMMENT_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * @param approvalID    审批ID
     * @param approvalState 审批状态（只能为1,2,3）  1同意2拒绝3撤销
     * @param observer      回调
     * @param comment       拒绝理由
     */
    public void approval(int approvalID, int approvalState, String comment, Observer<CommonReceiveMessageEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("APPROVALID", String.valueOf(approvalID));
        params.put("APPROVALSTATE", String.valueOf(approvalState));
        params.put("COMMENT", comment);

        service.approval(URLFinal.GO_TO_APPROVAL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
