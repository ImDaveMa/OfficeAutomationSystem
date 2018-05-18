package com.hengkai.officeautomationsystem.network.entity;

/**
 * Created by Harry on 2018/5/18.
 */
public class ProjectLibraryDetailEntity {

    /**
     * DATE : {"projectSummarize":"木有","pojectXzZ":"","typeName":"通知","pojectDw":"恒凯","principalUserName":"裴淳","qualifiedTime":1524887877000,"CreateUserName":"裴淳","pojectJf":"","createTime":1524455888000,"name":"上帝的项目","pojectXz":"","id":2,"dealMoney":""}
     * CODE : 1
     * MES : 查询成功
     */

    public DATEBean DATE;
    public int CODE;
    public String MES;

    public static class DATEBean {
        /**
         * projectSummarize : 木有
         * pojectXzZ :
         * typeName : 通知
         * pojectDw : 恒凯
         * principalUserName : 裴淳
         * qualifiedTime : 1524887877000
         * CreateUserName : 裴淳
         * pojectJf :
         * createTime : 1524455888000
         * name : 上帝的项目
         * pojectXz :
         * id : 2
         * dealMoney :
         */

        public String projectSummarize;
        public String pojectXzZ;
        public String typeName;
        public String pojectDw;
        public String principalUserName;
        public String qualifiedTime;
        public String CreateUserName;
        public String pojectJf;
        public long createTime;
        public String name;
        public String pojectXz;
        public int id;
        public String dealMoney;
    }
}
