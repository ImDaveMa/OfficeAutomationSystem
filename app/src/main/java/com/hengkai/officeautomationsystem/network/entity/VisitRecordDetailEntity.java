package com.hengkai.officeautomationsystem.network.entity;

/**
 * Created by Harry on 2018/5/9.
 */
public class VisitRecordDetailEntity {

    /**
     * CODE : 1
     * DATA : {"summary":"","companyName":"洛阳市商务局","userName":"裴淳","type":"1","contactsName":"乾隆","userId":1,"endLatitude":"34.426371","startLatitude":"34.426371","companyId":2,"identification":null,"isSubmission":true,"contactsId":"18","endLongitude":"112.436328","startTime":1525421764000,"id":18,"endTime":1525421772000,"projectName":"流程测试","department":"清朝","startLongitude":"112.506328","projectId":22,"remarks":null}
     * MES : 查询成功
     */

    public int CODE;
    public DATABean DATA;
    public String MES;
    /**
     * 审批ID
     */
    public int examineId;
    /**
     * 审批状态
     */
    public String examineState;
    /**
     * 0不允许, 1允许
     * <p>否允许操作(经理级别可操作, 可审批), 即使是经理级别如果操作完成后不可在操作</p>
     */
    public int isOptionable;

    public static class DATABean {
        /**
         * summary :
         * companyName : 洛阳市商务局
         * userName : 裴淳
         * type : 1
         * contactsName : 乾隆
         * userId : 1
         * endLatitude : 34.426371
         * startLatitude : 34.426371
         * companyId : 2
         * identification : null
         * isSubmission : true
         * contactsId : 18
         * endLongitude : 112.436328
         * startTime : 1525421764000
         * id : 18
         * endTime : 1525421772000
         * projectName : 流程测试
         * department : 清朝
         * startLongitude : 112.506328
         * projectId : 22
         * remarks : null
         */

        /**
         * 拜访跟进总结
         */
        public String summary;
        /**
         * 拜访单位名称
         */
        public String companyName;
        /**
         * 员工名称
         */
        public String userName;
        /**
         * 拜访跟进类型
         * 1：拜访 0：跟进 2：招待
         */
        public String type;
        /**
         * 拜访人名称
         */
        public String contactsName;
        /**
         * 员工id
         */
        public int userId;
        public String endLatitude;
        public String startLatitude;
        /**
         * 拜访单位id
         */
        public int companyId;
        /**
         * 文档备注
         */
        public Object identification;
        /**
         * 是否提交
         */
        public boolean isSubmission;
        /**
         * 拜访人id
         */
        public String contactsId;
        public String endLongitude;
        public long startTime;
        public int id;
        public long endTime;
        /**
         * 项目名称
         */
        public String projectName;
        /**
         * 拜访人部门
         */
        public String department;
        public String startLongitude;
        /**
         * 拜访项目id
         */
        public int projectId;
        /**
         * 备注
         */
        public Object remarks;
        public String startAddress;
        public String endAddress;
    }
}
