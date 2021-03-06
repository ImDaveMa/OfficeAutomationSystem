package com.hengkai.officeautomationsystem.final_constant;

/**
 * Created by Administrator on 2018/5/3.
 */

public class CommonFinal {

    /**
     * 公用页面传值：菜单ID
     * 用来确定sqlite数据库中对应的菜单
     */
    public static final String MENU_ID = "MenuId";

    /**
     * 公用分页每页数量
     */
    public static final int PAGE_SIZE = 15;
    /**
     * 拜访跟进页面的ResultCode
     */
    public static final int VISIT_RECORD_RESULT_CODE = 1001;
    /**
     * 拜访跟进页面的RequestCode
     */
    public static final int VISIT_RECORD_REQUEST_CODE = 1002;
    /**
     * 通用的请求码
     */
    public static final int COMMON_REQUEST_CODE = 1003;
    /**
     * 通用的结果码
     */
    public static final int COMMON_RESULT_CODE = 1004;
    /**
     * 拜访跟进详情页面 - 选择拜访单位和拜访人的请求码和结果码
     */
    public static final int SELECT_VISIT_UNIT_REQUEST_CODE = 1005;
    public static final int SELECT_VISIT_UNIT_RESULT_CODE = 1006;
    public static final int SELECT_VISIT_PERSON_REQUEST_CODE = 1007;
    public static final int SELECT_VISIT_PERSON_RESULT_CODE = 1008;
    public static final int SELECT_VISIT_PROJECT_REQUEST_CODE = 1012;
    public static final int SELECT_VISIT_PROJECT_RESULT_CODE = 1011;
    /**
     * 添加联系人的请求码和结果码
     */
    public static final int ADD_CONTACT_REQUEST_CODE = 1009;
    public static final int ADD_CONTACT_RESULT_CODE = 1010;

    /**
     * 是否是从拜访跟新打开的Activity的：Extra数据Key
     */
    public static final String EXTRA_KEY_OPEN_BY_VISIT = "OPEN_BY_VISIT";
}
