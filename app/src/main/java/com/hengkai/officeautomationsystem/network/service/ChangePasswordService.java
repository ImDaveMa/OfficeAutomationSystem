package com.hengkai.officeautomationsystem.network.service;

import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ChangePasswordService {

    @FormUrlEncoded
    @POST
    Observable<CommonReceiveMessageEntity> request(@Url String url, @FieldMap Map<String, String> params);
}
