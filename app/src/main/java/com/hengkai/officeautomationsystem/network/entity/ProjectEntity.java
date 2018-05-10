package com.hengkai.officeautomationsystem.network.entity;

import java.util.List;

public class ProjectEntity {

    /**
     * CODE : 1
     * projectlist : [{"name":"项目1","id":1},{"name":"上帝的项目","id":2},{"name":"玉帝的项目","id":3},{"name":"测试","id":15},{"name":"测试2","id":16},{"name":"测试3","id":20},{"name":"ceshiyixia2333","id":21},{"name":"流程测试","id":22},{"name":"测试流程2","id":23},{"name":"测试流程3","id":24},{"name":"测试创建回款","id":25},{"name":"测试流程111","id":26},{"name":"测试流程222","id":27},{"name":"cccccce222","id":28},{"name":"测试文件","id":29},{"name":"曙光","id":31}]
     * MES : 查询成功
     */

    private int CODE;
    private String MES;
    private List<ProjectBean> projectlist;

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

    public List<ProjectBean> getProjectlist() {
        return projectlist;
    }

    public void setProjectlist(List<ProjectBean> projectlist) {
        this.projectlist = projectlist;
    }

    public static class ProjectBean {
        /**
         * name : 项目1
         * id : 1
         */

        private String name;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
