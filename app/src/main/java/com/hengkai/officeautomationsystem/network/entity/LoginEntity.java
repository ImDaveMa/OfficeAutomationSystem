package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/5/2.
 */
public class LoginEntity {


    /**
     * CODE : 1
     * MES : 登录成功
     * USER : {"id":1,"loginName":"admin","status":0,"jobNumber":"hk001","lastLoginTime":1525311665000,"createUserId":0,"createTime":1520301699000,"iconLink":null,"position":"JAVA","departmentName":"恒凯科技","departmentPermission":"hk","realName":"裴淳","role":[{"rolePermission":"temporary_employee","roleName":"临时员工"},{"rolePermission":"administrators","roleName":"管理员"},{"rolePermission":"technology_manager","roleName":"技术部-经理"},{"rolePermission":"technology_leader","roleName":"技术部-组长"},{"rolePermission":"technology_member","roleName":"技术部-组员"},{"rolePermission":"marketing_manager","roleName":"销售部-经理"},{"rolePermission":"marketing_assistant","roleName":"销售部-助理"}]}
     * TOKEN : 592CF8CDE242DFEDAE687B91CD3EA1B8
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
         * status : 0
         * jobNumber : hk001
         * lastLoginTime : 1525311665000
         * createUserId : 0
         * createTime : 1520301699000
         * iconLink : null
         * position : JAVA
         * departmentName : 恒凯科技
         * departmentPermission : hk
         * realName : 裴淳
         * role : [{"rolePermission":"temporary_employee","roleName":"临时员工"},{"rolePermission":"administrators","roleName":"管理员"},{"rolePermission":"technology_manager","roleName":"技术部-经理"},{"rolePermission":"technology_leader","roleName":"技术部-组长"},{"rolePermission":"technology_member","roleName":"技术部-组员"},{"rolePermission":"marketing_manager","roleName":"销售部-经理"},{"rolePermission":"marketing_assistant","roleName":"销售部-助理"}]
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
        public String departmentName;
        public String departmentPermission;
        public String realName;
        public List<RoleBean> role;

        @Override
        public String toString() {
            return "USERBean{" +
                    "id=" + id +
                    ", loginName='" + loginName + '\'' +
                    ", status=" + status +
                    ", jobNumber='" + jobNumber + '\'' +
                    ", lastLoginTime=" + lastLoginTime +
                    ", createUserId=" + createUserId +
                    ", createTime=" + createTime +
                    ", iconLink=" + iconLink +
                    ", position='" + position + '\'' +
                    ", departmentName='" + departmentName + '\'' +
                    ", departmentPermission='" + departmentPermission + '\'' +
                    ", realName='" + realName + '\'' +
                    ", role=" + role +
                    '}';
        }

        public static class RoleBean {
            /**
             * rolePermission : temporary_employee
             * roleName : 临时员工
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
