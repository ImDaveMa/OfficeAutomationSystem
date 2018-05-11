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
//    public static final String BASE_URL = "http://192.168.2.157:8080/OA_inside/";
    public static final String BASE_URL = "http://192.168.2.105:8080/OA_inside/";


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
    public static final String GET_UNIT_LIST = "mobile/conAcom/comList";

    /**
     * 获取拜访跟进列表
     */
    public static final String GET_VISIT_RECORD_LIST = "mobile/visit/list";
    /**
     * 拜访跟进列表 - 删除未提交的item
     */
    public static final String VISIT_RECORD_ACTIVITY_DELETE_ITEM = "mobile/visit/del";

    /**
     * 获取物品列表
     */
    public static final String GET_GOODS_LIST = "mgoods/toGoodsList";
    /**
     * 领用物品
     */
    public static final String USE_GOODS = "out/insertOutStorage";
    /**
     * 领用物品 参数请求
     */
    public static final String USE_GOODS_PARAMS = "out/insertOutPage";
    /**
     * 物品单位列表
     */
    public static final String GOODS_UNIT_LIST = "mgoods/toUnitList";
    /**
     * 物品供货商列表
     */
    public static final String GOODS_SUPPLIER_LIST = "msupplier/toSupplierList";
    /**
     * 拜访跟进详情页面 - 获取单位列表
     */
    public static final String GET_VISIT_UNIT_LIST = "mobile/visit/companyList";
    /**
     * 拜访跟进详情页面 - 获取该单位下的联系人列表
     */
    public static final String GET_VISIT_UNIT_CUSTOMER_LIST = "mobile/visit/companyDetails";
    /**
     * 拜访跟进详情页面 - 获取该联系人下的项目列表
     */
    public static final String GET_VISIT_UNIT_PROJECT_LIST = "mobile/visit/contactsDetails";
    /**
     * 拜访跟进详情页面 - 从新增按钮进入到当前页面去保存当前页面
     */
    public static final String TO_SAVE_ON_NEW_ADD = "mobile/visit/ar";
    /**
     * 拜访跟进详情页面 - 从列表item点击进入
     */
    public static final String GET_VISIT_RECORD_DETAIL = "mobile/visit/details";
    /**
     * 拜访跟进详情页面 - 结束的接口
     */
    public static final String TO_END = "mobile/visit/se";
    /**
     * 拜访跟进详情页面 - 列表页进入点击保存, 或者提交的接口
     */
    public static final String TO_SAVE_OR_COMMIT = "mobile/visit/ps";
}
