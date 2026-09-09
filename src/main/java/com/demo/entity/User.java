package com.demo.entity;

import java.time.LocalDateTime;

/**
 * 用户实体类，对应数据库中的 user 表。
 * 属性使用驼峰命名，配合 mybatis-config.xml 里的 mapUnderscoreToCamelCase
 * 会自动把下划线列名（如 created_at）映射到驼峰属性（createdAt）。
 */
public class User {
    private Integer id;                 // 主键，自增
    private String username;            // 用户名（唯一）
    private String password;            // 密码
    private String email;               // 邮箱（唯一）
    private LocalDateTime createdAt;    // 创建时间，对应列 created_at
    private LocalDateTime updatedAt;    // 更新时间，对应列 updated_at

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
