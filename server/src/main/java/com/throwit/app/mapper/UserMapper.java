package com.throwit.app.mapper;

import com.throwit.app.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 用户数据访问层接口
 */
@Mapper
public interface UserMapper {
    
    /**
     * 根据ID查询用户
     */
    Optional<User> findById(@Param("id") Long id);
    
    /**
     * 根据用户名查询用户
     */
    Optional<User> findByUsername(@Param("username") String username);
    
    /**
     * 根据邮箱查询用户
     */
    Optional<User> findByEmail(@Param("email") String email);
    
    /**
     * 插入新用户
     */
    int insert(User user);
    
    /**
     * 更新用户信息
     */
    int update(User user);
    
    /**
     * 更新最后登录时间
     */
    int updateLastLoginTime(@Param("id") Long id, @Param("lastLoginAt") LocalDateTime lastLoginAt);
    
    /**
     * 软删除用户
     */
    int softDelete(@Param("id") Long id, @Param("deletedAt") LocalDateTime deletedAt);
    
    /**
     * 检查用户名是否存在
     */
    boolean existsByUsername(@Param("username") String username);
    
    /**
     * 检查邮箱是否存在
     */
    boolean existsByEmail(@Param("email") String email);
}