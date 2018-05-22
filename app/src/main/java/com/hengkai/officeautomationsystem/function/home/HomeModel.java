package com.hengkai.officeautomationsystem.function.home;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.service.ContactsService;
import com.hengkai.officeautomationsystem.network.service.HomeService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Harry on 2018/5/4.
 */
public class HomeModel extends BaseModel {

    private final HomeService service;

    public HomeModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(HomeService.class);
    }

    public void getApproveList(Observer observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("OPERATING", "0"); // 0:需要我审批 1:抄送我的2：我发起的审批
        params.put("SEARCHDAY", "0"); // 0为今天 1为昨天，依次叠加
        params.put("PAGEID", "0");
        params.put("PAGESIZE", "1");
        // params.put("SEARCHSTATE", "0"); // 根据审批状态搜索

        service.getApproveList(URLFinal.GET_APPROVE_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getMsgList(Observer observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("PAGEID", "0");
        params.put("PAGESIZE", "2");

        service.getMsgList(URLFinal.GET_MSG_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
