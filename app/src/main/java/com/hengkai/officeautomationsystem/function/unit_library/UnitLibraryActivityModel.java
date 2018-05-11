package com.hengkai.officeautomationsystem.function.unit_library;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.UnitLibraryEntity;
import com.hengkai.officeautomationsystem.network.service.UnitLibraryService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Harry on 2018/5/5.
 */
public class UnitLibraryActivityModel extends BaseModel {

    private final UnitLibraryService service;

    public UnitLibraryActivityModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(UnitLibraryService.class);
    }

    /**
     * @param ID 用于分页, 查询次ID之后的数据, 0为默认
     */
    public void getUnitList(int ID, Observer<UnitLibraryEntity> observer) {

        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("pageSize", String.valueOf(CommonFinal.PAGE_SIZE));
        params.put("ID", String.valueOf(ID));

        service.getUnitList(URLFinal.GET_UNIT_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
