package com.demo;

import com.demo.entity.User;
import com.demo.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

/**
 * UserMapper 测试类。
 * MyBatis 核心对象：
 *   SqlSessionFactory —— 数据库连接工厂（重量级，创建一次即可）
 *   SqlSession        —— 数据库连接会话，类似 JDBC 的 Connection
 *
 * 调用 SQL 的两种方式：
 *   1) sqlSession.selectList("namespace.方法id") —— 按 statement id 直接调用 XML 里声明的 SQL
 *   2) sqlSession.getMapper(UserMapper.class)     —— 拿到接口代理，直接调用接口方法
 */
public class UserMapperTest {

    // MyBatis 的连接工厂
    private SqlSessionFactory sqlSessionFactory;

    // @BeforeEach：每个测试方法执行前运行一次（JUnit 5）
    // 你教材里的 @Before 是 JUnit 4 写法，JUnit 5 对应的是 @BeforeEach
    @BeforeEach
    public void init() throws Exception {
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    }

    @Test
    public void testFindAll() {
        System.out.println("========== 测试查询所有用户 ==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 方式一：按 statement id 直接调用 XML 中声明的 SQL
        List<User> users = sqlSession.selectList("com.demo.mapper.UserMapper.selectAll");
        for (User user : users) {
            System.out.println(user);
        }
        sqlSession.close();
    }

    @Test
    public void testFindById() {
        System.out.println("========== 测试根据 ID 查询用户 ==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectById(1);
        System.out.println(user);
        sqlSession.close();
    }

    @Test
    public void testFindByUsername() {
        System.out.println("========== 测试根据用户名查询 ==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectByUsername("testuser1");
        System.out.println(user);
        sqlSession.close();
    }

    @Test
    public void testAddUser() {
        System.out.println("========== 测试添加用户 ==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = new User();
            user.setUsername("测试用户");
            user.setPassword("123456");
            user.setEmail("test@qq.com");
            int rows = userMapper.insert(user);
            sqlSession.commit();
            System.out.println("影响行数：" + rows);
            System.out.println("自增主键：" + user.getId());

            // 清理，避免重复运行时唯一约束冲突
            userMapper.deleteById(user.getId());
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateUser() {
        System.out.println("========== 测试更新用户 ==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // 先插入一条临时数据，再更新它，避免依赖固定 id
            User user = new User();
            user.setUsername("临时更新用户");
            user.setPassword("123456");
            user.setEmail("update-before@qq.com");
            userMapper.insert(user);
            sqlSession.commit();

            user.setUsername("更新后的用户名");
            user.setEmail("update@qq.com");
            int rows = userMapper.update(user);
            sqlSession.commit();
            System.out.println("影响行数：" + rows);

            // 清理
            userMapper.deleteById(user.getId());
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteUser() {
        System.out.println("========== 测试删除用户 ==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // 先插入一条临时数据，再删除它
            User user = new User();
            user.setUsername("临时删除用户");
            user.setPassword("123456");
            user.setEmail("delete@qq.com");
            userMapper.insert(user);
            sqlSession.commit();

            int rows = userMapper.deleteById(user.getId());
            sqlSession.commit();
            System.out.println("影响行数：" + rows);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFindByUsernameLike() {
        System.out.println("========== 测试根据用户名模糊查询 ==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = userMapper.findByUsernameLike("test");
        for (User user : users) {
            System.out.println(user);
        }
        sqlSession.close();
    }
}
