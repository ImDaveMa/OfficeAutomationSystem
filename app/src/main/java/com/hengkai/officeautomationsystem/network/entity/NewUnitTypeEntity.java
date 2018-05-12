package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/5/12.
 */
public class NewUnitTypeEntity {

    /**
     * CODE : 1
     * DATA : [{"id":60,"paramValue":"政府"},{"id":59,"paramValue":"事业单位"},{"id":58,"paramValue":"国家行政机关"},{"id":57,"paramValue":"国有企业"},{"id":56,"paramValue":"民营企业"},{"id":55,"paramValue":"国有控股企业"},{"id":54,"paramValue":"合资企业  "},{"id":53,"paramValue":"外资企业 "}]
     * MES : 查询成功
     */

    public int CODE;
    public String MES;
    public List<DATABean> DATA;

    public static class DATABean {
        /**
         * id : 60
         * paramValue : 政府
         */

        public int id;
        public String paramValue;
    }
}
