package com.hengkai.officeautomationsystem.function.visit_record.comment;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.CommentVisitEntity;
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
        params.put("FUNCTIONNAME", "approval_bfgj");

        service.getCommentList(URLFinal.GET_COMMENT_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
