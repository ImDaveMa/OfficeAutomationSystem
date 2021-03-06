package com.hengkai.officeautomationsystem.network.service;

import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.ContactsLibraryEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Harry on 2018/5/2.
 */
public interface ContactsLibraryService {

    @FormUrlEncoded
    @POST
    Observable<ContactsLibraryEntity> getContactsList(@Url String url, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST
    Observable<CommonReceiveMessageEntity> saveContact(@Url String url, @FieldMap Map<String, String> params);
}
