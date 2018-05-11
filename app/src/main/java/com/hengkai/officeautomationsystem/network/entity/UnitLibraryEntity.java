package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/5/5.
 */
public class UnitLibraryEntity {

    /**
     * CODE : 1
     * DATA : [{"id":25,"name":"测试","type":"政府","creditCode":"","address":"西工区","keywordId":null,"website":"","contract":null,"userId":null,"isChange":null,"createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"isDelete":null,"text":null,"remarks":null},{"id":18,"name":"曙光计算机产业有限公司","type":"国有企业","creditCode":"","address":"天津市西青区海泰新技术产业园区华科大街15号","keywordId":null,"website":"","contract":null,"userId":null,"isChange":null,"createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"isDelete":null,"text":null,"remarks":null},{"id":6,"name":"深圳市腾讯计算机系统有限公司","type":"政府","creditCode":"","address":"深圳市南山区高新科技园中区一路腾讯大厦","keywordId":null,"website":"http://www.tencent.com/","contract":null,"userId":null,"isChange":null,"createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"isDelete":null,"text":null,"remarks":null},{"id":5,"name":"深圳市腾讯计算机系统有限公司","type":"国有企业","creditCode":"","address":"深圳市南山区高新科技园中区一路腾讯大厦","keywordId":null,"website":"http://www.tencent.com/","contract":null,"userId":null,"isChange":null,"createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"isDelete":null,"text":null,"remarks":null},{"id":4,"name":"网易公司","type":"民营企业","creditCode":"","address":":广州市天河区科韵路16号广州信息港E栋网易大厦","keywordId":null,"website":"http://www.163.com/","contract":null,"userId":null,"isChange":null,"createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"isDelete":null,"text":null,"remarks":null},{"id":3,"name":"洛阳市恒凯信息科技有限公司","type":"事业单位","creditCode":"91410303767827070J","address":"洛阳市西工区王城大道111号紫金城02区1单元","keywordId":null,"website":"www.baidu.com","contract":null,"userId":null,"isChange":null,"createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"isDelete":null,"text":null,"remarks":null},{"id":2,"name":"洛阳市商务局","type":"国家行政机关","creditCode":"","address":"洛阳市人民政府党政办公大楼8楼","keywordId":null,"website":"www.lysswj.gov.cn/","contract":null,"userId":null,"isChange":null,"createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"isDelete":null,"text":null,"remarks":null},{"id":1,"name":"恒凯","type":"政府","creditCode":"123456","address":"王城大道","keywordId":null,"website":"www.baidu.com","contract":null,"userId":null,"isChange":null,"createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"isDelete":null,"text":null,"remarks":null}]
     * MES : 查询成功
     */

    public int CODE;
    public String MES;
    public List<DATABean> DATA;

    public static class DATABean {
        /**
         * id : 25
         * name : 测试
         * type : 政府
         * creditCode :
         * address : 西工区
         * keywordId : null
         * website :
         * contract : null
         * userId : null
         * isChange : null
         * createTime : null
         * createUser : null
         * updateTime : null
         * updateUser : null
         * isDelete : null
         * text : null
         * remarks : null
         */

        public int id;
        public String name;
        public String type;
        public String creditCode;
        public String address;
        public Object keywordId;
        public String website;
        public Object contract;
        public Object userId;
        public Object isChange;
        public Object createTime;
        public Object createUser;
        public Object updateTime;
        public Object updateUser;
        public Object isDelete;
        public Object text;
        public Object remarks;
    }
}
