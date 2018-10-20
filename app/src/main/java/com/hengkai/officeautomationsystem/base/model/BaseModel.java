package com.hengkai.officeautomationsystem.base.model;

import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.utils.SPUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harry on 2018/4/17.
 */
public class BaseModel implements IBaseModel {

    protected Map<String, String> getDefaultParams(){
        Map<String, String> commonParams = new HashMap<>();
        commonParams.put("TOKEN", SPUtils.getString(UserInfo.TOKEN.name(), ""));
        commonParams.put("USERID", SPUtils.getString(UserInfo.USER_ID.name(), ""));
        return commonParams;
    }

}
