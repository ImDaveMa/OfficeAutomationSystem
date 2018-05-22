package com.hengkai.officeautomationsystem.function.approve;

import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.service.ApproveService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ApproveListModel {
    public final int OPERATING_NEED_APPROVE = 0;
    public final int OPERATING_COPY = 1;
    public final int OPERATING_MY = 2;

    private final ApproveService service;

    public ApproveListModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(ApproveService.class);
    }

    public void getApproveList(Observer observer, int id, int operating, int searchDay) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("OPERATING", operating + ""); // 0:需要我审批 1:抄送我的2：我发起的审批
        params.put("SEARCHDAY", searchDay + ""); // 0为今天 1为昨天，依次叠加
        // params.put("SEARCHSTATE", "0"); // 根据审批状态搜索
        params.put("PAGEID", id + "");
        params.put("PAGESIZE", CommonFinal.PAGE_SIZE + "");

        service.getApproveList(URLFinal.GET_APPROVE_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
