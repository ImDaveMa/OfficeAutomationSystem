package com.hengkai.officeautomationsystem.final_constant;

import com.hengkai.officeautomationsystem.application.OfficeAutomationSystemApplication;
import com.hengkai.officeautomationsystem.utils.VersionUtils;

/**
 * Created by Harry on 2018/4/17.
 * 存储url地址
 */
public class URLFinal {

    /**
     * 版本控制
     */
    public static final String VERSION = VersionUtils.getVerName(OfficeAutomationSystemApplication.getAppContext());
    /**
     * baseURL
     */
//    public static final String BASE_URL = "http://192.168.2.157:8080/OA_inside/mobile/" + VERSION + "/";
//    public static final String BASE_URL = "http://192.168.2.22:8080/OA2.0/mobile/" + VERSION + "/";
//    public static final String BASE_URL = "http://oa.lyyisun.com:8042/mobile/" + VERSION + "/";
//    public static final String BASE_URL = "http://oa.hkkj.net:8043/mobile/" + VERSION + "/";
    public static final String BASE_URL = "http://192.168.2.199:8081/OA2.0/mobile/" + VERSION + "/";


    /**
     * 检测版本号
     */
    public static final String CHECK_VERSION = "version/getLatestVersion";
    /**
     * 下载新的版本信息
     */
    public static final String DOWNLOAD_NEW_VERSION = "http://oa.lyyisun.com:8042/oa/file/app/hkoa.apk";
    /**
     * 登录
     */
    public static final String LOGIN_URL = "login/password";
    /**
     * 登出
     */
    public static final String LOGOUT_URL = "login/logout";
    /**
     * 获取待办事项列表
     */
    public static final String GET_APPROVE_LIST = "mapprovalInf/toList";
    /**
     * 获取消息列表
     */
    public static final String GET_MSG_LIST = "mapprovalInf/newstoList";
    /**
     * 获取首页通知公告列表
     */
    public static final String GET_HOME_NOTICE_LIST = "mnotice/getHomeNotice";
    /**
     * 获取通知公告列表
     */
    public static final String GET_NOTICE_LIST = "mnotice/getNoticeUserListLimit";
    /**
     * 获取通知公告详情
     */
    public static final String GET_NOTICE_DETAIL = "mnotice/getUserNotice";

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
     * 拜访跟进接口整合(保存 开始 结束 提交)
     */
    public static final String VISIT_RECORD_SUBMISSION_DATA = "visit/submissionData";
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
     * 拜访跟进详情页面 - 从列表item点击进入
     */
    public static final String GET_VISIT_RECORD_DETAIL = "visit/details";
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
     * 获取供货商详情
     */
    public static final String GET_GOODS_SUPPLIER_DETAIL = "msupplier/toDetail";
    /**
     * 我的单位
     */
    public static final String MY_UNIT_GET_UNIT_LIST = "conAcom/userList";
    /**
     * 联系人库列表详情
     */
    public static final String GET_CONTACTS_DETAIL = "conAcom/conDetails";
    /**
     * 评论列表
     */
    public static final String GET_COMMENT_LIST = "mcomment/commentList";
    /**
     * 拜访跟进审批接口
     */
    public static final String GO_TO_APPROVAL = "mapprovalInf/updateApproval";
    /**
     * 评论
     */
    public static final String COMMENT_URL = "mcomment/insertComment";
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
     * 新增物品供应商页面参数
     */
    public static final String ADD_GOODS_SUPPLIER_PARAMS = "msupplier/toInsert";
    /**
     * 新增物品供应商
     */
    public static final String ADD_GOODS_SUPPLIER_URL = "msupplier/doInsert";
    /**
     * 新增项目联系人
     */
    public static final String ADD_CONTACT_URL = "conAcom/arCon";
    /**
     * 修改物品供应商页面参数
     */
    public static final String UPDATE_GOODS_SUPPLIER_PARAMS = "msupplier/toUpdate";
    /**
     * 修改物品供应商
     */
    public static final String UPDATE_GOODS_SUPPLIER_URL = "msupplier/doUpdate";
    /**
     * 新增请假外出的接口
     */
    public static final String ADD_GO_OUT = "mattendance/addAttendance";
    /**
     * 计算请假或者外出时长的接口
     */
    public static final String DURATION = "mattendance/computationTime";
    /**
     * 外出 - 获取抄送人和审批人
     */
    public static final String GO_OUT_GET_PERSON = "mattendance/selectApprovalPeoplewcsp";
    /**
     * 请假 - 获取抄送人和审批人
     */
    public static final String ASK_FOR_LEAVE_GET_PERSON = "mattendance/selectApprovalPeopleqjsp";
    /**
     * 获取请假记录
     */
    public static final String GET_ASK_FOR_LEAVE_LIST = "mattendance/toList";

    /**
     * 新增项目 - 获取项目类型
     */
    public static final String NEW_PROJECT_GET_TYPE = "mparam/getParamName";
    /**
     * 新增项目 - 获取项目负责人
     */
    public static final String NEW_PROJECT_GET_PERSON = "mparam/getUserName";
    /**
     * 新增项目 - 提交
     */
    public static final String NEW_PROJECT_COMMIT = "mproject/addOrUpdate";
    /**
     * 修改密码
     */
    public static final String CHANGE_PASSWORD = "user/updatePwd";
}
