package com.demo2.mapper;

import com.demo2.entity.DeptPayrollSummary;
import com.demo2.entity.Emp;
import com.demo2.entity.EmpBrief;
import com.demo2.entity.EmpManagerView;
import com.demo2.entity.EmpQuery;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmpMapper {
    List<Emp> findAll();

    List<Emp> findEmployeesWithDept();

    List<Emp> findEmployeesByDeptLocation(@Param("loc") String loc);

    List<DeptPayrollSummary> summarizeDeptPayroll();

    List<EmpManagerView> findEmployeesWithManagerAndDept();

    List<EmpBrief> listNameSalaryDept();

    List<Emp> findSalaryAbove(@Param("threshold") Double threshold);

    List<Emp> findInDepartments(@Param("deptnos") List<Integer> deptnos);

    List<Emp> findJoinedAfter(@Param("date") Date date);

    List<Emp> findWithCommission();

    List<Emp> findByJob(@Param("job") String job);

    List<Emp> findSalaryBetween(@Param("low") Double low, @Param("high") Double high);

    List<Emp> findNoCommissionUnderSalary(@Param("threshold") Double threshold);

    List<Emp> findAllOrderBySalaryDesc();

    List<Emp> findByTaskThreeRules(EmpQuery query);

    List<Emp> findByNameOrDept(EmpQuery query);

    Emp findByEmpno(Integer empno);

    int add(Emp emp);

    int addMany(@Param("emps") List<Emp> emps);

    int modify(Emp emp);

    int reviseSelective(Emp emp);

    int removeByEmpno(Integer empno);

    int removeManyByEmpno(@Param("empnos") List<Integer> empnos);
}
