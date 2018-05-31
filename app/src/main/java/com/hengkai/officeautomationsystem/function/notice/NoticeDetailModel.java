package com.hengkai.officeautomationsystem.function.notice;

import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.service.NoticeService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class NoticeDetailModel {
    private final NoticeService service;

    public NoticeDetailModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(NoticeService.class);
    }

    public void getNoticeDetail(int id, Observer observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("ID", String.valueOf(id));

        service.getNoticeDetail(URLFinal.GET_NOTICE_DETAIL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getNoticeComments(int id, Observer observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("OBJECTID", String.valueOf(id));
        params.put("FUNCTIONNAME", "notice_pingL");

        service.getNoticeComments(URLFinal.GET_COMMENT_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void comment(int id, String comment, Observer observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("FUNCTIONNAME", "notice_pingL");
        params.put("OBJECTID", String.valueOf(id));
        params.put("COMMENTCONTENT", comment);

        service.comment(URLFinal.COMMENT_URL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
