package com.hengkai.officeautomationsystem.network.service;

import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.CommonStringListEntity;
import com.hengkai.officeautomationsystem.network.entity.ScheduleEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ScheduleService {

    @FormUrlEncoded
    @POST
    Observable<CommonStringListEntity> getCalendarList(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Observable<ScheduleEntity> getCalendarListWithDay(@Url String url, @FieldMap Map<String, String> params);

}
