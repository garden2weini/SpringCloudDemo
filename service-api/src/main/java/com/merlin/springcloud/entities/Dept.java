package com.merlin.springcloud.entities;

import java.io.Serializable;

public class Dept implements Serializable
{
    private Long deptNo; // 主键
    private String deptName; // 部门名称
    private String dbSource;// 来自那个数据库，因为微服务架构可以一个服务对应一个数据库，同一个信息被存储到不同数据库

    public Dept() {
        super();
    }

    public Dept(String deptName) {
        super();
        this.deptName = deptName;
    }

    public Dept(Integer deptNo, String deptName, String dbSource) {
        super();
        this.deptNo = deptNo.longValue();
        this.deptName = deptName;
        this.dbSource = dbSource;
    }


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Long getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(Long deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDbSource() {
        return dbSource;
    }

    public void setDbSource(String dbSource) {
        this.dbSource = dbSource;
    }
}