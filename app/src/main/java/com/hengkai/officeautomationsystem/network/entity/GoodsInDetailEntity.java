package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class GoodsInDetailEntity {

    /**
     * CODE : 1
     * MES : 查询成功
     * inStorage : {"total":12,"createTime":1526087390000,"purpose":"121","aps":[{"departmentId":34,"id":38,"userType":"approval_rksp","type":1,"userName":"孙伟丽","userId":6},{"departmentId":34,"id":39,"userType":"approval_rksp","type":2,"userName":"常璐","userId":4}],"createUserName":"裴淳","id":39,"state":1,"aid":127,"outAttach":[{"total":12,"cost":12,"unitName":"瓶","num":1,"goodsName":"牛栏山"}]}
     */

    private int CODE;
    private String MES;
    private InStorageBean inStorage;

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

    public InStorageBean getInStorage() {
        return inStorage;
    }

    public void setInStorage(InStorageBean inStorage) {
        this.inStorage = inStorage;
    }

    public static class InStorageBean {
        /**
         * total : 12
         * createTime : 1526087390000
         * purpose : 121
         * aps : [{"departmentId":34,"id":38,"userType":"approval_rksp","type":1,"userName":"孙伟丽","userId":6},{"departmentId":34,"id":39,"userType":"approval_rksp","type":2,"userName":"常璐","userId":4}]
         * createUserName : 裴淳
         * id : 39
         * state : 1
         * aid : 127
         * outAttach : [{"total":12,"cost":12,"unitName":"瓶","num":1,"goodsName":"牛栏山"}]
         */

        private int total;
        private long createTime;
        private String purpose;
        private String createUserName;
        private int id;
        private int state;
        private int aid;
        private List<ApsBean> aps;
        private List<OutAttachBean> outAttach;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
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

        public List<ApsBean> getAps() {
            return aps;
        }

        public void setAps(List<ApsBean> aps) {
            this.aps = aps;
        }

        public List<OutAttachBean> getOutAttach() {
            return outAttach;
        }

        public void setOutAttach(List<OutAttachBean> outAttach) {
            this.outAttach = outAttach;
        }

        public static class ApsBean {
            /**
             * departmentId : 34
             * id : 38
             * userType : approval_rksp
             * type : 1
             * userName : 孙伟丽
             * userId : 6
             */

            private int departmentId;
            private int id;
            private String userType;
            private int type;
            private String userName;
            private int userId;

            public int getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(int departmentId) {
                this.departmentId = departmentId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUserType() {
                return userType;
            }

            public void setUserType(String userType) {
                this.userType = userType;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }

        public static class OutAttachBean {
            /**
             * total : 12
             * cost : 12
             * unitName : 瓶
             * num : 1
             * goodsName : 牛栏山
             */

            private int total;
            private int cost;
            private String unitName;
            private int num;
            private String goodsName;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getCost() {
                return cost;
            }

            public void setCost(int cost) {
                this.cost = cost;
            }

            public String getUnitName() {
                return unitName;
            }

            public void setUnitName(String unitName) {
                this.unitName = unitName;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }
        }
    }
}
