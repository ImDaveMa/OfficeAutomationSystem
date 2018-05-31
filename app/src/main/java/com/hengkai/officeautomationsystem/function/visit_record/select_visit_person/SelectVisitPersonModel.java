package com.hengkai.officeautomationsystem.function.visit_record.select_visit_person;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
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
 * Created by Harry on 2018/5/30.
 */
public class SelectVisitPersonModel extends BaseModel {

    private final VisitRecordDetailService service;

    public SelectVisitPersonModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(VisitRecordDetailService.class);
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
}
