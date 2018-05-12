package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class UseGoodsEntity {

    /**
     * CODE : 1
     * outStorage : [{"total":12,"purpose":"1212","createTime":1526086906000,"createUserName":"裴淳","id":45,"state":0,"aid":125,"productName":null}]
     * MES : 查询成功
     */

    private int CODE;
    private String MES;
    private List<OutStorageBean> outStorage;

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

    public List<OutStorageBean> getOutStorage() {
        return outStorage;
    }

    public void setOutStorage(List<OutStorageBean> outStorage) {
        this.outStorage = outStorage;
    }

    public static class OutStorageBean {
        /**
         * total : 12
         * purpose : 1212
         * createTime : 1526086906000
         * createUserName : 裴淳
         * id : 45
         * state : 0
         * aid : 125
         * productName : null
         */

        private int total;
        private String purpose;
        private long createTime;
        private String createUserName;
        private int id;
        private int state;
        private int aid;
        private Object productName;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
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

        public Object getProductName() {
            return productName;
        }

        public void setProductName(Object productName) {
            this.productName = productName;
        }
    }
}
