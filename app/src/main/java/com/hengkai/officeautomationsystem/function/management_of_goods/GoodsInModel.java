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

/**
 *
 */
public class GoodsInModel {

    private final GoodsInService service;

    public GoodsInModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(GoodsInService.class);
    }

    /**
     * 保存入库申请
     * @param observer
     * @param price 价格
     * @param reason 事由
     * @param details 物品详情
     */
    public void saveGoodsIn(Observer observer, double price, String reason, String details) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("TOTALMONEY", price + ""); // 总价
        params.put("PURPOSE", reason + ""); // 领用原因
        params.put("OUTGOODSVO", details + ""); // 领用详情
        params.put("pageSize", CommonFinal.PAGE_SIZE + "");

        service.submitGoodsIn(URLFinal.GOODS_IN_URL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取页面所需参数
     * @param observer
     */
    public void getParams(Observer observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        // params.put("USERID", SPUtils.getInt(UserInfo.USER_ID.name(), 0) + "");

        service.getParams(URLFinal.GET_GOODS_IN_PARAMS, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
