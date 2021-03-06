package com.hengkai.officeautomationsystem.function.goods_in;

import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.service.GoodsInService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 *
 */
public class GoodsInDetailModel {

    private final GoodsInService service;

    public GoodsInDetailModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(GoodsInService.class);
    }

    /**
     * 保存入库申请
     * @param observer
     * @param id 主键ID
     */
    public void getGoodsInDetail(Observer observer, int id) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("id", id + "");

        service.getGoodsInDetail(URLFinal.GOODS_IN_DETAIL_URL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
