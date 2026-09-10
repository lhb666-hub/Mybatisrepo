package com.demo.mapper;

import com.demo.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 注解方式的 Mapper 接口。
 * 用 @Select/@Insert/@Update/@Delete 注解直接在方法上写 SQL，
 * 因此不需要对应的 UserMapperAnnotation.xml 映射文件。
 * 参数和返回类型从方法签名自动推断。
 */
public interface UserMapperAnnotation {

    // 查询所有用户
    @Select("SELECT * FROM `user`")
    List<User> selectAll();

    // 按主键查询
    @Select("SELECT * FROM `user` WHERE id = #{id}")
    User selectById(Integer id);

    // 按用户名精确查询
    @Select("SELECT * FROM `user` WHERE username = #{username}")
    User selectByUsername(String username);

    // 按用户名模糊查询
    @Select("SELECT * FROM `user` WHERE username LIKE CONCAT('%', #{keyword}, '%')")
    List<User> findByUsernameLike(String keyword);

    // 多参数查询：用 @Param 给每个参数命名，SQL 里用 #{username} / #{email} 引用
    // 注意：多个参数时若不加 @Param，MyBatis 只能用 #{param1}、#{arg0} 这种位置名，可读性差
    @Select("SELECT * FROM `user` WHERE username = #{username} AND email = #{email}")
    User selectByUsernameAndEmail(@Param("username") String username, @Param("email") String email);

    // 新增用户；@Options 开启获取自增主键并回填到 user.id
    @Insert("INSERT INTO `user` (username, password, email) VALUES (#{username}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    // 更新用户
    @Update("UPDATE `user` SET username = #{username}, password = #{password}, email = #{email} WHERE id = #{id}")
    int update(User user);

    // 按主键删除
    @Delete("DELETE FROM `user` WHERE id = #{id}")
    int deleteById(Integer id);
}
