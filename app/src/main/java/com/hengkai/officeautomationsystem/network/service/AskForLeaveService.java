package com.hengkai.officeautomationsystem.network.service;

import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.DurationEntity;
import com.hengkai.officeautomationsystem.network.entity.GetAskForLeaveListEntity;
import com.hengkai.officeautomationsystem.network.entity.GoOutEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Harry on 2018/5/21.
 */
public interface AskForLeaveService {

    @FormUrlEncoded
    @POST
    Observable<GetAskForLeaveListEntity> getAskForLeaveList(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Observable<CommonReceiveMessageEntity> add(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Observable<DurationEntity> duration(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Observable<GoOutEntity> getCopyPerson(@Url String url, @FieldMap Map<String, String> params);
}
