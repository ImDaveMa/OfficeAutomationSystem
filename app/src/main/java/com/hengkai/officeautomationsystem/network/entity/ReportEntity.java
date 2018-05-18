package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/5/18.
 */
public class ReportEntity {

    /**
     * DATE : [{"createUserId":1,"presentContent":"今天任务","createTime":1526378407000,"isDelete":false,"remark":"1,2","id":39,"demandContent":"协调","type":"0","nextContent":"下部计划","userName":"管理员"},{"createUserId":1,"presentContent":"今天任务","createTime":1526523827000,"isDelete":false,"remark":"1,2","id":38,"demandContent":"协调","type":"0","nextContent":"下部计划","userName":"管理员"},{"createUserId":1,"presentContent":"我在鼓楼的夜色中为你唱花香自来","createTime":1526364831000,"isDelete":false,"remark":"","id":37,"demandContent":"幺幺","type":"0","nextContent":" 灯火阑珊处","userName":"管理员"},{"createUserId":1,"presentContent":"23123","createTime":1526023137000,"isDelete":false,"remark":"","id":35,"demandContent":"321312","type":"0","nextContent":" 1231231","userName":"管理员"},{"createUserId":1,"presentContent":"123123","createTime":1526023115000,"isDelete":false,"remark":"","id":34,"demandContent":"312312","type":"0","nextContent":" 123123","userName":"管理员"},{"createUserId":1,"presentContent":"312321","createTime":1526023103000,"isDelete":false,"remark":"","id":33,"demandContent":"123123123","type":"0","nextContent":"1312312","userName":"管理员"},{"createUserId":1,"presentContent":"21321","createTime":1526022935000,"isDelete":false,"remark":"","id":32,"demandContent":"312312312","type":"0","nextContent":" ","userName":"管理员"},{"createUserId":1,"presentContent":"defaultid","createTime":1525504083000,"isDelete":false,"remark":"1,7","id":29,"demandContent":"defaultid","type":"0","nextContent":" defaultid","userName":"管理员"},{"createUserId":1,"presentContent":"今日","createTime":1525503419000,"isDelete":false,"remark":"","id":28,"demandContent":"协调","type":"0","nextContent":" 下部","userName":"管理员"},{"createUserId":1,"presentContent":"今日完成","createTime":1525503333000,"isDelete":false,"remark":"","id":27,"demandContent":"协调","type":"0","nextContent":"下部计划 ","userName":"管理员"}]
     * CODE : 1
     * MES : 查询成功
     */

    public int CODE;
    public String MES;
    public List<DATEBean> DATE;

    public static class DATEBean {
        /**
         * createUserId : 1
         * presentContent : 今天任务
         * createTime : 1526378407000
         * isDelete : false
         * remark : 1,2
         * id : 39
         * demandContent : 协调
         * type : 0
         * nextContent : 下部计划
         * userName : 管理员
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
    }
}
