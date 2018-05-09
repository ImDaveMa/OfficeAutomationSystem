package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class GoodsEntity {

    /**
     * CODE : 1
     * MES : 查询成功
     * list : [{"unit":"箱","goodsTypeName":"招待用品","num":0,"name":"12qwq","id":12},{"unit":"箱","goodsTypeName":"招待用品","num":0,"name":"111","id":11},{"unit":"条","goodsTypeName":"招待用品","num":0,"name":"qwq","id":10},{"unit":"盆","goodsTypeName":"软件设施","num":2,"name":"234","id":9},{"unit":"箱","goodsTypeName":"硬件设施","num":2,"name":"adsa","id":8},{"unit":"盒","goodsTypeName":"软件设施","num":5,"name":"测试2","id":4},{"unit":"盆","goodsTypeName":"硬件设施","num":20,"name":"测试","id":3},{"unit":"条","goodsTypeName":"招待用品","num":10,"name":"中华","id":2},{"unit":"瓶","goodsTypeName":"办公消耗","num":10,"name":"牛栏山","id":1}]
     */

    private int CODE;
    private String MES;
    private List<GoodsBean> list;

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

    public List<GoodsBean> getList() {
        return list;
    }

    public void setList(List<GoodsBean> list) {
        this.list = list;
    }

    public static class GoodsBean {
        /**
         * unit : 箱
         * goodsTypeName : 招待用品
         * num : 0
         * name : 12qwq
         * id : 12
         */

        private String unit;
        private String goodsTypeName;
        private int num;
        private String name;
        private int id;
        private int cost;

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }
    }
}
