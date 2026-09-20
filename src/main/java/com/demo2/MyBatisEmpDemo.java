package com.demo2;

import com.demo2.entity.Emp;
import com.demo2.mapper.EmpMapper;
import com.demo2.util.MyBatisUtil;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

public class MyBatisEmpDemo {
    public static void main(String[] args) {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            List<Emp> employees = mapper.findAll();
            employees.forEach(System.out::println);
        }
    }
}
