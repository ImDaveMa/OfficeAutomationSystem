package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/5/7.
 */
public class VisitRecordEntity {

    /**
     * CODE : 1
     * DATA : [{"companyName":"洛阳市商务局","userName":"裴淳","type":"1","contactsName":"乾隆","userId":1,"companyId":2,"identification":null,"isSubmission":false,"contactsId":"18","startTime":1525421764000,"id":18,"endTime":1525421772000,"projectName":"流程测试","department":"清朝","projectId":22},{"companyName":"洛阳市商务局","userName":"裴淳","type":"1","contactsName":null,"userId":1,"companyId":2,"identification":null,"isSubmission":true,"contactsId":"","startTime":1525506893000,"id":17,"endTime":1525507005000,"projectName":"上帝的项目","department":"技术部","projectId":2},{"companyName":"恒凯","userName":"许宁","type":"2","contactsName":null,"userId":3,"companyId":1,"identification":null,"isSubmission":true,"contactsId":null,"startTime":null,"id":16,"endTime":null,"projectName":null,"department":null,"projectId":null},{"companyName":"洛阳市商务局","userName":"许宁","type":"2","contactsName":null,"userId":3,"companyId":2,"identification":null,"isSubmission":false,"contactsId":"21","startTime":1525415519000,"id":15,"endTime":1525415563000,"projectName":"上帝的项目","department":"技术部","projectId":2},{"companyName":"恒凯","userName":"裴淳","type":"0","contactsName":"玉帝","userId":1,"companyId":1,"identification":null,"isSubmission":true,"contactsId":"12","startTime":1525231302000,"id":14,"endTime":1525231304000,"projectName":"测试3","department":"东方神话","projectId":20},{"companyName":"洛阳市商务局","userName":"裴淳","type":"1","contactsName":"乾隆","userId":1,"companyId":2,"identification":null,"isSubmission":false,"contactsId":"18","startTime":1524878395000,"id":13,"endTime":1524878398000,"projectName":null,"department":"清朝","projectId":7},{"companyName":"恒凯","userName":"裴淳","type":"1","contactsName":"张百忍","userId":1,"companyId":1,"identification":null,"isSubmission":false,"contactsId":"1","startTime":1524822821000,"id":12,"endTime":1524822824000,"projectName":null,"department":"天庭","projectId":7},{"companyName":"洛阳市商务局","userName":"裴淳","type":"2","contactsName":"乾隆","userId":1,"companyId":2,"identification":null,"isSubmission":false,"contactsId":"18","startTime":1524812921000,"id":11,"endTime":1524812923000,"projectName":"上帝的项目","department":"技术部","projectId":2}]
     * MES : 查询成功
     */

    public int CODE;
    public String MES;
    public List<DATABean> DATA;

    public static class DATABean {
        /**
         * companyName : 洛阳市商务局
         * userName : 裴淳
         * type : 1
         * contactsName : 乾隆
         * userId : 1
         * companyId : 2
         * identification : null
         * isSubmission : false
         * contactsId : 18
         * startTime : 1525421764000
         * id : 18
         * endTime : 1525421772000
         * projectName : 流程测试
         * department : 清朝
         * projectId : 22
         */

        public String companyName;
        public String userName;
        public String type;
        public String contactsName;
        public int userId;
        public int companyId;
        public String identification;
        public boolean isSubmission;
        public String contactsId;
        public long startTime;
        public int id;
        public long endTime;
        public String projectName;
        public String department;
        public int projectId;
    }
}
