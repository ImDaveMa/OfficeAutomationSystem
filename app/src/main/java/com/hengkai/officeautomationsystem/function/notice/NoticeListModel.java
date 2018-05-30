package com.hengkai.officeautomationsystem.function.notice;

import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.service.ApproveService;
import com.hengkai.officeautomationsystem.network.service.NoticeService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class NoticeListModel {

    /**
     * 全部
     */
    public static final int SEARCHSTATUS_NORMAL = -1;
    /**
     * 未读
     */
    public static final int SEARCHSTATUS_UNREAD = 0;
    /**
     * 已读
     */
    public static final int SEARCHSTATUS_READED = 1;

    private final NoticeService service;

    public NoticeListModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(NoticeService.class);
    }

    public void getNoticeList(int id, int readState, Observer observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        // params.put("SEARCHNAME", operating + ""); // 按照名称模糊搜索
        if(readState > -1) {
            params.put("SEARCHSTATUS", readState + ""); // 0：未读  1：已读  不传参数或者为null则加载全部
        }
        params.put("PAGEID", id + "");
        params.put("PAGESIZE", CommonFinal.PAGE_SIZE + "");

        service.getNoticeList(URLFinal.GET_NOTICE_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
