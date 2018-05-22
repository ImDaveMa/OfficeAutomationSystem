package com.hengkai.officeautomationsystem.function.goods_supplier;

import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.service.AddGoodsSupplierService;
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
public class AddGoodsSupplierModel {

    private final AddGoodsSupplierService service;

    public AddGoodsSupplierModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(AddGoodsSupplierService.class);
    }

    /**
     * 添加物品供应商
     *
     * @param observer
     * @param name 名称
     * @param supperType 类型
     * @param city 所在城市
     * @param address 地址
     * @param postalCode 邮政编码
     * @param contacts 联系人
     * @param phone 电话
     * @param fax 传真
     * @param mailBox 电子邮箱
     * @param qq QQ
     * @param weChat 微信
     * @param source 来源
     * @param state 是否是合作商家
     * @param description 描述
     * @param remark 备注
     */
    public void addGoodsSupplier(Observer observer, String name, int supperType, String city, String address,
                                 String postalCode, String contacts, String phone, String fax,String mailBox,
                                 String qq, String weChat, String source, int state, String description, String remark) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("NAME", name); // 名称
        params.put("SUPPERTYPE", supperType + ""); // 供货商类型
        params.put("CITY", city); // 供货商所在城市
        params.put("ADDRESS", address); // 地址
        params.put("POSTALCODE", postalCode); // 邮政编码
        params.put("CONTACTS", contacts); // 联系人
        params.put("PHONE", phone); // 电话
        params.put("FAX", fax); // 传真
        params.put("MAILBOX", mailBox); // 电子邮箱
        params.put("QQ", qq); // QQ
        params.put("WECHAT", weChat); // 微信
        params.put("SOURCE", source); // 来源
        params.put("STATE", state + ""); // 是否是合作商家
        params.put("DESCRIPTION", description); // 业务描述
        params.put("REMARK", remark); // 备注

        service.addGoodsSupplier(URLFinal.ADD_GOODS_SUPPLIER_URL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 修改物品供货商
     *
     * @param observer
     * @param id     名称
     * @param name 名称
     * @param supperType 类型
     * @param city 所在城市
     * @param address 地址
     * @param postalCode 邮政编码
     * @param contacts 联系人
     * @param phone 电话
     * @param fax 传真
     * @param mailBox 电子邮箱
     * @param qq QQ
     * @param weChat 微信
     * @param source 来源
     * @param state 是否是合作商家
     * @param description 描述
     * @param remark 备注
     */
    public void updateGoodsSupplier(Observer observer, int id, String name, int supperType, String city, String address,
                                    String postalCode, String contacts, String phone, String fax,String mailBox,
                                    String qq, String weChat, String source, int state, String description, String remark) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("ID", id + ""); // ID
        params.put("NAME", name); // 名称
        params.put("SUPPERTYPE", supperType + ""); // 供货商类型
        params.put("CITY", city); // 供货商所在城市
        params.put("ADDRESS", address); // 地址
        params.put("POSTALCODE", postalCode); // 邮政编码
        params.put("CONTACTS", contacts); // 联系人
        params.put("PHONE", phone); // 电话
        params.put("FAX", fax); // 传真
        params.put("MAILBOX", mailBox); // 电子邮箱
        params.put("QQ", qq); // QQ
        params.put("WECHAT", weChat); // 微信
        params.put("SOURCE", source); // 来源
        params.put("STATE", state + ""); // 是否是合作商家
        params.put("DESCRIPTION", description); // 业务描述
        params.put("REMARK", remark); // 备注

        service.updateGoodsSupplier(URLFinal.UPDATE_GOODS_SUPPLIER_URL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取新增页面所需参数
     *
     * @param observer
     */
    public void getAddSupplierParams(Observer observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));

        service.getAddSupplierParams(URLFinal.ADD_GOODS_SUPPLIER_PARAMS, params)
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
    public void getUpdateSupplierParams(Observer observer, int id) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("ID", id + "");

        service.getUpdateSupplierParams(URLFinal.UPDATE_GOODS_SUPPLIER_PARAMS, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
