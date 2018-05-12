package com.hengkai.officeautomationsystem.network.service;

import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.NewUnitKeywordEntity;
import com.hengkai.officeautomationsystem.network.entity.NewUnitTypeEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Harry on 2018/5/12.
 */
public interface NewUnitService {

    @FormUrlEncoded
    @POST
    Observable<NewUnitKeywordEntity> getKeywordList(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Observable<NewUnitTypeEntity> getTypeList(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Observable<CommonReceiveMessageEntity> commit(@Url String url, @FieldMap Map<String, String> params);
}
