package com.demo2.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EmpQuery implements Serializable {
    private String enameKeyword;
    private String jobName;
    private List<Integer> deptnos;
    private Date hiredAfter;
    private Double minimumSalary;
    private Double salaryFloor;
    private Double salaryCeiling;
    private Double salaryBelow;
    private Boolean commissionPresent;
    private Boolean salaryDesc;
    private String exactEname;
    private Integer fallbackDeptno;

    public String getEnameKeyword() {
        return enameKeyword;
    }

    public void setEnameKeyword(String enameKeyword) {
        this.enameKeyword = enameKeyword;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public List<Integer> getDeptnos() {
        return deptnos;
    }

    public void setDeptnos(List<Integer> deptnos) {
        this.deptnos = deptnos;
    }

    public Date getHiredAfter() {
        return hiredAfter;
    }

    public void setHiredAfter(Date hiredAfter) {
        this.hiredAfter = hiredAfter;
    }

    public Double getMinimumSalary() {
        return minimumSalary;
    }

    public void setMinimumSalary(Double minimumSalary) {
        this.minimumSalary = minimumSalary;
    }

    public Double getSalaryFloor() {
        return salaryFloor;
    }

    public void setSalaryFloor(Double salaryFloor) {
        this.salaryFloor = salaryFloor;
    }

    public Double getSalaryCeiling() {
        return salaryCeiling;
    }

    public void setSalaryCeiling(Double salaryCeiling) {
        this.salaryCeiling = salaryCeiling;
    }

    public Double getSalaryBelow() {
        return salaryBelow;
    }

    public void setSalaryBelow(Double salaryBelow) {
        this.salaryBelow = salaryBelow;
    }

    public Boolean getCommissionPresent() {
        return commissionPresent;
    }

    public void setCommissionPresent(Boolean commissionPresent) {
        this.commissionPresent = commissionPresent;
    }

    public Boolean getSalaryDesc() {
        return salaryDesc;
    }

    public void setSalaryDesc(Boolean salaryDesc) {
        this.salaryDesc = salaryDesc;
    }

    public String getExactEname() {
        return exactEname;
    }

    public void setExactEname(String exactEname) {
        this.exactEname = exactEname;
    }

    public Integer getFallbackDeptno() {
        return fallbackDeptno;
    }

    public void setFallbackDeptno(Integer fallbackDeptno) {
        this.fallbackDeptno = fallbackDeptno;
    }
}
