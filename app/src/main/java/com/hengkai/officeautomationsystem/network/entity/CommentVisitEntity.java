package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/5/17.
 */
public class CommentVisitEntity {

    /**
     * TOTAL : 3
     * DATE : [{"comment_time":"2018-05-17 15:30:59","function_name":"approval_bfgj","comment_content":"测试测试","createUserName":"裴淳","id":60,"object_id":77,"is_delete":false},{"comment_time":"2018-05-17 14:37:37","function_name":"approval_bfgj","comment_content":"测试。第2次","createUserName":"裴淳","id":56,"object_id":77,"is_delete":false},{"comment_time":"2018-05-17 14:34:49","function_name":"approval_bfgj","comment_content":"测试，第一次","createUserName":"裴淳","id":55,"object_id":77,"is_delete":false}]
     * CODE : 1
     * MES : 查询成功
     */

    public int TOTAL;
    public int CODE;
    public String MES;
    public List<DATEBean> DATE;

    public static class DATEBean {
        /**
         * comment_time : 2018-05-17 15:30:59
         * function_name : approval_bfgj
         * comment_content : 测试测试
         * createUserName : 裴淳
         * id : 60
         * object_id : 77
         * is_delete : false
         */

        public long comment_time;
        public String function_name;
        public String comment_content;
        public String createUserName;
        public int id;
        public int object_id;
        public boolean is_delete;
    }
}
