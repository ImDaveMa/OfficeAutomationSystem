package com.hengkai.officeautomationsystem.function.management_of_goods;

import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.service.GoodsService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ManagementOfGoodsModel {

    private final GoodsService service;

    public ManagementOfGoodsModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(GoodsService.class);
    }

    public void getGoodsList(Observer observer, int id) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("searchId", id + "");
        params.put("pageSize", CommonFinal.PAGE_SIZE + "");

        service.getGoodsList(URLFinal.GET_GOODS_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void deleteGoodsUnit(Observer observer, int id) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("ID", id + "");

        service.deleteGoodsUnit(URLFinal.DELETE_GOODS_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
