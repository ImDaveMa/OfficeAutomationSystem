package com.hengkai.officeautomationsystem.function.contacts_library;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.ContactsLibraryEntity;
import com.hengkai.officeautomationsystem.network.service.ContactsLibraryService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Harry on 2018/5/11.
 */
public class ContactsLibraryModel extends BaseModel {

    private final ContactsLibraryService service;

    public ContactsLibraryModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(ContactsLibraryService.class);
    }

    public void getContactsList(int ID, int unitID, Observer<ContactsLibraryEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("pageSize", String.valueOf(CommonFinal.PAGE_SIZE));
        if(unitID > 0) {
            params.put("companyId", String.valueOf(unitID)); // 根据单位筛选
        }
        params.put("ID", String.valueOf(ID));

        service.getContactsList(URLFinal.GET_CONTACTS_LIBRARY_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
