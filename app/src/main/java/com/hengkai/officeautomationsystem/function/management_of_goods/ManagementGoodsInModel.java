package com.hengkai.officeautomationsystem.function.management_of_goods;

import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.service.GoodsInService;
import com.hengkai.officeautomationsystem.network.service.GoodsService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ManagementGoodsInModel {

    private final GoodsInService service;

    public ManagementGoodsInModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(GoodsInService.class);
    }

    public void getGoodsList(Observer observer, int id) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("searchId", id + "");
        params.put("pageSize", CommonFinal.PAGE_SIZE + "");

        service.getGoodsInList(URLFinal.GET_GOODS_IN_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
