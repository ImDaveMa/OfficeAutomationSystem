package com.hengkai.officeautomationsystem.function.goods_supplier;

import android.text.TextUtils;

import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.service.GoodsSupplierService;
import com.hengkai.officeautomationsystem.network.service.GoodsUnitService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class GoodsSupplierModel {

    private final GoodsSupplierService service;

    public GoodsSupplierModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(GoodsSupplierService.class);
    }

    public void getGoodsSupplierList(Observer observer, String searchName, int searchType, int searchCity) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        if(!TextUtils.isEmpty(searchName)) {
            params.put("searchName", searchName);
        }
        if(searchType > 0) {
            params.put("searchType", searchType + "");
        }
        if(searchCity > 0) {
            params.put("searchCity", searchCity + "");
        }

        service.getGoodsSupplierList(URLFinal.GOODS_SUPPLIER_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
