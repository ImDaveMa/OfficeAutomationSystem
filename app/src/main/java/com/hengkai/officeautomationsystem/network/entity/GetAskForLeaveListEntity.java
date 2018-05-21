package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/5/21.
 */
public class GetAskForLeaveListEntity {

    /**
     * CODE : 1
     * DATA : [{"approval_information_id":277,"reason":"阿萨德","start_time":1526868300000,"create_time":1526868365000,"leave_type":1,"end_time":1526871900000,"name":"管理员","id":109,"time":1,"state":0,"attendance_type":1},{"approval_information_id":276,"reason":"阿萨德","start_time":1526868240000,"create_time":1526868301000,"leave_type":3,"end_time":1526911200000,"name":"管理员","id":108,"time":6.4,"state":0,"attendance_type":1},{"approval_information_id":275,"reason":"阿萨德","start_time":1526868120000,"create_time":1526868177000,"leave_type":2,"end_time":1526911200000,"name":"管理员","id":107,"time":6.4,"state":0,"attendance_type":1},{"approval_information_id":180,"reason":"123","start_time":1526227200000,"create_time":1526279867000,"leave_type":2,"end_time":1526313600000,"name":"管理员","id":103,"time":8,"state":0,"attendance_type":1},{"approval_information_id":179,"reason":"1","start_time":1526227200000,"create_time":1526279639000,"leave_type":1,"end_time":1526227200000,"name":"管理员","id":102,"time":0,"state":3,"attendance_type":1},{"approval_information_id":178,"reason":"1","start_time":1526227200000,"create_time":1526279639000,"leave_type":1,"end_time":1526227200000,"name":"管理员","id":101,"time":0,"state":0,"attendance_type":1},{"approval_information_id":177,"reason":"1","start_time":1526227200000,"create_time":1526279620000,"leave_type":1,"end_time":1526227200000,"name":"管理员","id":100,"time":0,"state":0,"attendance_type":1},{"approval_information_id":176,"reason":"123","start_time":1526313600000,"create_time":1526279403000,"leave_type":2,"end_time":1526313600000,"name":"管理员","id":99,"time":0,"state":3,"attendance_type":1},{"approval_information_id":175,"reason":"事假","start_time":1526227200000,"create_time":1526279378000,"leave_type":2,"end_time":1526313600000,"name":"管理员","id":98,"time":8,"state":3,"attendance_type":1},{"approval_information_id":164,"reason":"事假","start_time":1526227200000,"create_time":1526262818000,"leave_type":1,"end_time":1526313600000,"name":"管理员","id":91,"time":8,"state":0,"attendance_type":1}]
     * MES : 查询成功
     */
    public int CODE;
    public String MES;
    public List<DATABean> DATA;

    public static class DATABean {
        /**
         * approval_information_id : 277
         * reason : 阿萨德
         * start_time : 1526868300000
         * create_time : 1526868365000
         * leave_type : 1
         * end_time : 1526871900000
         * name : 管理员
         * id : 109
         * time : 1
         * state : 0
         * attendance_type : 1
         */

        public int approval_information_id;
        public String reason;
        public long start_time;
        public long create_time;
        /**
         * 1零星假2事假3病假4婚假5产假6陪产假7丧假
         */
        public int leave_type;
        public long end_time;
        public String name;
        public int id;
        public int time;
        /**
         * 0未审批 1通过 2未通过 3已撤销
         */
        public int state;
        /**
         * 1请假2外出3补卡
         */
        public int attendance_type;
    }
}
