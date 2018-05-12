package com.hengkai.officeautomationsystem.network.entity;

/**
 * Created by Harry on 2018/5/12.
 */
public class UnitLibraryDetailEntity {

    /**
     * CODE : 1
     * DATA : {"id":1,"name":"恒凯","type":"政府","creditCode":"123456","address":"王城大道","keywordId":2,"website":"www.baidu.com","contract":null,"userId":"2","isChange":false,"createTime":1523936581000,"createUser":2,"updateTime":1525745615000,"updateUser":1,"isDelete":false,"text":"<p style=\"text-align: justify;\"><strong>洛阳市恒凯信息科技有限公司是一家集研发、销售、运营互联网产品为一体的民营科技企业，于2004年10月由<\/strong><a target=\"_blank\" href=\"https://baike.baidu.com/item/%E5%88%98%E6%96%87%E5%8D%9A\" style=\"color: rgb(19, 110, 194); text-decoration-line: none;\"><strong>刘文博<\/strong><\/a><strong>创建于洛阳，是洛阳市互联网龙头企业之一。在城市化门户网站平台的研发与运营领域做到了国内领先。<\/strong><\/p><p style=\"text-align: justify;\"><strong>公司主营业务包括：网站平台建设，<\/strong><a target=\"_blank\" href=\"https://baike.baidu.com/item/%E5%9F%9F%E5%90%8D%E6%B3%A8%E5%86%8C\" style=\"color: rgb(19, 110, 194); text-decoration-line: none;\"><strong>域名注册<\/strong><\/a><strong>、网络广告宣传推广、软件开发、手机应用研发和地方门户网站运营等领域。为全国的企事业单位提供各种软硬件设备的服务与解决方案。<\/strong><\/p><p style=\"text-align: center;\"><br/><\/p>","remarks":null}
     * MES : 查询成功
     */

    public int CODE;
    public DATABean DATA;
    public String MES;

    public static class DATABean {
        /**
         * id : 1
         * name : 恒凯
         * type : 政府
         * creditCode : 123456
         * address : 王城大道
         * keywordId : 2
         * website : www.baidu.com
         * contract : null
         * userId : 2
         * isChange : false
         * createTime : 1523936581000
         * createUser : 2
         * updateTime : 1525745615000
         * updateUser : 1
         * isDelete : false
         * text : <p style="text-align: justify;"><strong>洛阳市恒凯信息科技有限公司是一家集研发、销售、运营互联网产品为一体的民营科技企业，于2004年10月由</strong><a target="_blank" href="https://baike.baidu.com/item/%E5%88%98%E6%96%87%E5%8D%9A" style="color: rgb(19, 110, 194); text-decoration-line: none;"><strong>刘文博</strong></a><strong>创建于洛阳，是洛阳市互联网龙头企业之一。在城市化门户网站平台的研发与运营领域做到了国内领先。</strong></p><p style="text-align: justify;"><strong>公司主营业务包括：网站平台建设，</strong><a target="_blank" href="https://baike.baidu.com/item/%E5%9F%9F%E5%90%8D%E6%B3%A8%E5%86%8C" style="color: rgb(19, 110, 194); text-decoration-line: none;"><strong>域名注册</strong></a><strong>、网络广告宣传推广、软件开发、手机应用研发和地方门户网站运营等领域。为全国的企事业单位提供各种软硬件设备的服务与解决方案。</strong></p><p style="text-align: center;"><br/></p>
         * remarks : null
         */

        public int id;
        public String name;
        public String type;
        public String creditCode;
        public String address;
        public int keywordId;
        public String website;
        public String contract;
        public String userId;
        public boolean isChange;
        public long createTime;
        public int createUser;
        public long updateTime;
        public int updateUser;
        public boolean isDelete;
        public String text;
        public Object remarks;
    }
}
