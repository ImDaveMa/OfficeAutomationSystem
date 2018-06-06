package com.hengkai.officeautomationsystem.network.service;

import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.NewProjectGetPersonEntity;
import com.hengkai.officeautomationsystem.network.entity.NewProjectGetTypeEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Harry on 2018/6/5.
 */
public interface NewProjectService {

    @FormUrlEncoded
    @POST
    Observable<NewProjectGetTypeEntity> getTypeList(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Observable<NewProjectGetPersonEntity> getPersonList(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Observable<CommonReceiveMessageEntity> commit(@Url String url, @FieldMap Map<String, String> params);
}
