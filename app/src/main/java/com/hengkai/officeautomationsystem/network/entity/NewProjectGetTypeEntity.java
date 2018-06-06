package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/6/5.
 */
public class NewProjectGetTypeEntity {

    /**
     * DATE : [{"id":120,"value":"特殊项目"},{"id":86,"value":"个人项目"},{"id":85,"value":"公司制定项目"}]
     * CODE : 1
     * MES : 查询成功
     */

    public int CODE;
    public String MES;
    public List<DATEBean> DATE;

    public static class DATEBean {
        /**
         * id : 120
         * value : 特殊项目
         */

        public int id;
        public String value;
    }
}
