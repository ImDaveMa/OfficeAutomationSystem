package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/5/12.
 */
public class NewUnitKeywordEntity {

    /**
     * CODE : 1
     * DATA : [{"name":"科技","id":1},{"name":"商务局","id":2},{"name":"老城区","id":3},{"name":"洛阳","id":4},{"name":"西工区","id":5},{"name":"郑州","id":6},{"name":"孟津","id":7},{"name":"网易","id":8},{"name":"腾讯","id":9},{"name":"天宇","id":10},{"name":"完美世界","id":11},{"name":"第九城市","id":12}]
     * MES : 查询成功
     */

    public int CODE;
    public String MES;
    public List<DATABean> DATA;

    public static class DATABean {
        /**
         * name : 科技
         * id : 1
         */

        public String name;
        public int id;
    }
}
