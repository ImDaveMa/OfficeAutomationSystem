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
//    public static final String BASE_URL = "http://192.168.2.157:8080/" + VERSION + "OA_inside/mobile/";
    public static final String BASE_URL = "http://192.168.2.157:8080/OA_inside/mobile/";
//    public static final String BASE_URL = "http://192.168.2.105:8080/OA_inside/mobile/";


    /**
     * 登录
     */
    public static final String LOGIN_URL = "login/password";
    /**
     * 获取待办事项列表
     */
    public static final String GET_APPROVE_LIST = "mapprovalInf/toList";
    /**
     * 获取消息列表
     */
    public static final String GET_MSG_LIST = "mapprovalInf/newstoList";

    /**
     * 获取联系人列表
     */
    public static final String GET_CONTACTS_LIST = "user/selectDirectories";

    /**
     * 获取单位库列表
     */
    public static final String GET_UNIT_LIST = "conAcom/comList";
    /**
     * 单位列表的详情
     */
    public static final String GET_UNIT_DETAIL = "conAcom/comDetails";
    /**
     * 项目 - 联系人库列表
     */
    public static final String GET_CONTACTS_LIBRARY_LIST = "conAcom/conList";

    /**
     * 获取拜访跟进列表
     */
    public static final String GET_VISIT_RECORD_LIST = "visit/list";
    /**
     * 拜访跟进列表 - 删除未提交的item
     */
    public static final String VISIT_RECORD_ACTIVITY_DELETE_ITEM = "visit/del";

    /**
     * 获取物品列表
     */
    public static final String GET_GOODS_LIST = "mgoods/toGoodsList";
    /**
     * 删除物品列表
     */
    public static final String DELETE_GOODS_LIST = "mgoods/doDelete";
    /**
     * 领用物品
     */
    public static final String USE_GOODS = "out/insertOutStorage";
    /**
     * 领用物品详情
     */
    public static final String USE_GOODS_DETAIL = "out/mOutDetail";
    /**
     * 领用物品 参数请求
     */
    public static final String USE_GOODS_PARAMS = "out/insertOutPage";
    /**
     * 获取物品领用列表
     */
    public static final String GET_USE_GOODS_LIST = "out/mOutList";
    /**
     * 物品单位列表
     */
    public static final String GOODS_UNIT_LIST = "mgoods/toUnitList";
    /**
     * 新增物品单位
     */
    public static final String ADD_GOODS_UNIT = "mgoods/doAddUnit";
    /**
     * 新增物品单位
     */
    public static final String DELETE_GOODS_UNIT = "mgoods/doDelUnit";
    /**
     * 物品供货商列表
     */
    public static final String GOODS_SUPPLIER_LIST = "msupplier/toSupplierList";
    /**
     * 获取物品入库列表
     */
    public static final String GET_GOODS_IN_LIST = "mIn/mInList";
    /**
     * 物品入库 参数请求
     */
    public static final String GET_GOODS_IN_PARAMS = "mIn/insertInPage";
    /**
     * 物品入库
     */
    public static final String GOODS_IN_URL = "mIn/insertInStorage";
    /**
     * 物品入库详情
     */
    public static final String GOODS_IN_DETAIL_URL = "mIn/mOutDetail";
    /**
     * 物品详情
     */
    public static final String GOODS_DETAIL_URL = "mgoods/toDetail";
    /**
     * 拜访跟进详情页面 - 获取单位列表
     */
    public static final String GET_VISIT_UNIT_LIST = "visit/companyList";
    /**
     * 拜访跟进详情页面 - 获取该单位下的联系人列表
     */
    public static final String GET_VISIT_UNIT_CUSTOMER_LIST = "visit/companyDetails";
    /**
     * 拜访跟进详情页面 - 获取该联系人下的项目列表
     */
    public static final String GET_VISIT_UNIT_PROJECT_LIST = "visit/contactsDetails";
    /**
     * 拜访跟进详情页面 - 从新增按钮进入到当前页面去保存当前页面
     */
    public static final String TO_SAVE_ON_NEW_ADD = "visit/ar";
    /**
     * 拜访跟进详情页面 - 从列表item点击进入
     */
    public static final String GET_VISIT_RECORD_DETAIL = "visit/details";
    /**
     * 拜访跟进详情页面 - 结束的接口
     */
    public static final String TO_END = "visit/se";
    /**
     * 拜访跟进详情页面 - 列表页进入点击保存, 或者提交的接口
     */
    public static final String TO_SAVE_OR_COMMIT = "visit/ps";
    /**
     * 新增单位 - 获取关键词列表
     */
    public static final String GET_KEYWORD_LIST = "conAcom/keyType";
    /**
     * 新增单位 - 获取类型列表
     */
    public static final String GET_TYPE_LIST = "conAcom/comType";
    /**
     * 新增单位 - 提交
     */
    public static final String NEW_UNIT_COMMIT = "conAcom/ar";
    /**
     * 项目列表
     */
    public static final String GET_PROJECT_LIST = "mproject/toList";
    /**
     * 获取项目库详情
     */
    public static final String GET_PROJECT_DETAIL = "mproject/getDetailProject";
    /**
     * 我的单位
     */
    public static final String MY_UNIT_GET_UNIT_LIST = "conAcom/userList";
    /**
     * 联系人库列表详情
     */
    public static final String GET_CONTACTS_DETAIL = "conAcom/conDetails";
    /**
     * 拜访跟进 - 评论列表
     */
    public static final String GET_COMMENT_LIST = "mcomment/commentList";
    /**
     * 拜访跟进 - 评论
     */
    public static final String VISIT_COMMENT = "mcomment/insertComment";
    /**
     * 员工 - 报表列表(周报日报)
     */
    public static final String GET_REPORT_LIST = "report/toList";
    /**
     * 员工 - 新增周报或者日报的页面 - 选择发送给谁的接口
     */
    public static final String GET_GROUP_MEMBER_LIST = "report/selectTheDepartmentEveryone";
    /**
     * 员工 - 新增周报或者日报的页面 - 提交
     */
    public static final String ADD_REPORT = "report/insertReport";
    /**
     * 添加物品 参数请求
     */
    public static final String ADD_GOODS_PARAMS = "mgoods/toInsert";
    /**
     * 新增物品
     */
    public static final String ADD_GOODS_URL = "mgoods/doInsert";
    /**
     * 修改物品 参数请求
     */
    public static final String UPDATE_GOODS_PARAMS = "mgoods/toUpdate";
    /**
     * 修改物品
     */
    public static final String UPDATE_GOODS_URL = "mgoods/doUpdate";
    /**
     * 新增请假外出的接口
     */
    public static final String ADD_GO_OUT = "mattendance/addAttendance";
    /**
     * 计算请假或者外出时长的接口
     */
    public static final String DURATION = "mattendance/computationTime";
}
