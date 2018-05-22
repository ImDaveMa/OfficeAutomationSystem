package com.hengkai.officeautomationsystem.function.message;

import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.service.ApproveService;
import com.hengkai.officeautomationsystem.network.service.MessageService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MessageListModel {
    public final int OPERATING_NEED_APPROVE = 0;
    public final int OPERATING_COPY = 1;
    public final int OPERATING_MY = 2;

    private final MessageService service;

    public MessageListModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(MessageService.class);
    }

    public void getMsgList(Observer observer, int id) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("PAGEID", id + "");
        params.put("PAGESIZE", CommonFinal.PAGE_SIZE + "");

        service.getMsgList(URLFinal.GET_MSG_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
