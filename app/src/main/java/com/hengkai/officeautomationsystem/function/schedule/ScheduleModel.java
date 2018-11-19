package com.hengkai.officeautomationsystem.function.schedule;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.network.entity.CommonStringListEntity;
import com.hengkai.officeautomationsystem.network.service.ScheduleService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ScheduleModel extends BaseModel {

    private final ScheduleService service;

    public ScheduleModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(ScheduleService.class);
    }

    public void getCalendarList(int year, int month, Observer<CommonStringListEntity> observer) {
        Map<String, String> params = getDefaultParams();

        params.put("Year", String.valueOf(year));
        params.put("Month", String.valueOf(month));

        service.getCalendarList(URLFinal.GET_CALENDAR_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getCalendarListWithDay(int year, int month, int day, Observer<CommonStringListEntity> observer) {
        Map<String, String> params = getDefaultParams();

        params.put("Year", String.valueOf(year));
        params.put("Month", String.valueOf(month));
        params.put("Day", String.valueOf(day));

        service.getCalendarListWithDay(URLFinal.GET_CALENDAR_LIST_WITH_DAY, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
