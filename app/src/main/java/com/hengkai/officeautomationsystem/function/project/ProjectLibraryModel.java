package com.hengkai.officeautomationsystem.function.project;

import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.service.GoodsService;
import com.hengkai.officeautomationsystem.network.service.ProjectLibraryService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ProjectLibraryModel {

    private final ProjectLibraryService service;

    public ProjectLibraryModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(ProjectLibraryService.class);
    }

    public void getProjectList(Observer observer, int id) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("userId", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("searchId", id + "");
        params.put("pageSize", CommonFinal.PAGE_SIZE + "");

        service.getProjectList(URLFinal.GET_PROJECT_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
