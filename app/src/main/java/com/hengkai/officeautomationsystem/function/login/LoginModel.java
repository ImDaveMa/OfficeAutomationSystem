package com.hengkai.officeautomationsystem.function.login;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.network.service.LoginService;
import com.hengkai.officeautomationsystem.utils.DeviceUtill;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Harry on 2018/5/2.
 */
public class LoginModel extends BaseModel {

    private final LoginService service;

    public LoginModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(LoginService.class);
    }

    public void login(String loginName, String password, Observer observer) {
        Map<String, String> params = new HashMap<>();

        params.put("LOGINNAME", loginName);
        params.put("PASSWORD", password);
        params.put("UUID", DeviceUtill.getDeviceUUID());

        service.login(URLFinal.LOGIN_URL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
