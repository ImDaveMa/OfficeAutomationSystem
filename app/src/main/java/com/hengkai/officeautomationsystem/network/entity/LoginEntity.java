package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/5/2.
 */
public class LoginEntity {

    /**
     * CODE : 1
     * MES : 登录成功
     * USER : {"id":1,"loginName":"admin","phone":"18888888888","status":0,"jobNumber":"hk001","lastLoginTime":1528442774000,"createUserId":1,"createTime":1520265600000,"iconLink":"http://192.168.2.157:8080/oa/file/20180604092613091_752.jpeg","position":"运营管理","departmentId":16,"departmentName":"恒凯科技","departmentPermission":"hk","realName":"管理员","role":[{"rolePermission":"admin","roleName":"超级管理员"}]}
     * TOKEN : CF22C345AC6E1F39D2BB25ED8762EA37
     */

    public int CODE;
    public String MES;
    public USERBean USER;
    public String TOKEN;

    @Override
    public String toString() {
        return "LoginEntity{" +
                "CODE=" + CODE +
                ", MES='" + MES + '\'' +
                ", USER=" + USER +
                ", TOKEN='" + TOKEN + '\'' +
                '}';
    }

    public static class USERBean {
        /**
         * id : 1
         * loginName : admin
         * phone : 18888888888
         * status : 0
         * jobNumber : hk001
         * lastLoginTime : 1528442774000
         * createUserId : 1
         * createTime : 1520265600000
         * iconLink : http://192.168.2.157:8080/oa/file/20180604092613091_752.jpeg
         * position : 运营管理
         * departmentId : 16
         * departmentName : 恒凯科技
         * departmentPermission : hk
         * realName : 管理员
         * role : [{"rolePermission":"admin","roleName":"超级管理员"}]
         */

        public int id;
        public String loginName;
        public String phone;
        public int status;
        public String jobNumber;
        public long lastLoginTime;
        public int createUserId;
        public long createTime;
        public String iconLink;
        public String position;
        public int departmentId;
        public String departmentName;
        public String departmentPermission;
        public String realName;
        public List<RoleBean> role;

        @Override
        public String toString() {
            return "USERBean{" +
                    "id=" + id +
                    ", loginName='" + loginName + '\'' +
                    ", phone='" + phone + '\'' +
                    ", status=" + status +
                    ", jobNumber='" + jobNumber + '\'' +
                    ", lastLoginTime=" + lastLoginTime +
                    ", createUserId=" + createUserId +
                    ", createTime=" + createTime +
                    ", iconLink='" + iconLink + '\'' +
                    ", position='" + position + '\'' +
                    ", departmentId=" + departmentId +
                    ", departmentName='" + departmentName + '\'' +
                    ", departmentPermission='" + departmentPermission + '\'' +
                    ", realName='" + realName + '\'' +
                    ", role=" + role +
                    '}';
        }

        public static class RoleBean {
            /**
             * rolePermission : admin
             * roleName : 超级管理员
             */

            public String rolePermission;
            public String roleName;

            @Override
            public String toString() {
                return "RoleBean{" +
                        "rolePermission='" + rolePermission + '\'' +
                        ", roleName='" + roleName + '\'' +
                        '}';
            }
        }
    }
}
