package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class AddGoodsEntity {

    /**
     * CODE : 1
     * unit : [{"name":"只","id":62},{"name":"支","id":57},{"name":"KG","id":44},{"name":"斤","id":43},{"name":"盒","id":8},{"name":"瓶","id":7},{"name":"盆","id":5},{"name":"条","id":4},{"name":"个","id":3},{"name":"件","id":2},{"name":"箱","id":1}]
     * param : [{"name":"招待用品","id":7},{"name":"办公消耗","id":6},{"name":"硬件设施","id":5},{"name":"软件设施","id":4}]
     * supplier : [{"name":"洛阳恒凯科技信息有限公司","id":1},{"name":"测试1","id":2},{"name":"测试数据2","id":3},{"name":"测试数据3","id":4},{"name":"测试","id":5},{"name":"洛阳好公司","id":7},{"name":"大洛阳","id":8},{"name":"易商科技","id":9},{"name":"曙光","id":11},{"name":"百度","id":12},{"name":"测试数据wr","id":13}]
     * MES : 查询成功
     */

    public int CODE;
    public String MES;
    public List<UnitBean> unit;
    public List<ParamBean> param;
    public List<SupplierBean> supplier;

    public static class UnitBean {
        /**
         * name : 只
         * id : 62
         */

        public String name;
        public int id;
    }

    public static class ParamBean {
        /**
         * name : 招待用品
         * id : 7
         */

        public String name;
        public int id;
    }

    public static class SupplierBean {
        /**
         * name : 洛阳恒凯科技信息有限公司
         * id : 1
         */

        public String name;
        public int id;
    }
}
