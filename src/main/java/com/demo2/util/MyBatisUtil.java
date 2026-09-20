package com.demo2.util;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public final class MyBatisUtil {
    private static final SqlSessionFactory SQL_SESSION_FACTORY = buildFactory();

    private MyBatisUtil() {
    }

    public static SqlSession openSession() {
        return SQL_SESSION_FACTORY.openSession();
    }

    private static SqlSessionFactory buildFactory() {
        try (InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml")) {
            return new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
