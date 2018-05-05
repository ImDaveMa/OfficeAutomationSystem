package com.hengkai.officeautomationsystem.network.service;

import com.hengkai.officeautomationsystem.network.entity.UnitLibraryEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Harry on 2018/5/2.
 */
public interface UnitLibraryService {

    @FormUrlEncoded
    @POST
    Observable<UnitLibraryEntity> getUnitList(@Url String url, @FieldMap Map<String, String> params);
}
