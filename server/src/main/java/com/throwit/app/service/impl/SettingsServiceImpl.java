package com.throwit.app.service.impl;

import com.throwit.app.dto.RemindSettingsRequest;
import com.throwit.app.dto.RemindSettingsResponse;
import com.throwit.app.mapper.UserRemindSettingMapper;
import com.throwit.app.model.UserRemindSetting;
import com.throwit.app.service.SettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 用户设置服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {
    
    private final UserRemindSettingMapper userRemindSettingMapper;
    
    @Value("${app.business.default-reminder.continuous-entertainment-limit}")
    private Integer defaultEntertainmentLimit;
    
    @Value("${app.business.default-reminder.reminder-content}")
    private String defaultReminderContent;
    
    @Override
    public RemindSettingsResponse getUserRemindSettings(Long userId) {
        log.debug("获取用户提醒设置：{}", userId);
        
        UserRemindSetting setting = userRemindSettingMapper.findByUserId(userId)
                .orElse(null);
        
        if (setting == null) {
            // 如果用户没有设置，初始化默认设置
            log.info("用户{}没有提醒设置，初始化默认设置", userId);
            initializeDefaultSettings(userId);
            setting = userRemindSettingMapper.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("初始化用户设置失败"));
        }
        
        return RemindSettingsResponse.builder()
                .id(setting.getId())
                .continuousEntertainmentLimit(setting.getContinuousEntertainmentLimit())
                .reminderContent(setting.getReminderContent())
                .build();
    }
    
    @Override
    @Transactional
    public RemindSettingsResponse updateRemindSettings(Long userId, RemindSettingsRequest request) {
        log.info("更新用户提醒设置：{}", userId);
        
        UserRemindSetting existingSetting = userRemindSettingMapper.findByUserId(userId)
                .orElse(null);
        
        if (existingSetting == null) {
            // 如果不存在，先初始化
            initializeDefaultSettings(userId);
            existingSetting = userRemindSettingMapper.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("初始化用户设置失败"));
        }
        
        // 更新设置
        UserRemindSetting updatedSetting = UserRemindSetting.builder()
                .userId(userId)
                .continuousEntertainmentLimit(request.getContinuousEntertainmentLimit() != null ? 
                        request.getContinuousEntertainmentLimit() : existingSetting.getContinuousEntertainmentLimit())
                .reminderContent(request.getReminderContent() != null ? 
                        request.getReminderContent() : existingSetting.getReminderContent())
                .updatedAt(LocalDateTime.now())
                .build();
        
        int result = userRemindSettingMapper.update(updatedSetting);
        if (result <= 0) {
            throw new RuntimeException("更新用户设置失败");
        }
        
        log.info("用户提醒设置更新成功：{}", userId);
        
        // 返回更新后的设置
        return getUserRemindSettings(userId);
    }
    
    @Override
    @Transactional
    public void initializeDefaultSettings(Long userId) {
        log.info("初始化用户默认设置：{}", userId);
        
        UserRemindSetting defaultSetting = UserRemindSetting.builder()
                .userId(userId)
                .continuousEntertainmentLimit(defaultEntertainmentLimit != null ? defaultEntertainmentLimit : 30)
                .reminderContent(defaultReminderContent)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        int result = userRemindSettingMapper.insert(defaultSetting);
        if (result <= 0) {
            throw new RuntimeException("初始化用户默认设置失败");
        }
        
        log.info("用户默认设置初始化成功：{}", userId);
    }
}