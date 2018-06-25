package com.hengkai.officeautomationsystem.function.visit_record.detail_x;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hengkai.officeautomationsystem.base.model.BaseModel;
import com.hengkai.officeautomationsystem.final_constant.URLFinal;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.network.entity.CommonReceiveMessageEntity;
import com.hengkai.officeautomationsystem.network.entity.VisitRecordDetailEntity;
import com.hengkai.officeautomationsystem.network.service.VisitRecordDetailService;
import com.hengkai.officeautomationsystem.utils.RetrofitHelper;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by Harry on 2018/6/15.
 */
public class VisitRecordDetailModel extends BaseModel {

    private final VisitRecordDetailService service;

    public VisitRecordDetailModel() {
        Retrofit retrofit = RetrofitHelper.getInstance().getRetrofit();
        service = retrofit.create(VisitRecordDetailService.class);
    }

    /**
     * @param requestType          拜访的动作(visit_save, visit_start, visit_end, visit_submission
     * @param currentVisitRecordID 当前拜访的ID, 新增的时候没有这个ID, 如果是(从列表进入, 保存, 开始或者结束), 这个时候ID是有值的
     * @param observer             接口回调
     * @param companyId            拜访跟进的单位ID
     * @param type                 0：跟进 1：拜访 2：招待
     * @param projectId            拜访跟进的项目ID
     * @param contactsId           被拜访人的ID
     * @param department           被拜访人的的部门
     * @param summary              拜访总结
     * @param startLongitude       开始经度
     * @param startLatitude        开始纬度
     * @param startPhone           开始手机
     * @param startAddress         开始地址
     * @param endLongitude         结束经度
     * @param endLatitude          结束纬度
     * @param endPhone             结束手机
     * @param endAddress           结束地址
     */
    public void submissionData(@NonNull String requestType, @Nullable String currentVisitRecordID,
                               @Nullable String companyId, @NonNull String type, @Nullable String projectId,
                               @Nullable String contactsId, @Nullable String department,
                               @Nullable String summary, @Nullable String startLongitude,
                               @Nullable String startLatitude, @Nullable String startPhone,
                               @Nullable String startAddress, @Nullable String endLongitude,
                               @Nullable String endLatitude, @Nullable String endPhone, @Nullable String endAddress,
                               Observer<CommonReceiveMessageEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("userId", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("requestType", requestType);
        params.put("type", type);
        if (!TextUtils.isEmpty(currentVisitRecordID)) {
            params.put("ID", currentVisitRecordID);
        }
        if (!TextUtils.isEmpty(companyId)) {
            params.put("companyId", companyId);
        }
        if (!TextUtils.isEmpty(projectId)) {
            params.put("projectId", projectId);
        }
        if (!TextUtils.isEmpty(contactsId)) {
            params.put("contactsId", contactsId);
        }
        if (!TextUtils.isEmpty(department)) {
            params.put("department", department);
        }
        if (!TextUtils.isEmpty(summary)) {
            params.put("summary", summary);
        }
        if (!TextUtils.isEmpty(startLongitude)) {
            params.put("startLongitude", startLongitude);
        }
        if (!TextUtils.isEmpty(startLatitude)) {
            params.put("startLatitude", startLatitude);
        }
        if (!TextUtils.isEmpty(startPhone)) {
            params.put("startPhone", startPhone);
        }
        if (!TextUtils.isEmpty(startAddress)) {
            params.put("startAddress", startAddress);
        }
        if (!TextUtils.isEmpty(endLongitude)) {
            params.put("endLongitude", endLongitude);
        }
        if (!TextUtils.isEmpty(endLatitude)) {
            params.put("endLatitude", endLatitude);
        }
        if (!TextUtils.isEmpty(endPhone)) {
            params.put("endPhone", endPhone);
        }
        if (!TextUtils.isEmpty(endAddress)) {
            params.put("endAddress", endAddress);
        }

        service.submissionData(URLFinal.VISIT_RECORD_SUBMISSION_DATA, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getVisitRecordDetail(int currentVisitRecordID, Observer<VisitRecordDetailEntity> observer) {
        Map<String, String> params = new HashMap<>();

        params.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        params.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        params.put("ID", String.valueOf(currentVisitRecordID));

        service.getVisitRecordDetail(URLFinal.GET_VISIT_RECORD_DETAIL, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
