package com.hengkai.officeautomationsystem.function.unit_library.search;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.NewUnitKeywordEntity;
import com.hengkai.officeautomationsystem.network.entity.UnitLibraryEntity;
import com.hengkai.officeautomationsystem.network.service.NewUnitService;
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
 * Created by Harry on 2018/5/28.
 */
public class SearchUnitModel extends BaseModel {

    private final NewUnitService service;
    private final UnitLibraryService unitLibraryService;

    public SearchUnitModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(NewUnitService.class);
        unitLibraryService = retrofit.create(UnitLibraryService.class);
    }

    public void getKeywordList(Observer<NewUnitKeywordEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));

        service.getKeywordList(URLFinal.GET_KEYWORD_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 直接搜索 显示全部
     * @param ID 用于分页, 查询次ID之后的数据, 0为默认
     */
    public void getUnitList(int ID, Observer<UnitLibraryEntity> observer) {

        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("pageSize", String.valueOf(CommonFinal.PAGE_SIZE));
        params.put("ID", String.valueOf(ID));

        unitLibraryService.getUnitList(URLFinal.GET_UNIT_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 关键词直接搜索
     * @param ID 用于分页, 查询次ID之后的数据, 0为默认
     */
    public void getUnitListWithKeyword(int keywordId, int ID, Observer<UnitLibraryEntity> observer) {

        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("pageSize", String.valueOf(CommonFinal.PAGE_SIZE));
        params.put("ID", String.valueOf(ID));
        params.put("keywordId", String.valueOf(keywordId));

        unitLibraryService.getUnitList(URLFinal.GET_UNIT_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 单位名称 单位地址的模糊搜索
     * @param ID 用于分页, 查询次ID之后的数据, 0为默认
     */
    public void getUnitListWithName(String name, int ID, Observer<UnitLibraryEntity> observer) {

        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("pageSize", String.valueOf(CommonFinal.PAGE_SIZE));
        params.put("ID", String.valueOf(ID));
        params.put("name", name);

        unitLibraryService.getUnitList(URLFinal.GET_UNIT_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
