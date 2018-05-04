package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/5/4.
 */
public class ContactsEntity {

    /**
     * CODE : 1
     * MES : 查询成功
     * DIRECTORIES : [{"departmentName":"恒凯科技","departmentUserList":[{"id":1,"name":"裴淳","position":"JAVA","personalitySignature":null,"inspirationalSlogans":null,"iconLink":null,"qqNumber":null,"weixinNumber":null,"dingNumber":null,"phone":"18888888888","sparePhone":null,"email":"123@qq.com","createTime":1524129990000,"correctionTime":null,"govsectorId":16,"attendanceType":1,"startTime":1525134600000,"endTime":1525428000000},{"id":7,"name":"刘浩","position":"JAVA","personalitySignature":null,"inspirationalSlogans":null,"iconLink":null,"qqNumber":null,"weixinNumber":null,"dingNumber":null,"phone":"18888888888","sparePhone":null,"email":"123@qq.com","createTime":1524129990000,"correctionTime":null,"govsectorId":16,"attendanceType":null,"startTime":null,"endTime":null}]},{"departmentName":"技术部","departmentUserList":[{"id":3,"name":"许宁","position":"UI","personalitySignature":null,"inspirationalSlogans":null,"iconLink":null,"qqNumber":null,"weixinNumber":null,"dingNumber":null,"phone":"18888888888","sparePhone":null,"email":"123@qq.com","createTime":1524444094000,"correctionTime":null,"govsectorId":17,"attendanceType":null,"startTime":null,"endTime":null},{"id":4,"name":"常璐","position":"JAVA","personalitySignature":null,"inspirationalSlogans":null,"iconLink":null,"qqNumber":null,"weixinNumber":null,"dingNumber":null,"phone":"18888888888","sparePhone":null,"email":"123@qq.com","createTime":1524129990000,"correctionTime":null,"govsectorId":17,"attendanceType":1,"startTime":1524210660000,"endTime":1524210660000},{"id":5,"name":"杨帅","position":"JAVA","personalitySignature":null,"inspirationalSlogans":null,"iconLink":null,"qqNumber":null,"weixinNumber":null,"dingNumber":null,"phone":"18888888888","sparePhone":null,"email":"123@qq.com","createTime":1524129990000,"correctionTime":null,"govsectorId":17,"attendanceType":null,"startTime":null,"endTime":null},{"id":6,"name":"万瑞","position":"JAVA","personalitySignature":null,"inspirationalSlogans":null,"iconLink":null,"qqNumber":null,"weixinNumber":null,"dingNumber":null,"phone":"18888888888","sparePhone":null,"email":"123@qq.com","createTime":1524129990000,"correctionTime":null,"govsectorId":17,"attendanceType":null,"startTime":null,"endTime":null}]},{"departmentName":"销售部","departmentUserList":[{"id":2,"name":"朱哲峰","position":"行政","personalitySignature":null,"inspirationalSlogans":null,"iconLink":null,"qqNumber":null,"weixinNumber":null,"dingNumber":null,"phone":"18888888888","sparePhone":null,"email":"123@qq.com","createTime":1524130003000,"correctionTime":null,"govsectorId":18,"attendanceType":null,"startTime":null,"endTime":null}]},{"departmentName":"行政部","departmentUserList":[]}]
     */

    public int CODE;
    public String MES;
    public List<DIRECTORIESBean> DIRECTORIES;

    public static class DIRECTORIESBean {
        /**
         * departmentName : 恒凯科技
         * departmentUserList : [{"id":1,"name":"裴淳","position":"JAVA","personalitySignature":null,"inspirationalSlogans":null,"iconLink":null,"qqNumber":null,"weixinNumber":null,"dingNumber":null,"phone":"18888888888","sparePhone":null,"email":"123@qq.com","createTime":1524129990000,"correctionTime":null,"govsectorId":16,"attendanceType":1,"startTime":1525134600000,"endTime":1525428000000},{"id":7,"name":"刘浩","position":"JAVA","personalitySignature":null,"inspirationalSlogans":null,"iconLink":null,"qqNumber":null,"weixinNumber":null,"dingNumber":null,"phone":"18888888888","sparePhone":null,"email":"123@qq.com","createTime":1524129990000,"correctionTime":null,"govsectorId":16,"attendanceType":null,"startTime":null,"endTime":null}]
         */

        public String departmentName;
        public List<DepartmentUserListBean> departmentUserList;

        public static class DepartmentUserListBean {
            /**
             * id : 1
             * name : 裴淳
             * position : JAVA
             * personalitySignature : null
             * inspirationalSlogans : null
             * iconLink : null
             * qqNumber : null
             * weixinNumber : null
             * dingNumber : null
             * phone : 18888888888
             * sparePhone : null
             * email : 123@qq.com
             * createTime : 1524129990000
             * correctionTime : null
             * govsectorId : 16
             * attendanceType : 1
             * startTime : 1525134600000
             * endTime : 1525428000000
             */

            public int id;
            public String name;
            public String position;
            public String personalitySignature;
            public String inspirationalSlogans;
            public String iconLink;
            public String qqNumber;
            public String weixinNumber;
            public String dingNumber;
            public String phone;
            public String sparePhone;
            public String email;
            public long createTime;
            public long correctionTime;
            public int govsectorId;
            public int attendanceType;
            public long startTime;
            public long endTime;
        }
    }
}
