package com.hengkai.officeautomationsystem.function.visit_record;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.service.VisitRecordService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Harry on 2018/5/7.
 */
public class VisitRecordActivityModel extends BaseModel {

    private final VisitRecordService service;

    public VisitRecordActivityModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(VisitRecordService.class);
    }

    public void getVisitRecordList(Observer observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));

        service.getVisitRecordList(URLFinal.GET_VISIT_RECORD_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void deleteItem(int ID, Observer<CommonReceiveMessageEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("ID", String.valueOf(ID));

        service.deleteItem(URLFinal.VISIT_RECORD_ACTIVITY_DELETE_ITEM, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
