package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class ProjectEntity {
    /**
     * TOTAL : 16
     * DATE : [{"createTime":"2018-04-23 11:59:10","projectSummarize":"还是木有","CZquan":1,"projectType":"公告","opened":"0","principalUserName":"裴淳","projectName":"玉帝的项目","projectId":"3","updateName":"","qualifiedTime":"2018-05-01 11:58:55","createName":"裴淳","dealMoney":"5000"},{"createTime":"2018-04-23 11:58:08","projectSummarize":"木有","CZquan":1,"projectType":"通知","opened":"0","principalUserName":"裴淳","projectName":"上帝的项目","projectId":"2","updateName":"","qualifiedTime":"2018-04-28 11:57:57","createName":"裴淳","dealMoney":"5"},{"createTime":"2018-04-17 16:00:29","projectSummarize":"项目1概述","CZquan":1,"projectType":"通知","opened":"0","principalUserName":"裴淳","projectName":"项目1","projectId":"1","updateName":"","qualifiedTime":"2018-04-21 16:00:18","createName":"裴淳","dealMoney":"100000"}]
     * CODE : 1
     * MES : 查询成功
     */

    private int TOTAL;
    private int CODE;
    private String MES;
    private List<ProjectBean> DATE;

    public int getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(int TOTAL) {
        this.TOTAL = TOTAL;
    }

    public int getCODE() {
        return CODE;
    }

    public void setCODE(int CODE) {
        this.CODE = CODE;
    }

    public String getMES() {
        return MES;
    }

    public void setMES(String MES) {
        this.MES = MES;
    }

    public List<ProjectBean> getDATE() {
        return DATE;
    }

    public void setDATE(List<ProjectBean> DATE) {
        this.DATE = DATE;
    }

    public static class ProjectBean {
        /**
         * createTime : 2018-04-23 11:59:10
         * projectSummarize : 还是木有
         * CZquan : 1
         * projectType : 公告
         * opened : 0
         * principalUserName : 裴淳
         * projectName : 玉帝的项目
         * projectId : 3
         * updateName :
         * qualifiedTime : 2018-05-01 11:58:55
         * createName : 裴淳
         * dealMoney : 5000
         */

        private String createTime;
        private String projectSummarize;
        private int CZquan;
        private String projectType;
        private boolean opened;
        private String principalUserName;
        private String projectName;
        private int projectId;
        private String updateName;
        private String qualifiedTime;
        private String createName;
        private String dealMoney;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getProjectSummarize() {
            return projectSummarize;
        }

        public void setProjectSummarize(String projectSummarize) {
            this.projectSummarize = projectSummarize;
        }

        public int getCZquan() {
            return CZquan;
        }

        public void setCZquan(int CZquan) {
            this.CZquan = CZquan;
        }

        public String getProjectType() {
            return projectType;
        }

        public void setProjectType(String projectType) {
            this.projectType = projectType;
        }

        public boolean getOpened() {
            return opened;
        }

        public void setOpened(boolean opened) {
            this.opened = opened;
        }

        public String getPrincipalUserName() {
            return principalUserName;
        }

        public void setPrincipalUserName(String principalUserName) {
            this.principalUserName = principalUserName;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public int getProjectId() {
            return projectId;
        }

        public void setProjectId(int projectId) {
            this.projectId = projectId;
        }

        public String getUpdateName() {
            return updateName;
        }

        public void setUpdateName(String updateName) {
            this.updateName = updateName;
        }

        public String getQualifiedTime() {
            return qualifiedTime;
        }

        public void setQualifiedTime(String qualifiedTime) {
            this.qualifiedTime = qualifiedTime;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getDealMoney() {
            return dealMoney;
        }

        public void setDealMoney(String dealMoney) {
            this.dealMoney = dealMoney;
        }
    }
}
