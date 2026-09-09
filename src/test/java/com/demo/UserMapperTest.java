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
        // 读取 mybatis-config.xml（里面包含数据库连接 + Mapper 文件）
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        // 构建连接工厂
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    }

    @Test
    public void testFindAll() {
        System.out.println("========== 测试查询所有用户 ==========");
        // 打开一个连接会话，相当于 Connection
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 方式一：按 statement id 直接调用 XML 中声明的 SQL（namespace + 标签 id）
        List<User> users = sqlSession.selectList("com.demo.mapper.UserMapper.selectAll");

        // 方式二：通过接口代理调用（与上面等价）
        // List<User> users = sqlSession.getMapper(UserMapper.class).selectAll();

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
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUsername("测试用户");
        user.setPassword("123456");
        user.setEmail("test@qq.com");
        int rows = userMapper.insert(user);
        System.out.println("影响行数：" + rows);
        System.out.println("自增主键：" + user.getId());
        sqlSession.commit(); // 写操作必须 commit 才会真正提交
        sqlSession.close();
    }

    @Test
    public void testUpdateUser() {
        System.out.println("========== 测试更新用户 ==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectById(1);
        user.setUsername("更新后的用户名");
        user.setEmail("update@qq.com");
        int rows = userMapper.update(user);
        System.out.println("影响行数：" + rows);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testDeleteUser() {
        System.out.println("========== 测试删除用户 ==========");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int rows = userMapper.deleteById(1);
        System.out.println("影响行数：" + rows);
        sqlSession.commit();
        sqlSession.close();
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
