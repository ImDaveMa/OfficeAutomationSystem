package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/6/9.
 */
public class GoOutEntity {

    /**
     * MSG : 查询成功
     * CODE : 1
     * DATA : [{"iconLink":null,"type":1,"userName":"朱哲峰"},{"iconLink":"http://192.168.2.157:8080/oa/file/20180530174748110_811.jpeg","type":1,"userName":"许宁"},{"iconLink":null,"type":2,"userName":"裴淳"}]
     */

    public String MSG;
    public int CODE;
    public List<DATABean> DATA;

    public static class DATABean {
        /**
         * iconLink : null
         * type : 1
         * userName : 朱哲峰
         */

        public String iconLink;
        public int type;
        public String userName;
    }
}
