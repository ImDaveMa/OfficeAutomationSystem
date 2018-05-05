package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/5/5.
 */
public class UnitLibraryEntity {

    /**
     * CODE : 1
     * DATA : [{"id":1,"name":"恒凯","type":null,"creditCode":null,"address":null,"keywordId":null,"website":null,"contract":null,"userId":null,"isChange":null,"createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"isDelete":null,"text":null,"remarks":null},{"id":2,"name":"洛阳市商务局","type":null,"creditCode":null,"address":null,"keywordId":null,"website":null,"contract":null,"userId":null,"isChange":null,"createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"isDelete":null,"text":null,"remarks":null},{"id":3,"name":"洛阳市恒凯信息科技有限公司","type":null,"creditCode":null,"address":null,"keywordId":null,"website":null,"contract":null,"userId":null,"isChange":null,"createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"isDelete":null,"text":null,"remarks":null},{"id":4,"name":"网易公司","type":null,"creditCode":null,"address":null,"keywordId":null,"website":null,"contract":null,"userId":null,"isChange":null,"createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"isDelete":null,"text":null,"remarks":null},{"id":5,"name":"深圳市腾讯计算机系统有限公司","type":null,"creditCode":null,"address":null,"keywordId":null,"website":null,"contract":null,"userId":null,"isChange":null,"createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"isDelete":null,"text":null,"remarks":null},{"id":6,"name":"深圳市腾讯计算机系统有限公司","type":null,"creditCode":null,"address":null,"keywordId":null,"website":null,"contract":null,"userId":null,"isChange":null,"createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"isDelete":null,"text":null,"remarks":null},{"id":9,"name":"三大发达","type":null,"creditCode":null,"address":null,"keywordId":null,"website":null,"contract":null,"userId":null,"isChange":null,"createTime":null,"createUser":null,"updateTime":null,"updateUser":null,"isDelete":null,"text":null,"remarks":null}]
     * MES : 查询成功
     */

    public int CODE;
    public String MES;
    public List<DATABean> DATA;

    public static class DATABean {
        /**
         * id : 1
         * name : 恒凯
         * type : null
         * creditCode : null
         * address : null
         * keywordId : null
         * website : null
         * contract : null
         * userId : null
         * isChange : null
         * createTime : null
         * createUser : null
         * updateTime : null
         * updateUser : null
         * isDelete : null
         * text : null
         * remarks : null
         */

        public int id;
        public String name;
        public Object type;
        public Object creditCode;
        public String address;
        public Object keywordId;
        public Object website;
        public Object contract;
        public Object userId;
        public Object isChange;
        public Object createTime;
        public Object createUser;
        public Object updateTime;
        public Object updateUser;
        public Object isDelete;
        public Object text;
        public Object remarks;
    }
}
