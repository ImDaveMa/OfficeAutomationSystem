package com.hengkai.officeautomationsystem.function.setting;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.service.SettingService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SettingModel extends BaseModel {

    private final SettingService service;

    public SettingModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(SettingService.class);
    }

    public void request(String url, Map<String,String> params, Observer<CommonReceiveMessageEntity> observer) {
        params.putAll(super.getDefaultParams());

        service.logout(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}

