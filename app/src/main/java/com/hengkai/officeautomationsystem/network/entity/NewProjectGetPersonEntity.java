package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/6/5.
 */
public class NewProjectGetPersonEntity {

    /**
     * DATE : [{"dName":"技术部","userName":"朱哲峰","userId":2,"dId":17},{"dName":"技术部","userName":"许宁","userId":3,"dId":17},{"dName":"技术部","userName":"常璐","userId":4,"dId":17},{"dName":"技术部","userName":"杨帅","userId":5,"dId":17},{"dName":"财务部","userName":"孙伟丽","userId":6,"dId":34},{"dName":"技术部","userName":"刘浩","userId":7,"dId":17},{"dName":"技术部","userName":"张冰","userId":11,"dId":17},{"dName":"行政部","userName":"六六","userId":16,"dId":19},{"dName":"销售部","userName":"ttttttt","userId":18,"dId":18},{"dName":"销售部","userName":"赵鹏","userId":19,"dId":18},{"dName":"行政部","userName":"裴淳","userId":20,"dId":19},{"dName":"销售部","userName":"销售1","userId":21,"dId":18},{"dName":"销售部","userName":"销售2","userId":22,"dId":18},{"dName":"技术部","userName":"七七","userId":24,"dId":17},{"dName":"技术部","userName":"赵四","userId":25,"dId":17},{"dName":"销售部","userName":"旺旺","userId":26,"dId":18},{"dName":"销售部","userName":"王柳","userId":27,"dId":18},{"dName":"技术部","userName":"kong","userId":28,"dId":17},{"dName":"技术部","userName":"刘才","userId":29,"dId":17},{"dName":"技术部","userName":"刘策","userId":30,"dId":17},{"dName":"技术部","userName":"张佳辉","userId":34,"dId":17},{"dName":"技术部","userName":"赵颖","userId":35,"dId":17},{"dName":"技术部","userName":"符一博","userId":36,"dId":17},{"dName":"技术部","userName":"张静静","userId":37,"dId":17},{"dName":"技术部","userName":"谢巧艳","userId":38,"dId":17},{"dName":"技术部","userName":"彭毅歌","userId":39,"dId":17},{"dName":"技术部","userName":"晋守桦","userId":40,"dId":17},{"dName":"技术部","userName":"尤洋洋","userId":41,"dId":17},{"dName":"技术部","userName":"万瑞","userId":42,"dId":17},{"dName":"技术部","userName":"李振伟","userId":43,"dId":17},{"dName":"行政部","userName":"冰冰","userId":45,"dId":19},{"dName":"技术部","userName":"刘甜甜","userId":46,"dId":17},{"dName":"技术部","userName":"bug测试","userId":52,"dId":17}]
     * CODE : 1
     * MES : 查询成功
     */

    public int CODE;
    public String MES;
    public List<DATEBean> DATE;

    public static class DATEBean {
        /**
         * dName : 技术部
         * userName : 朱哲峰
         * userId : 2
         * dId : 17
         */

        public String dName;
        public String userName;
        public int userId;
        public int dId;
    }
}
