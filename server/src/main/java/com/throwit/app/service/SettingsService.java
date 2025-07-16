package com.throwit.app.service;

import com.throwit.app.dto.RemindSettingsRequest;
import com.throwit.app.dto.RemindSettingsResponse;

/**
 * 用户设置服务接口
 * 负责管理用户的个性化配置
 */
public interface SettingsService {
    
    /**
     * 获取用户提醒设置
     * 
     * @param userId 用户ID
     * @return 提醒设置
     */
    RemindSettingsResponse getUserRemindSettings(Long userId);
    
    /**
     * 更新用户提醒设置
     * 
     * @param userId 用户ID
     * @param request 设置请求
     * @return 更新后的设置
     */
    RemindSettingsResponse updateRemindSettings(Long userId, RemindSettingsRequest request);
    
    /**
     * 初始化用户默认设置
     * 
     * @param userId 用户ID
     */
    void initializeDefaultSettings(Long userId);
}