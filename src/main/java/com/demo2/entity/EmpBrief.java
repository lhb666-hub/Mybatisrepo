package com.demo2.entity;

import java.io.Serializable;

public class EmpBrief implements Serializable {
    private String ename;
    private Double sal;
    private Integer deptno;

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Double getSal() {
        return sal;
    }

    public void setSal(Double sal) {
        this.sal = sal;
    }

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    @Override
    public String toString() {
        return "EmpBrief{" +
                "ename='" + ename + '\'' +
                ", sal=" + sal +
                ", deptno=" + deptno +
                '}';
    }
}
