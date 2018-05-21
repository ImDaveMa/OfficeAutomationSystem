package com.hengkai.officeautomationsystem.network.service;

import com.hengkai.officeautomationsystem.network.entity.AddGoodsEntity;
import com.hengkai.officeautomationsystem.network.entity.AddGoodsSupplierEntity;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.EditGoodsEntity;
import com.hengkai.officeautomationsystem.network.entity.EditGoodsSupplierEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface AddGoodsSupplierService {

    @FormUrlEncoded
    @POST
    Observable<CommonReceiveMessageEntity> addGoodsSupplier(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Observable<CommonReceiveMessageEntity> updateGoodsSupplier(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Observable<AddGoodsSupplierEntity> getAddSupplierParams(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Observable<EditGoodsSupplierEntity> getUpdateSupplierParams(@Url String url, @FieldMap Map<String, String> params);

}
