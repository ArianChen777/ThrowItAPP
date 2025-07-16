package com.throwit.app.service.impl;

import com.throwit.app.dto.*;
import com.throwit.app.mapper.UserMapper;
import com.throwit.app.model.User;
import com.throwit.app.service.UserService;
import com.throwit.app.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    @Override
    @Transactional
    public UserInfoResponse register(RegisterRequest request) {
        log.info("用户注册：{}", request.getUsername());
        
        // 检查用户名是否已存在
        if (!isUsernameAvailable(request.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 检查邮箱是否已存在（如果提供了邮箱）
        if (request.getEmail() != null && !isEmailAvailable(request.getEmail())) {
            throw new RuntimeException("邮箱已存在");
        }
        
        // 创建用户
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .status(1) // 正常状态
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        int result = userMapper.insert(user);
        if (result <= 0) {
            throw new RuntimeException("用户注册失败");
        }
        
        log.info("用户注册成功：{}, ID: {}", request.getUsername(), user.getId());
        return convertToUserInfoResponse(user);
    }
    
    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("用户登录：{}", request.getUsername());
        
        // 查找用户
        User user = userMapper.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));
        
        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        
        // 检查用户状态
        if (user.getStatus() != 1) {
            throw new RuntimeException("用户已被禁用");
        }
        
        // 更新最后登录时间
        updateLastLoginTime(user.getId());
        
        // 生成JWT Token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        
        log.info("用户登录成功：{}, ID: {}", request.getUsername(), user.getId());
        
        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userInfo(convertToUserInfoResponse(user))
                .build();
    }
    
    @Override
    public UserInfoResponse getUserInfo(Long userId) {
        User user = userMapper.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        return convertToUserInfoResponse(user);
    }
    
    @Override
    @Transactional
    public UserInfoResponse updateProfile(Long userId, UpdateProfileRequest request) {
        log.info("更新用户资料：{}", userId);
        
        User user = userMapper.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        // 检查邮箱是否已被其他用户使用
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (!isEmailAvailable(request.getEmail())) {
                throw new RuntimeException("邮箱已存在");
            }
        }
        
        // 更新用户信息
        User updatedUser = User.builder()
                .id(userId)
                .email(request.getEmail())
                .phone(request.getPhone())
                .avatarUrl(request.getAvatarUrl())
                .updatedAt(LocalDateTime.now())
                .build();
        
        int result = userMapper.update(updatedUser);
        if (result <= 0) {
            throw new RuntimeException("更新用户资料失败");
        }
        
        // 返回更新后的用户信息
        User refreshedUser = userMapper.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        log.info("用户资料更新成功：{}", userId);
        return convertToUserInfoResponse(refreshedUser);
    }
    
    @Override
    public boolean isUsernameAvailable(String username) {
        return !userMapper.existsByUsername(username);
    }
    
    @Override
    public boolean isEmailAvailable(String email) {
        if (email == null || email.trim().isEmpty()) {
            return true;
        }
        return !userMapper.existsByEmail(email);
    }
    
    @Override
    public void updateLastLoginTime(Long userId) {
        userMapper.updateLastLoginTime(userId, LocalDateTime.now());
    }
    
    /**
     * 将User实体转换为UserInfoResponse
     */
    private UserInfoResponse convertToUserInfoResponse(User user) {
        return UserInfoResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .avatarUrl(user.getAvatarUrl())
                .status(user.getStatus())
                .lastLoginAt(user.getLastLoginAt())
                .createdAt(user.getCreatedAt())
                .build();
    }
}