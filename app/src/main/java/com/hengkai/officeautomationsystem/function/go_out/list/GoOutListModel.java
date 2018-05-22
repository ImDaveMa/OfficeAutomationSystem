package com.hengkai.officeautomationsystem.function.go_out.list;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.CommonFinal;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.GetAskForLeaveListEntity;
import com.hengkai.officeautomationsystem.network.service.AskForLeaveService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Harry on 2018/5/22.
 */
public class GoOutListModel extends BaseModel {

    private final AskForLeaveService service;

    public GoOutListModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(AskForLeaveService.class);
    }

    public void getAskForLeaveList(int searchID, Observer<GetAskForLeaveListEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("attendanceType", String.valueOf(2));
        params.put("SEARCHID", String.valueOf(searchID));
        params.put("pageSize", String.valueOf(CommonFinal.PAGE_SIZE));

        service.getAskForLeaveList(URLFinal.GET_ASK_FOR_LEAVE_LIST, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
