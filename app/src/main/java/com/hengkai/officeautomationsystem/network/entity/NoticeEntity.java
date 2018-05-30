package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class NoticeEntity {

    /**
     * TOTAL : 6
     * DATE : [{"readStatus":1,"createTime":1527587004000,"createUserName":"管理员","id":12,"noticeTypeName":"技术部","noticeTitle":"接口测试标题修","noticeSummary":"测试摘要"},{"readStatus":"","createTime":1527582970000,"createUserName":"管理员","id":11,"noticeTypeName":"恒凯科技","noticeTitle":"草稿修","noticeSummary":""},{"readStatus":"","createTime":1527575801000,"createUserName":"管理员","id":7,"noticeTypeName":"行政部","noticeTitle":"测试","noticeSummary":"测试测试12"},{"readStatus":"","createTime":1527499544000,"createUserName":"管理员","id":6,"noticeTypeName":"恒凯科技","noticeTitle":"测试公告内容图片","noticeSummary":""},{"readStatus":1,"createTime":1527318905000,"createUserName":"管理员","id":4,"noticeTypeName":"恒凯科技","noticeTitle":"测试星号","noticeSummary":"测试"},{"readStatus":1,"createTime":1527315329000,"createUserName":"管理员","id":3,"noticeTypeName":"恒凯科技","noticeTitle":"我好好好好好好好好好我好好好好好好好好好好说123123","noticeSummary":"1111111"}]
     * CODE : 1
     * MES : 查询成功
     */

    public int TOTAL;
    public int CODE;
    public String MES;
    public List<DATEBean> DATE;

    public static class DATEBean {
        /**
         * readStatus : 1
         * createTime : 1527587004000
         * createUserName : 管理员
         * id : 12
         * noticeTypeName : 技术部
         * noticeTitle : 接口测试标题修
         * noticeSummary : 测试摘要
         */

        public String readStatus;
        public long createTime;
        public String createUserName;
        public int id;
        public String noticeTypeName;
        public String noticeTitle;
        public String noticeSummary;
    }
}
