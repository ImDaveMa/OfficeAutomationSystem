package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/5/11.
 */
public class ContactsLibraryEntity {

    /**
     * CODE : 1
     * DATA : [{"birthday":"","companyName":"曙光计算机产业有限公司","sex":true,"companyId":18,"mailbox":"","phone":"13738828381","createTime":1525934835000,"name":"赵强","rank":"","id":24,"projectName":null,"department":"实验室","projectId":null},{"birthday":"2018-05-12","companyName":"曙光计算机产业有限公司","sex":true,"companyId":18,"mailbox":"","phone":"","createTime":1525684792000,"name":"王瑞","rank":"","id":21,"projectName":null,"department":"研发室","projectId":null},{"birthday":"1976-9-6","companyName":"恒凯","sex":true,"companyId":1,"mailbox":"99@163.com","phone":"18888888888","createTime":1524906673000,"name":"刘邦","rank":"陛下","id":19,"projectName":null,"department":"汉朝","projectId":null},{"birthday":"1996-3-6","companyName":"洛阳市商务局","sex":true,"companyId":2,"mailbox":"8888888@qq.com","phone":"15788888888","createTime":1524453571000,"name":"乾隆","rank":"皇上","id":18,"projectName":"玉帝的项目","department":"清朝","projectId":3},{"birthday":"1992-06-23","companyName":"恒凯","sex":true,"companyId":1,"mailbox":"88888@163.com","phone":"159752156","createTime":1524301721000,"name":"玉帝","rank":"一品","id":12,"projectName":"项目1","department":"东方神话","projectId":1},{"birthday":"2018-03-01","companyName":"恒凯","sex":true,"companyId":1,"mailbox":"yvdi@qq.com","phone":"1889999000","createTime":1524203003000,"name":"张百忍","rank":"玉皇大帝","id":1,"projectName":"项目1","department":"天庭","projectId":1}]
     * MES : 查询成功
     */

    public int CODE;
    public String MES;
    public List<DATABean> DATA;

    public static class DATABean {
        /**
         * birthday :
         * companyName : 曙光计算机产业有限公司
         * sex : true
         * companyId : 18
         * mailbox :
         * phone : 13738828381
         * createTime : 1525934835000
         * name : 赵强
         * rank :
         * id : 24
         * projectName : null
         * department : 实验室
         * projectId : null
         */

        public String birthday;
        public String companyName;
        public boolean sex;
        public int companyId;
        public String mailbox;
        public String phone;
        public long createTime;
        public String name;
        public String rank;
        public int id;
        public String projectName;
        public String department;
        public int projectId;
    }
}
