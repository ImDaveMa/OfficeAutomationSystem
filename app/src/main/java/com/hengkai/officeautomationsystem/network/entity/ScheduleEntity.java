package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class ScheduleEntity {

    /**
     * CODE : 1
     * DATA : [{"memoContent":"测试日程内容发","id":145,"memoEndDate":"2018-10-21 16:50:30","memoStartDate":"2018-10-20 15:50:30"}]
     * MES : 查询成功
     */

    public int CODE;
    public String MES;
    public List<ScheduleBean> DATA;

    public static class ScheduleBean {
        /**
         * memoContent : 测试日程内容发
         * id : 145
         * memoEndDate : 2018-10-21 16:50:30
         * memoStartDate : 2018-10-20 15:50:30
         */

        public int id;
        public String memoContent;
        public String memoEndDate;
        public String memoStartDate;
    }
}
