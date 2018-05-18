package com.hengkai.officeautomationsystem.function.project_library.detail;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.ProjectLibraryDetailEntity;
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
public class ProjectLibraryDetailModel extends BaseModel {

    private final ProjectLibraryDetailService service;

    public ProjectLibraryDetailModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(ProjectLibraryDetailService.class);
    }

    public void getProjectDetail(int projectID, Observer<ProjectLibraryDetailEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("PROJECTID", String.valueOf(projectID));

        service.getProjectDetail(URLFinal.GET_PROJECT_DETAIL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
