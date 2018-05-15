package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class GoodsDetailEntity {

    /**
     * CODE : 1
     * MES : 查询成功
     * list : [{"unit":"盆","total":880,"cost":25,"createTime":1524455709000,"goodsTypeName":"办公消耗","num":16,"supplier":"测试数据1supplier","name":"测试","createUserName":"裴淳","remark":"Field 'name' doesn't have a default valueField 'name' doesn't have a default value","specifications":"5Kg","brand":"测试"}]
     */

    private int CODE;
    private String MES;
    private List<DetailBean> list;

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

    public List<DetailBean> getList() {
        return list;
    }

    public void setList(List<DetailBean> list) {
        this.list = list;
    }

    public static class DetailBean {
        /**
         * unit : 盆
         * total : 880
         * cost : 25
         * createTime : 1524455709000
         * goodsTypeName : 办公消耗
         * num : 16
         * supplier : 测试数据1supplier
         * name : 测试
         * createUserName : 裴淳
         * remark : Field 'name' doesn't have a default valueField 'name' doesn't have a default value
         * specifications : 5Kg
         * brand : 测试
         */

        private String unit;
        private double total;
        private double cost;
        private long createTime;
        private String goodsTypeName;
        private int num;
        private String supplier;
        private String name;
        private String createUserName;
        private String remark;
        private String specifications;
        private String brand;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public double getCost() {
            return cost;
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getGoodsTypeName() {
            return goodsTypeName;
        }

        public void setGoodsTypeName(String goodsTypeName) {
            this.goodsTypeName = goodsTypeName;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getSupplier() {
            return supplier;
        }

        public void setSupplier(String supplier) {
            this.supplier = supplier;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreateUserName() {
            return createUserName;
        }

        public void setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSpecifications() {
            return specifications;
        }

        public void setSpecifications(String specifications) {
            this.specifications = specifications;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }
    }
}
