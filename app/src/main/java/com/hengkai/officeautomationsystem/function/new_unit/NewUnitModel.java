package com.hengkai.officeautomationsystem.function.new_unit;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.NewUnitKeywordEntity;
import com.hengkai.officeautomationsystem.network.entity.NewUnitTypeEntity;
import com.hengkai.officeautomationsystem.network.service.NewUnitService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Harry on 2018/5/11.
 */
public class NewUnitModel extends BaseModel {

    private final NewUnitService service;

    public NewUnitModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(NewUnitService.class);
    }

    public void getKeywordList(Observer<NewUnitKeywordEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));

        service.getKeywordList(URLFinal.GET_KEYWORD_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getTypeList(Observer<NewUnitTypeEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));

        service.getTypeList(URLFinal.GET_TYPE_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void commit(Map<String, String> params, Observer<CommonReceiveMessageEntity> observer) {

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("userId", SPUtils.getString(UserInfo.USER_ID.name(), ""));

        service.commit(URLFinal.NEW_UNIT_COMMIT, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
