package com.hengkai.officeautomationsystem.function.goods_supplier;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.GoodsSupplierDetailEntity;
import com.hengkai.officeautomationsystem.network.entity.ProjectLibraryDetailEntity;
import com.hengkai.officeautomationsystem.network.service.GoodsSupplierService;
import com.hengkai.officeautomationsystem.network.service.ProjectLibraryDetailService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Harry on 2018/5/17.
 */
public class GoodsSupplierDetailModel extends BaseModel {

    private final GoodsSupplierService service;

    public GoodsSupplierDetailModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(GoodsSupplierService.class);
    }

    public void getGoodsSupplier(int id, Observer<GoodsSupplierDetailEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("ID", String.valueOf(id));

        service.getGoodsSupplier(URLFinal.GET_GOODS_SUPPLIER_DETAIL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
