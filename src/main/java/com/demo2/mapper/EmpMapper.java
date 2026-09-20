package com.demo2.mapper;

import com.demo2.entity.Emp;
import java.util.List;

public interface EmpMapper {
    List<Emp> findAll();

    Emp findByEmpno(Integer empno);

    int add(Emp emp);

    int modify(Emp emp);

    int removeByEmpno(Integer empno);
}
