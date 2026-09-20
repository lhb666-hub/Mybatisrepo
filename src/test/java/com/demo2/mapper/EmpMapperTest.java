package com.demo2.mapper;

import com.demo2.entity.Emp;
import com.demo2.util.MyBatisUtil;
import java.util.Date;
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
}
