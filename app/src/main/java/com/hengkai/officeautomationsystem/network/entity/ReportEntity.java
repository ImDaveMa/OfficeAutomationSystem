package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/5/18.
 */
public class ReportEntity {

    /**
     * DATE : [{"createUserId":1,"presentContent":"放大说","createTime":1527132488000,"isDelete":false,"commnet":[{"id":175,"commentType":null,"objectId":54,"functionName":"sys_report","replyId":null,"commentContent":"11","createUserId":null,"commentTime":1527211557000,"updateUserId":null,"updateTime":null,"isDelete":false,"isState":0,"createUserName":"管理员","userLink":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"}],"remark":"1,7","id":54,"demandContent":"发达","type":"1","nextContent":"俄服务","userName":"管理员","headPortrait":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"},{"createUserId":1,"presentContent":" a  s    d","createTime":1526364688000,"isDelete":false,"commnet":[{"id":123,"commentType":null,"objectId":36,"functionName":"sys_report","replyId":null,"commentContent":"阿达说的","createUserId":null,"commentTime":1526867659000,"updateUserId":null,"updateTime":null,"isDelete":false,"isState":0,"createUserName":"管理员","userLink":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"},{"id":124,"commentType":null,"objectId":36,"functionName":"sys_report","replyId":null,"commentContent":"","createUserId":null,"commentTime":1526867667000,"updateUserId":null,"updateTime":null,"isDelete":false,"isState":0,"createUserName":"管理员","userLink":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"},{"id":125,"commentType":null,"objectId":36,"functionName":"sys_report","replyId":null,"commentContent":"阿萨德","createUserId":null,"commentTime":1526867675000,"updateUserId":null,"updateTime":null,"isDelete":false,"isState":0,"createUserName":"管理员","userLink":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"},{"id":126,"commentType":null,"objectId":36,"functionName":"sys_report","replyId":null,"commentContent":"","createUserId":null,"commentTime":1526868119000,"updateUserId":null,"updateTime":null,"isDelete":false,"isState":0,"createUserName":"管理员","userLink":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"},{"id":127,"commentType":null,"objectId":36,"functionName":"sys_report","replyId":null,"commentContent":"啊实打实","createUserId":null,"commentTime":1526868152000,"updateUserId":null,"updateTime":null,"isDelete":false,"isState":0,"createUserName":"管理员","userLink":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"},{"id":128,"commentType":null,"objectId":36,"functionName":"sys_report","replyId":null,"commentContent":"阿萨德","createUserId":null,"commentTime":1526868171000,"updateUserId":null,"updateTime":null,"isDelete":false,"isState":0,"createUserName":"管理员","userLink":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"},{"id":132,"commentType":null,"objectId":36,"functionName":"sys_report","replyId":null,"commentContent":"啊实打实大","createUserId":null,"commentTime":1526868285000,"updateUserId":null,"updateTime":null,"isDelete":false,"isState":0,"createUserName":"管理员","userLink":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"},{"id":136,"commentType":null,"objectId":36,"functionName":"sys_report","replyId":null,"commentContent":"额2额2","createUserId":null,"commentTime":1526869458000,"updateUserId":null,"updateTime":null,"isDelete":false,"isState":0,"createUserName":"管理员","userLink":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"},{"id":148,"commentType":null,"objectId":36,"functionName":"sys_report","replyId":null,"commentContent":"312312","createUserId":null,"commentTime":1526869556000,"updateUserId":null,"updateTime":null,"isDelete":false,"isState":0,"createUserName":"管理员","userLink":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"}],"remark":"","id":36,"demandContent":"123","type":"1","nextContent":" bgh","userName":"管理员","headPortrait":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"},{"createUserId":1,"presentContent":"123123","createTime":1526006564000,"isDelete":false,"commnet":[{"id":133,"commentType":null,"objectId":31,"functionName":"sys_report","replyId":null,"commentContent":"阿大东区","createUserId":null,"commentTime":1526868515000,"updateUserId":null,"updateTime":null,"isDelete":false,"isState":0,"createUserName":"管理员","userLink":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"}],"remark":"","id":31,"demandContent":"12312312312","type":"1","nextContent":" 123213123","userName":"管理员","headPortrait":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"},{"createUserId":1,"presentContent":"今天完成的有:已完成的工作,","createTime":1526002361000,"isDelete":false,"commnet":[],"remark":"1,7","id":30,"demandContent":"我试试是","type":"1","nextContent":"未完成的任务有:,工作描述2,工作描述2,工作描述2,工作描述2,工作描述2,工作描述2,工作描述2,不重要的工作,dasd","userName":"管理员","headPortrait":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"},{"createUserId":1,"presentContent":"本周完成的","createTime":1524710623000,"isDelete":false,"commnet":[],"remark":"","id":25,"demandContent":"资源","type":"1","nextContent":" 下周计划","userName":"管理员","headPortrait":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"}]
     * CODE : 1
     * MES : 查询成功
     */

    public int CODE;
    public String MES;
    public List<DATEBean> DATE;

    public static class DATEBean {
        /**
         * createUserId : 1
         * presentContent : 放大说
         * createTime : 1527132488000
         * isDelete : false
         * commnet : [{"id":175,"commentType":null,"objectId":54,"functionName":"sys_report","replyId":null,"commentContent":"11","createUserId":null,"commentTime":1527211557000,"updateUserId":null,"updateTime":null,"isDelete":false,"isState":0,"createUserName":"管理员","userLink":"http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg"}]
         * remark : 1,7
         * id : 54
         * demandContent : 发达
         * type : 1
         * nextContent : 俄服务
         * userName : 管理员
         * headPortrait : http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg
         */

        public int createUserId;
        public String presentContent;
        public long createTime;
        public boolean isDelete;
        public String remark;
        public int id;
        public String demandContent;
        public String type;
        public String nextContent;
        public String userName;
        public String headPortrait;
        public List<CommnetBean> commnet;

        public static class CommnetBean {
            /**
             * id : 175
             * commentType : null
             * objectId : 54
             * functionName : sys_report
             * replyId : null
             * commentContent : 11
             * createUserId : null
             * commentTime : 1527211557000
             * updateUserId : null
             * updateTime : null
             * isDelete : false
             * isState : 0
             * createUserName : 管理员
             * userLink : http://192.168.2.157:8080/oa/file/20180531134333216_476.jpeg
             */

            public int id;
            public Object commentType;
            public int objectId;
            public String functionName;
            public Object replyId;
            public String commentContent;
            public Object createUserId;
            public long commentTime;
            public Object updateUserId;
            public Object updateTime;
            public boolean isDelete;
            public int isState;
            public String createUserName;
            public String userLink;
        }
    }
}
