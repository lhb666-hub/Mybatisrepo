package com.demo2.mapper;

import com.demo2.entity.Emp;
import com.demo2.entity.EmpBrief;
import com.demo2.entity.EmpQuery;
import com.demo2.util.MyBatisUtil;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

public class EmpMapperTest {
    @Test
    public void findAllReturnsEmployees() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Assert.assertNotNull(mapper.findAll());
        }
    }

    @Test
    public void insertUpdateDeleteEmployee() {
        Integer empno = 9901;

        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            mapper.removeByEmpno(empno);
            session.commit();
        }

        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp emp = new Emp(empno, "测试员工", "实习生", null, new Date(), 3500.00, null, null);
            Assert.assertEquals(1, mapper.add(emp));
            session.commit();
        }

        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp emp = mapper.findByEmpno(empno);
            Assert.assertNotNull(emp);
            emp.setJob("开发助理");
            emp.setSal(4200.00);
            Assert.assertEquals(1, mapper.modify(emp));
            session.commit();
        }

        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Assert.assertEquals(1, mapper.removeByEmpno(empno));
            session.commit();
        }
    }

    @Test
    public void taskThreeQueriesCoverGivenSqlList() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            List<Emp> allEmployees = mapper.findAll();
            List<EmpBrief> payrollView = mapper.listNameSalaryDept();
            List<Emp> highSalary = mapper.findSalaryAbove(10000.00);
            List<Emp> deptTwentyOrThirty = mapper.findInDepartments(Arrays.asList(20, 30));
            List<Emp> hiredAfterDate = mapper.findJoinedAfter(java.sql.Date.valueOf("2001-01-01"));
            List<Emp> withCommission = mapper.findWithCommission();
            List<Emp> byJob = mapper.findByJob("销售员");
            List<Emp> salaryRange = mapper.findSalaryBetween(8000.00, 20000.00);
            List<Emp> noCommissionAndLowSalary = mapper.findNoCommissionUnderSalary(15000.00);
            List<Emp> salaryDesc = mapper.findAllOrderBySalaryDesc();

            Assert.assertNotNull(allEmployees);
            Assert.assertNotNull(payrollView);
            Assert.assertNotNull(highSalary);
            Assert.assertNotNull(deptTwentyOrThirty);
            Assert.assertNotNull(hiredAfterDate);
            Assert.assertNotNull(withCommission);
            Assert.assertNotNull(byJob);
            Assert.assertNotNull(salaryRange);
            Assert.assertNotNull(noCommissionAndLowSalary);
            Assert.assertNotNull(salaryDesc);
        }
    }

    @Test
    public void taskThreeDynamicAndBatchOperations() {
        List<Integer> tempEmpnos = Arrays.asList(9911, 9912);

        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            mapper.removeManyByEmpno(tempEmpnos);
            session.commit();
        }

        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            List<Emp> employees = Arrays.asList(
                    new Emp(9911, "韩雨", "临时开发", null, new Date(), 9100.00, null, 10),
                    new Emp(9912, "林峰", "临时测试", 9911, new Date(), 8800.00, 300.00, 20)
            );
            Assert.assertEquals(2, mapper.addMany(employees));
            session.commit();
        }

        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            Emp patch = new Emp();
            patch.setEmpno(9911);
            patch.setJob("任务三验证");
            patch.setSal(9900.00);
            Assert.assertEquals(1, mapper.reviseSelective(patch));

            EmpQuery query = new EmpQuery();
            query.setDeptnos(Arrays.asList(10, 20));
            query.setSalaryFloor(8000.00);
            query.setSalaryCeiling(10000.00);
            query.setSalaryDesc(true);
            Assert.assertFalse(mapper.findByTaskThreeRules(query).isEmpty());

            EmpQuery choice = new EmpQuery();
            choice.setExactEname("韩雨");
            choice.setFallbackDeptno(20);
            Assert.assertEquals(1, mapper.findByNameOrDept(choice).size());

            session.commit();
        }

        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Assert.assertEquals(2, mapper.removeManyByEmpno(tempEmpnos));
            session.commit();
        }
    }
}
