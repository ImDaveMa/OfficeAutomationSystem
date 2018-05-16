package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class MessageEntity {

    /**
     * TOTAL : 1
     * DATE : [{"news_type":0,"createTime":"May 15, 2018 2:30:15 PM","project_id":99,"adoptName":"","typeName":"报销审批","id":207,"state":0,"project_name":"sys_expense_account","type":"approval_bxsp","createName":"裴淳"}]
     * CODE : 1
     * MES : 查询成功
     */

    private int TOTAL;
    private int CODE;
    private String MES;
    private List<MsgBean> DATE;

    public int getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(int TOTAL) {
        this.TOTAL = TOTAL;
    }

    public int getCODE() {
        return CODE;
    }

    public void setCODE(int CODE) {
        this.CODE = CODE;
    }

    public String getMES() {
        return MES;
    }

    public void setMES(String MES) {
        this.MES = MES;
    }

    public List<MsgBean> getDATE() {
        return DATE;
    }

    public void setDATE(List<MsgBean> DATE) {
        this.DATE = DATE;
    }

    public static class MsgBean {
        /**
         * news_type : 0
         * createTime : May 15, 2018 2:30:15 PM
         * project_id : 99
         * adoptName :
         * typeName : 报销审批
         * id : 207
         * state : 0
         * project_name : sys_expense_account
         * type : approval_bxsp
         * createName : 裴淳
         */

        private int news_type;
        private long createTime;
        private int project_id;
        private String adoptName;
        private String typeName;
        private int id;
        private int state;
        private String project_name;
        private String type;
        private String createName;

        public int getNews_type() {
            return news_type;
        }

        public void setNews_type(int news_type) {
            this.news_type = news_type;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getProject_id() {
            return project_id;
        }

        public void setProject_id(int project_id) {
            this.project_id = project_id;
        }

        public String getAdoptName() {
            return adoptName;
        }

        public void setAdoptName(String adoptName) {
            this.adoptName = adoptName;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }
    }
}
