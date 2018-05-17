package com.hengkai.officeautomationsystem.network.service;

import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.GoodsEntity;
import com.hengkai.officeautomationsystem.network.entity.GoodsParamsEntity;
import com.hengkai.officeautomationsystem.network.entity.GoodsUnitEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface GoodsUnitService {

    @FormUrlEncoded
    @POST
    Observable<GoodsUnitEntity> getGoodsUnitList(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Observable<CommonReceiveMessageEntity> addGoodsUnit(@Url String url, @FieldMap Map<String, String> params);
}
