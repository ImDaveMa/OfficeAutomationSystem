package com.hengkai.officeautomationsystem.network.service;

import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Harry on 2018/5/16.
 */
public interface GoToCommentService {

    @FormUrlEncoded
    @POST
    Observable<CommonReceiveMessageEntity> comment(@Url String url, @FieldMap Map<String, String> params);
}
