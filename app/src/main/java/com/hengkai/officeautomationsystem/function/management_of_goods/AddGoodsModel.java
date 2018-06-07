package com.hengkai.officeautomationsystem.function.management_of_goods;

import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.service.AddGoodsService;
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
public class AddGoodsModel {

    private final AddGoodsService service;

    public AddGoodsModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(AddGoodsService.class);
    }

    /**
     * 添加物品
     *
     * @param observer
     * @param name     名称
     * @param type     类型
     * @param supplier 供货商
     * @param unit     单位
     * @param band     品牌
     * @param spec     规格
     * @param cost     单价
     * @param remark   备注
     * @param num      数量
     * @param total    总价
     */
    public void addGoods(Observer observer, String name, int type, int supplier, int unit, String band, String spec, double cost, String remark, int num, double total) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("NAME", name); // 名称
        params.put("GOODSTYPE", type + ""); // 物品类型
        params.put("SUPPLIERID", supplier + ""); // 供货商
        params.put("UNITID", unit + ""); // 单位
        params.put("BAND", band); // 品牌
        params.put("SPEC", spec); // 规格
        params.put("COST", cost + ""); // 单价
        params.put("REMARK", remark); // 备注
        params.put("NUM", num + ""); // 数量
        params.put("TOTAL", total + ""); // 总数

        service.addGoods(URLFinal.ADD_GOODS_URL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 修改物品
     *
     * @param observer
     * @param name     名称
     * @param type     类型
     * @param supplier 供货商
     * @param unit     单位
     * @param band     品牌
     * @param spec     规格
     * @param cost     单价
     * @param remark   备注
     * @param num      数量
     * @param total    总价
     */
    public void updateGoods(Observer observer, int id, String name, int type, int supplier, int unit, String band, String spec, double cost, String remark, int num, double total) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("ID", id + ""); // ID
        params.put("NAME", name); // 名称
        params.put("GOODSTYPE", type + ""); // 物品类型
        params.put("SUPPLIERID", supplier + ""); // 供货商
        params.put("UNITID", unit + ""); // 单位
        params.put("BRAND", band); // 品牌
        params.put("SPEC", spec); // 规格
        params.put("COST", cost + ""); // 单价
        params.put("REMARK", remark); // 备注
        params.put("NUM", num + ""); // 数量
        params.put("TOTAL", total + ""); // 总数

        service.updateGoods(URLFinal.UPDATE_GOODS_URL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取新增页面所需参数
     *
     * @param observer
     */
    public void getAddParams(Observer observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));

        service.getAddParams(URLFinal.ADD_GOODS_PARAMS, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取页面所需参数
     *
     * @param observer
     * @param id       主键ID
     */
    public void getUpdateParams(Observer observer, int id) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("ID", id + "");

        service.getUpdateParams(URLFinal.UPDATE_GOODS_PARAMS, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
