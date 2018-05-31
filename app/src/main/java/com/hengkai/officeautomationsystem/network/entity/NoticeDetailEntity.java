package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class NoticeDetailEntity {

    /**
     * DATE : {"upDown":{"upId":"","downId":"","down":"","up":""},"notList":[{"userAvatar":"","readTime":"","userName":"朱哲峰"},{"userAvatar":"","readTime":"","userName":"常璐"},{"userAvatar":"20180528135133373_846.jpeg","readTime":"","userName":"张冰"},{"userAvatar":"","readTime":"","userName":"七七"},{"userAvatar":"","readTime":"","userName":"赵四"},{"userAvatar":"","readTime":"","userName":"刘才"},{"userAvatar":"","readTime":"","userName":"刘策"},{"userAvatar":"","readTime":"","userName":"张佳辉"},{"userAvatar":"","readTime":"","userName":"赵颖"},{"userAvatar":"","readTime":"","userName":"符一博"},{"userAvatar":"","readTime":"","userName":"张静静"},{"userAvatar":"","readTime":"","userName":"谢巧燕"},{"userAvatar":"20180529174623812_227.jpeg","readTime":"","userName":"彭毅歌"},{"userAvatar":"","readTime":"","userName":"晋守桦"},{"userAvatar":"","readTime":"","userName":"尤洋洋"},{"userAvatar":"","readTime":"","userName":"万瑞"},{"userAvatar":"","readTime":"","userName":"李振伟"},{"userAvatar":"","readTime":"","userName":"ttttttt"},{"userAvatar":"","readTime":"","userName":"赵鹏"},{"userAvatar":"","readTime":"","userName":"销售1"},{"userAvatar":"","readTime":"","userName":"销售2"},{"userAvatar":"","readTime":"","userName":"旺旺"},{"userAvatar":"","readTime":"","userName":"王柳"},{"userAvatar":"","readTime":"","userName":"六六"},{"userAvatar":"","readTime":"","userName":"裴淳"},{"userAvatar":"","readTime":"","userName":"孙伟丽"},{"userAvatar":"","readTime":"","userName":"刘浩"}],"alrList":[{"userAvatar":"20180530174748110_811.jpeg","readTime":"2018-05-30 17:47:25","userName":"许宁"},{"userAvatar":"","readTime":"2018-05-31 09:49:44","userName":"杨帅"},{"userAvatar":"20180526171939330_428.jpeg","readTime":"2018-05-30 10:59:44","userName":"kong"},{"userAvatar":"20180530195456969_541.jpeg","readTime":"2018-05-29 18:02:01","userName":"管理员"}],"notice":{"createUserName":"管理员","noticeTitle":"接口测试标题修","createTime":1527587004000,"noticeValue":"测试内容"}}
     * CODE : 1
     * MES : 查询成功
     */

    public DATEBean DATE;
    public int CODE;
    public String MES;

    public static class DATEBean {
        /**
         * upDown : {"upId":"","downId":"","down":"","up":""}
         * notList : [{"userAvatar":"","readTime":"","userName":"朱哲峰"},{"userAvatar":"","readTime":"","userName":"常璐"},{"userAvatar":"20180528135133373_846.jpeg","readTime":"","userName":"张冰"},{"userAvatar":"","readTime":"","userName":"七七"},{"userAvatar":"","readTime":"","userName":"赵四"},{"userAvatar":"","readTime":"","userName":"刘才"},{"userAvatar":"","readTime":"","userName":"刘策"},{"userAvatar":"","readTime":"","userName":"张佳辉"},{"userAvatar":"","readTime":"","userName":"赵颖"},{"userAvatar":"","readTime":"","userName":"符一博"},{"userAvatar":"","readTime":"","userName":"张静静"},{"userAvatar":"","readTime":"","userName":"谢巧燕"},{"userAvatar":"20180529174623812_227.jpeg","readTime":"","userName":"彭毅歌"},{"userAvatar":"","readTime":"","userName":"晋守桦"},{"userAvatar":"","readTime":"","userName":"尤洋洋"},{"userAvatar":"","readTime":"","userName":"万瑞"},{"userAvatar":"","readTime":"","userName":"李振伟"},{"userAvatar":"","readTime":"","userName":"ttttttt"},{"userAvatar":"","readTime":"","userName":"赵鹏"},{"userAvatar":"","readTime":"","userName":"销售1"},{"userAvatar":"","readTime":"","userName":"销售2"},{"userAvatar":"","readTime":"","userName":"旺旺"},{"userAvatar":"","readTime":"","userName":"王柳"},{"userAvatar":"","readTime":"","userName":"六六"},{"userAvatar":"","readTime":"","userName":"裴淳"},{"userAvatar":"","readTime":"","userName":"孙伟丽"},{"userAvatar":"","readTime":"","userName":"刘浩"}]
         * alrList : [{"userAvatar":"20180530174748110_811.jpeg","readTime":"2018-05-30 17:47:25","userName":"许宁"},{"userAvatar":"","readTime":"2018-05-31 09:49:44","userName":"杨帅"},{"userAvatar":"20180526171939330_428.jpeg","readTime":"2018-05-30 10:59:44","userName":"kong"},{"userAvatar":"20180530195456969_541.jpeg","readTime":"2018-05-29 18:02:01","userName":"管理员"}]
         * notice : {"createUserName":"管理员","noticeTitle":"接口测试标题修","createTime":1527587004000,"noticeValue":"测试内容"}
         */

        public UpDownBean upDown;
        public NoticeBean notice;
        public List<NotListBean> notList;
        public List<AlrListBean> alrList;

        public static class UpDownBean {
            /**
             * upId :
             * downId :
             * down :
             * up :
             */

            public String upId;
            public String downId;
            public String down;
            public String up;
        }

        public static class NoticeBean {
            /**
             * createUserName : 管理员
             * noticeTitle : 接口测试标题修
             * createTime : 1527587004000
             * noticeValue : 测试内容
             */

            public String createUserName;
            public int createUserId;
            public String createUserAvatar;
            public String noticeTitle;
            public long createTime;
            public String noticeValue;
        }

        public static class NotListBean {
            /**
             * userAvatar :
             * readTime :
             * userName : 朱哲峰
             */

            public String userAvatar;
            public String readTime;
            public String userName;
        }

        public static class AlrListBean {
            /**
             * userAvatar : 20180530174748110_811.jpeg
             * readTime : 2018-05-30 17:47:25
             * userName : 许宁
             */

            public String userAvatar;
            public String readTime;
            public String userName;
        }
    }
}
