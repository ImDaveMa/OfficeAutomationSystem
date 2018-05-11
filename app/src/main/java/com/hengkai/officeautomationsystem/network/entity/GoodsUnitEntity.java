package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class GoodsUnitEntity {

    /**
     * CODE : 1
     * MES : 查询成功
     * list : [{"name":"admin","id":56},{"name":"他","id":50},{"name":"袋","id":45},{"name":"KG","id":44},{"name":"斤","id":43},{"name":"套","id":42},{"name":"盒","id":8},{"name":"瓶","id":7},{"name":"盆","id":5},{"name":"条","id":4},{"name":"个","id":3},{"name":"件","id":2},{"name":"箱","id":1}]
     */

    private int CODE;
    private String MES;
    private List<UnitBean> list;

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

    public List<UnitBean> getList() {
        return list;
    }

    public void setList(List<UnitBean> list) {
        this.list = list;
    }

    public static class UnitBean {
        /**
         * name : admin
         * id : 56
         */

        private String name;
        private int id;

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
    }
}
