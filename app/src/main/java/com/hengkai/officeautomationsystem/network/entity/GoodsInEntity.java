package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class GoodsInEntity {

    /**
     * CODE : 1
     * MES : 查询成功
     * inStorage : [{"total":132,"purpose":"11111111","createTime":1526023709000,"createUserName":"裴淳","id":28,"state":0,"aid":117},{"total":1300,"purpose":"123","createTime":1526009512000,"createUserName":"裴淳","id":27,"state":0,"aid":89},{"total":260,"purpose":"你猜啊","createTime":1525945245000,"createUserName":"裴淳","id":9,"state":2,"aid":73},{"total":384,"purpose":"1221","createTime":1525775216000,"createUserName":"裴淳","id":8,"state":0,"aid":36},{"total":1700,"purpose":"ewrwe","createTime":1525775077000,"createUserName":"裴淳","id":7,"state":3,"aid":35}]
     */

    private int CODE;
    private String MES;
    private List<InStorageBean> inStorage;

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

    public List<InStorageBean> getInStorage() {
        return inStorage;
    }

    public void setInStorage(List<InStorageBean> inStorage) {
        this.inStorage = inStorage;
    }

    public static class InStorageBean {
        /**
         * total : 132
         * purpose : 11111111
         * createTime : 1526023709000
         * createUserName : 裴淳
         * id : 28
         * state : 0
         * aid : 117
         */

        private double total;
        private String purpose;
        private long createTime;
        private String createUserName;
        private int id;
        private int state;
        private int aid;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
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

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }
    }
}
