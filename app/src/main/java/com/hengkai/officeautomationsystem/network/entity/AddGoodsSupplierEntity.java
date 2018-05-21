package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class AddGoodsSupplierEntity {

    /**
     * CODE : 1
     * param : [{"name":"硬件供应商","id":24},{"name":"软件供应商","id":23}]
     * MES : 查询成功
     */

    public int CODE;
    public String MES;
    public List<ParamBean> param;

    public static class ParamBean {
        /**
         * name : 硬件供应商
         * id : 24
         */

        public String name;
        public int id;
    }
}
