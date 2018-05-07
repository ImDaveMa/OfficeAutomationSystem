package com.hengkai.officeautomationsystem.final_constant;

/**
 * Created by Harry on 2018/4/17.
 * 存储url地址
 */
public class URLFinal {

    /**
     * 版本控制
     */
    public static final String VERSION = "v1.0.0/";
    /**
     * baseURL
     */
//    public static final String BASE_URL = "http://192.168.2.157:8080/" + VERSION + "OA_inside/";
    public static final String BASE_URL = "http://192.168.2.157:8080/OA_inside/";


    /**
     * 登录
     */
    public static final String LOGIN_URL = "mobile/login/password";

    /**
     * 获取联系人列表
     */
    public static final String GET_CONTACTS_LIST = "mobile/user/selectDirectories";

    /**
     * 获取单位库列表
     */
    public static final String GET_UNIT_LIST = "mobile/visit/companyList";

    /**
     * 获取拜访跟进列表
     */
    public static final String GET_VISIT_RECORD_LIST = "mobile/visit/list";

    /**
     * 获取物品列表
     */
    public static final String GET_GOODS_LIST = "mgoods/toGoodsList";
}
