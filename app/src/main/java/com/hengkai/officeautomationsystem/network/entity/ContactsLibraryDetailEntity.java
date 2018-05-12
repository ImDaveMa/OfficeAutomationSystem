package com.hengkai.officeautomationsystem.network.entity;

/**
 * Created by Harry on 2018/5/12.
 */
public class ContactsLibraryDetailEntity {

    /**
     * CODE : 1
     * DATA : {"birthday":"2018-03-01","isDelete":false,"companyName":"恒凯","sex":true,"wechat":"116031566kk","updateUser":1,"updateTime":1525745091000,"companyId":1,"mailbox":"yvdi@qq.com","licensePlate":"豫C 88888","phone":"1889999000","createTime":1524203003000,"name":"张百忍","rank":"玉皇大帝","createUser":1,"proName":"ceshiyixia2333,测试流程2","id":1,"landline":"0379-6356878","department":"天庭","projectId":1,"remarks":null,"age":null,"hobby":"人分公司的"}
     * MES : 查询成功
     */

    public int CODE;
    public DATABean DATA;
    public String MES;

    public static class DATABean {
        /**
         * birthday : 2018-03-01
         * isDelete : false
         * companyName : 恒凯
         * sex : true
         * wechat : 116031566kk
         * updateUser : 1
         * updateTime : 1525745091000
         * companyId : 1
         * mailbox : yvdi@qq.com
         * licensePlate : 豫C 88888
         * phone : 1889999000
         * createTime : 1524203003000
         * name : 张百忍
         * rank : 玉皇大帝
         * createUser : 1
         * proName : ceshiyixia2333,测试流程2
         * id : 1
         * landline : 0379-6356878
         * department : 天庭
         * projectId : 1
         * remarks : null
         * age : null
         * hobby : 人分公司的
         */

        public String birthday;
        public boolean isDelete;
        public String companyName;
        public boolean sex;
        public String wechat;
        public int updateUser;
        public long updateTime;
        public int companyId;
        public String mailbox;
        public String licensePlate;
        public String phone;
        public long createTime;
        public String name;
        public String rank;
        public int createUser;
        public String proName;
        public int id;
        public String landline;
        public String department;
        public int projectId;
        public Object remarks;
        public Object age;
        public String hobby;
    }
}
