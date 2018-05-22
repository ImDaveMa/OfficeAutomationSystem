package com.hengkai.officeautomationsystem.network.service;

import com.hengkai.officeautomationsystem.network.entity.MessageEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface MessageService {

    @FormUrlEncoded
    @POST
    Observable<MessageEntity> getMsgList(@Url String url, @FieldMap Map<String, String> params);

}
