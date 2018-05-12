package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/5/12.
 */
public class MyUnitEntity {

    /**
     * CODE : 1
     * DATA : [{"keywordId":2,"website":"www.baidu.com","creditCode":"123456","address":"王城大道","myID":9,"createTime":1523936581000,"contract":null,"name":"恒凯","id":1,"type":"政府"},{"keywordId":2,"website":"www.lysswj.gov.cn/","creditCode":"","address":"洛阳市人民政府党政办公大楼8楼","myID":10,"createTime":1524192374000,"contract":null,"name":"洛阳市商务局","id":2,"type":"国家行政机关"}]
     * MES : 查询成功
     */

    public int CODE;
    public String MES;
    public List<DATABean> DATA;

    public static class DATABean {
        /**
         * keywordId : 2
         * website : www.baidu.com
         * creditCode : 123456
         * address : 王城大道
         * myID : 9
         * createTime : 1523936581000
         * contract : null
         * name : 恒凯
         * id : 1
         * type : 政府
         */

        public int keywordId;
        public String website;
        public String creditCode;
        public String address;
        public int myID;
        public long createTime;
        public String contract;
        public String name;
        public int id;
        public String type;
    }
}
