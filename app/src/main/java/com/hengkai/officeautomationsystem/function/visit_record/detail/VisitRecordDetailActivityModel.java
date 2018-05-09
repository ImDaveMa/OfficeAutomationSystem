package com.hengkai.officeautomationsystem.function.visit_record.detail;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailEntity;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailGetVisitUnitEntity;
import com.hengkai.officeautomationsystem.network.service.VisitRecordDetailService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Harry on 2018/5/8.
 */
public class VisitRecordDetailActivityModel extends BaseModel {

    private final VisitRecordDetailService service;

    public VisitRecordDetailActivityModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(VisitRecordDetailService.class);
    }

    public void getVisitUnitList(Observer<VisitRecordDetailGetVisitUnitEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));

        service.getVisitUnitList(URLFinal.GET_VISIT_UNIT_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getVisitCustomerList(int unitID, Observer<VisitRecordDetailGetVisitUnitEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("ID", String.valueOf(unitID));

        service.getVisitUnitList(URLFinal.GET_VISIT_UNIT_CUSTOMER_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getVisitProjectList(int customerID, Observer<VisitRecordDetailGetVisitUnitEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("ID", String.valueOf(customerID));

        service.getVisitUnitList(URLFinal.GET_VISIT_UNIT_PROJECT_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void toSaveCurrentPageOnNewAdd(Map<String, String> params, Observer<CommonReceiveMessageEntity> observer) {
        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("userId", SPUtils.getString(UserInfo.USER_ID.name(), ""));

        service.toSaveOnNewAdd(URLFinal.TO_SAVE_ON_NEW_ADD, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getVisitRecordDetail(int currentVisitRecordID, Observer<VisitRecordDetailEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("ID", String.valueOf(currentVisitRecordID));

        service.getVisitRecordDetail(URLFinal.GET_VISIT_RECORD_DETAIL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void toEnd(Map<String, String> params, Observer<CommonReceiveMessageEntity> observer) {
        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("userId", SPUtils.getString(UserInfo.USER_ID.name(), ""));

        service.toEnd(URLFinal.TO_END, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void toSaveOrCommit(Map<String, String> params, Observer<CommonReceiveMessageEntity> observer) {
        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("userId", SPUtils.getString(UserInfo.USER_ID.name(), ""));

        service.toSaveOrCommit(URLFinal.TO_SAVE_OR_COMMIT, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
