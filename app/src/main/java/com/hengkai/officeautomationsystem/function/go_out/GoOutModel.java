package com.hengkai.officeautomationsystem.function.go_out;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.DurationEntity;
import com.hengkai.officeautomationsystem.network.entity.GoOutEntity;
import com.hengkai.officeautomationsystem.network.service.AskForLeaveService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Harry on 2018/5/22.
 */
public class GoOutModel extends BaseModel {

    private final AskForLeaveService service;

    public GoOutModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(AskForLeaveService.class);
    }

    public void add(Map<String, String> params, Observer<CommonReceiveMessageEntity> observer) {
        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("ATTENDANCETYPE", String.valueOf(2));

        service.add(URLFinal.ADD_GO_OUT, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void duration(String startTime, String endTime, Observer<DurationEntity> observer) {
        Map<String, String> params = new HashMap<>();
        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("startTime", startTime);
        params.put("endTime", endTime);

        service.duration(URLFinal.DURATION, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getCopyPerson(Observer<GoOutEntity> observer) {
        Map<String, String> params = new HashMap<>();
        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));

        service.getCopyPerson(URLFinal.GO_OUT_GET_PERSON, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
