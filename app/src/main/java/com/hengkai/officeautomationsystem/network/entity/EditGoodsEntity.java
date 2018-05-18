package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class EditGoodsEntity {

    /**
     * CODE : 1
     * goods : {"supplierId":7,"cost":333,"num":0,"remark":"","paramlist":[{"name":"招待用品","id":7},{"name":"办公消耗","id":6},{"name":"硬件设施","id":5},{"name":"软件设施","id":4}],"spec":"10盒每条","supplierlist":[{"name":"洛阳恒凯科技信息有限公司","id":1},{"name":"测试1","id":2},{"name":"测试数据2","id":3},{"name":"测试数据3","id":4},{"name":"测试","id":5},{"name":"洛阳好公司","id":7},{"name":"大洛阳","id":8},{"name":"易商科技","id":9},{"name":"曙光","id":11},{"name":"百度","id":12},{"name":"测试数据wr","id":13}],"goodsType":7,"total":"","name":"qwq","unitId":4,"id":10,"brand":"玉溪"}
     * MES : 查询成功
     */

    public int CODE;
    public GoodsBean goods;
    public String MES;

    public static class GoodsBean {
        /**
         * supplierId : 7
         * cost : 333
         * num : 0
         * remark :
         * paramlist : [{"name":"招待用品","id":7},{"name":"办公消耗","id":6},{"name":"硬件设施","id":5},{"name":"软件设施","id":4}]
         * spec : 10盒每条
         * supplierlist : [{"name":"洛阳恒凯科技信息有限公司","id":1},{"name":"测试1","id":2},{"name":"测试数据2","id":3},{"name":"测试数据3","id":4},{"name":"测试","id":5},{"name":"洛阳好公司","id":7},{"name":"大洛阳","id":8},{"name":"易商科技","id":9},{"name":"曙光","id":11},{"name":"百度","id":12},{"name":"测试数据wr","id":13}]
         * goodsType : 7
         * total :
         * name : qwq
         * unitId : 4
         * id : 10
         * brand : 玉溪
         */

        public int supplierId;
        public int cost;
        public int num;
        public String remark;
        public String spec;
        public int goodsType;
        public String total;
        public String name;
        public int unitId;
        public int id;
        public String brand;
        public List<ParamlistBean> paramlist;
        public List<SupplierlistBean> supplierlist;
        public String goodsTypeName;
        public String supplierName;
        public String unitName;

        public static class ParamlistBean {
            /**
             * name : 招待用品
             * id : 1
             */

            public String name;
            public int id;
        }

        public static class SupplierlistBean {
            /**
             * name : 洛阳恒凯科技信息有限公司
             * id : 1
             */

            public String name;
            public int id;
        }
    }
}
