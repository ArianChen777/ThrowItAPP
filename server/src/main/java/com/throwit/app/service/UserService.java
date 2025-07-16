package com.throwit.app.service;

import com.throwit.app.model.User;
import com.throwit.app.dto.LoginRequest;
import com.throwit.app.dto.LoginResponse;
import com.throwit.app.dto.RegisterRequest;
import com.throwit.app.dto.UpdateProfileRequest;
import com.throwit.app.dto.UserInfoResponse;

/**
 * 用户服务接口
 * 负责用户注册、登录、会话管理和用户信息管理
 */
public interface UserService {
    
    /**
     * 用户注册
     * 
     * @param request 注册请求
     * @return 注册成功的用户信息
     */
    UserInfoResponse register(RegisterRequest request);
    
    /**
     * 用户登录
     * 
     * @param request 登录请求
     * @return 登录结果（包含Token）
     */
    LoginResponse login(LoginRequest request);
    
    /**
     * 根据用户ID获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    UserInfoResponse getUserInfo(Long userId);
    
    /**
     * 更新用户资料
     * 
     * @param userId 用户ID
     * @param request 更新请求
     * @return 更新后的用户信息
     */
    UserInfoResponse updateProfile(Long userId, UpdateProfileRequest request);
    
    /**
     * 检查用户名是否可用
     * 
     * @param username 用户名
     * @return true-可用，false-已存在
     */
    boolean isUsernameAvailable(String username);
    
    /**
     * 检查邮箱是否可用
     * 
     * @param email 邮箱
     * @return true-可用，false-已存在
     */
    boolean isEmailAvailable(String email);
    
    /**
     * 更新用户最后登录时间
     * 
     * @param userId 用户ID
     */
    void updateLastLoginTime(Long userId);
}