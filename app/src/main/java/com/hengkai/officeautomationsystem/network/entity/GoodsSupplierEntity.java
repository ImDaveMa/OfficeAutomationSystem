package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class GoodsSupplierEntity {

    /**
     * CODE : 1
     * param : [{"param":"硬件供应商","id":24},{"param":"软件供应商","id":23}]
     * supplier : [{"supplierTypeName":"软件供应商","city":"111","name":"11111","id":10,"supplierType":23},{"supplierTypeName":"硬件供应商","city":"洛阳","name":"易商科技","id":9,"supplierType":24},{"supplierTypeName":"硬件供应商","city":"洛阳","name":"大洛阳","id":8,"supplierType":24},{"supplierTypeName":"软件供应商","city":"洛阳","name":"洛阳好公司","id":7,"supplierType":23},{"supplierTypeName":"硬件供应商","city":"洛阳","name":"测试","id":5,"supplierType":24},{"supplierTypeName":"软件供应商","city":"洛测试数据3","name":"测试数据3","id":4,"supplierType":23},{"supplierTypeName":"硬件供应商","city":"上测试数据2","name":"测试数据2","id":3,"supplierType":24},{"supplierTypeName":"软件供应商","city":"上海supplier","name":"测试数据1supplier","id":2,"supplierType":23},{"supplierTypeName":"硬件供应商","city":"洛阳","name":"洛阳恒凯科技信息有限公司","id":1,"supplierType":24}]
     * MES : 查询成功
     */

    private int CODE;
    private String MES;
    private List<ParamBean> param;
    private List<SupplierBean> supplier;

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

    public List<ParamBean> getParam() {
        return param;
    }

    public void setParam(List<ParamBean> param) {
        this.param = param;
    }

    public List<SupplierBean> getSupplier() {
        return supplier;
    }

    public void setSupplier(List<SupplierBean> supplier) {
        this.supplier = supplier;
    }

    public static class ParamBean {
        /**
         * param : 硬件供应商
         * id : 24
         */

        private String param;
        private int id;

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class SupplierBean {
        /**
         * supplierTypeName : 软件供应商
         * city : 111
         * name : 11111
         * id : 10
         * supplierType : 23
         */

        private String supplierTypeName;
        private String city;
        private String name;
        private int id;
        private int supplierType;

        public String getSupplierTypeName() {
            return supplierTypeName;
        }

        public void setSupplierTypeName(String supplierTypeName) {
            this.supplierTypeName = supplierTypeName;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
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

        public int getSupplierType() {
            return supplierType;
        }

        public void setSupplierType(int supplierType) {
            this.supplierType = supplierType;
        }
    }
}
