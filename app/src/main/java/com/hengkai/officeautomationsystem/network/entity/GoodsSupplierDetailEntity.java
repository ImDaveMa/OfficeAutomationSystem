package com.hengkai.officeautomationsystem.network.entity;

public class GoodsSupplierDetailEntity {

    /**
     * CODE : 1
     * supplier : {"qq":"772137923","supperType":"硬件供应商","address":"西工区王城大道111号紫金城3楼3079","city":"洛阳","postalCode":"471000","wechat":"15037968103","description":"软件读物","remark":"大公司","source":"招标","bussiness":3,"cooperative":4,"mailbox":"772137923@qq.com","phone":"15037968103","name":"洛阳恒凯科技信息有限公司","id":1,"state":0,"fax":"","contacts":"万瑞"}
     * MES : 查询成功
     */

    public int CODE;
    public SupplierDetailBean supplier;
    public String MES;

    public static class SupplierDetailBean {
        /**
         * qq : 772137923
         * supperType : 硬件供应商
         * address : 西工区王城大道111号紫金城3楼3079
         * city : 洛阳
         * postalCode : 471000
         * wechat : 15037968103
         * description : 软件读物
         * remark : 大公司
         * source : 招标
         * bussiness : 3
         * cooperative : 4
         * mailbox : 772137923@qq.com
         * phone : 15037968103
         * name : 洛阳恒凯科技信息有限公司
         * id : 1
         * state : 0
         * fax :
         * contacts : 万瑞
         */

        public String qq;
        public String supperType;
        public String address;
        public String city;
        public String postalCode;
        public String wechat;
        public String description;
        public String remark;
        public String source;
        public int bussiness;
        public int cooperative;
        public String mailbox;
        public String phone;
        public String name;
        public int id;
        public int state;
        public String fax;
        public String contacts;
    }
}
