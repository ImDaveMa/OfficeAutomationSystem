package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class EditGoodsSupplierEntity {

    /**
     * CODE : 1
     * supplier : {"qq":"772137923","supperType":24,"address":"西工区王城大道111号紫金城3楼3079","city":"洛阳","postalCode":"471000","wechat":"15037968103","description":"软件读物","remark":"大公司","paramlist":[{"name":"硬件供应商","id":24},{"name":"软件供应商","id":23}],"source":"招标","mailbox":"772137923@qq.com","phone":"15037968103","name":"洛阳恒凯科技信息有限公司","id":1,"fax":"","contacts":"万瑞"}
     * MES : 查询成功
     */

    public int CODE;
    public SupplierBean supplier;
    public String MES;

    public static class SupplierBean {
        /**
         * qq : 772137923
         * supperType : 24
         * address : 西工区王城大道111号紫金城3楼3079
         * city : 洛阳
         * postalCode : 471000
         * wechat : 15037968103
         * description : 软件读物
         * remark : 大公司
         * paramlist : [{"name":"硬件供应商","id":24},{"name":"软件供应商","id":23}]
         * source : 招标
         * mailbox : 772137923@qq.com
         * phone : 15037968103
         * name : 洛阳恒凯科技信息有限公司
         * id : 1
         * fax :
         * contacts : 万瑞
         */

        public String qq;
        public int supperType;
        public String address;
        public String city;
        public String postalCode;
        public String wechat;
        public String description;
        public String remark;
        public String source;
        public String mailbox;
        public String phone;
        public String name;
        public int id;
        public String fax;
        public String contacts;
        public List<ParamlistBean> paramlist;

        public static class ParamlistBean {
            /**
             * name : 硬件供应商
             * id : 24
             */

            public String name;
            public int id;
        }
    }
}
