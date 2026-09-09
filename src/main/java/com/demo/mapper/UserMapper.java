package com.demo.mapper;

import com.demo.entity.User;
import java.util.List;

/**
 * user 表对应的 Mapper 接口。
 * 接口方法名必须与 UserMapper.xml 中对应标签的 id 保持一致，MyBatis 才能正确绑定。
 */
public interface UserMapper {

    // 查询所有用户
    List<User> selectAll();

    // 按主键 id 查询单个用户
    User selectById(Integer id);

    // 新增用户，返回影响行数；插入后自增主键会回填到 user.id
    int insert(User user);

    // 更新用户
    int update(User user);

    // 按主键删除
    int deleteById(Integer id);

    // 按用户名精确查询
    User selectByUsername(String username);

    // 按用户名模糊查询
    List<User> findByUsernameLike(String keyword);
}
