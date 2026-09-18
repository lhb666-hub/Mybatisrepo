package com.demo;

import com.demo.entity.User;
import com.demo.mapper.UserMapperAnnotation;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

/**
 * 注解方式 Mapper（UserMapperAnnotation）的测试类。
 * 与 UserMapperTest 的区别：SQL 写在 @Select/@Insert/@Update/@Delete 注解里，
 * 不再需要 UserMapperAnnotation.xml 映射文件。
 */
public class UserMapperAnnotationTest {

    private SqlSessionFactory sqlSessionFactory;

    // 每个测试方法执行前构建一次 SqlSessionFactory
    @BeforeEach
    public void init() throws Exception {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    }

    @Test
    public void testSelectAll() {
        System.out.println("========== 注解方式：查询所有用户 ==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapperAnnotation mapper = sqlSession.getMapper(UserMapperAnnotation.class);
            List<User> users = mapper.selectAll();
            for (User user : users) {
                System.out.println(user);
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectById() {
        System.out.println("========== 注解方式：根据 ID 查询 ==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapperAnnotation mapper = sqlSession.getMapper(UserMapperAnnotation.class);
            User user = mapper.selectById(1);
            System.out.println(user);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByUsername() {
        System.out.println("========== 注解方式：根据用户名查询 ==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapperAnnotation mapper = sqlSession.getMapper(UserMapperAnnotation.class);
            User user = mapper.selectByUsername("testuser1");
            System.out.println(user);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFindByUsernameLike() {
        System.out.println("========== 注解方式：模糊查询 ==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapperAnnotation mapper = sqlSession.getMapper(UserMapperAnnotation.class);
            List<User> users = mapper.findByUsernameLike("test");
            for (User user : users) {
                System.out.println(user);
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsert() {
        System.out.println("========== 注解方式：添加用户 ==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapperAnnotation mapper = sqlSession.getMapper(UserMapperAnnotation.class);
            User user = new User();
            user.setUsername("注解测试用户");
            user.setPassword("123456");
            user.setEmail("annotation@qq.com");
            int rows = mapper.insert(user);
            sqlSession.commit();
            System.out.println("影响行数：" + rows + "，自增主键：" + user.getId());

            // 清理，避免重复运行时唯一约束冲突
            mapper.deleteById(user.getId());
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdate() {
        System.out.println("========== 注解方式：更新用户 ==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapperAnnotation mapper = sqlSession.getMapper(UserMapperAnnotation.class);
            // 先插入临时数据，再更新，避免依赖固定 id
            User user = new User();
            user.setUsername("注解临时更新");
            user.setPassword("123456");
            user.setEmail("update-before@qq.com");
            mapper.insert(user);
            sqlSession.commit();

            user.setUsername("注解更新后");
            user.setEmail("update@qq.com");
            int rows = mapper.update(user);
            sqlSession.commit();
            System.out.println("影响行数：" + rows);

            // 清理
            mapper.deleteById(user.getId());
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteById() {
        System.out.println("========== 注解方式：删除用户 ==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapperAnnotation mapper = sqlSession.getMapper(UserMapperAnnotation.class);
            // 先插入临时数据，再删除
            User user = new User();
            user.setUsername("注解临时删除");
            user.setPassword("123456");
            user.setEmail("delete@qq.com");
            mapper.insert(user);
            sqlSession.commit();

            int rows = mapper.deleteById(user.getId());
            sqlSession.commit();
            System.out.println("影响行数：" + rows);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByUsernameAndEmail() {
        System.out.println("========== 注解方式：多参数查询（@Param）==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapperAnnotation mapper = sqlSession.getMapper(UserMapperAnnotation.class);
            // 先插入临时数据，再用 username + email 两个参数查询，避免依赖固定数据
            User user = new User();
            user.setUsername("注解多参用户");
            user.setPassword("123456");
            user.setEmail("multi-anno@qq.com");
            mapper.insert(user);
            sqlSession.commit();

            User result = mapper.selectByUsernameAndEmail("注解多参用户", "multi-anno@qq.com");
            System.out.println(result);

            // 清理
            mapper.deleteById(user.getId());
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}
