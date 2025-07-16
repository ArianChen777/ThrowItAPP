package com.throwit.app.service.impl;

import com.throwit.app.mapper.NotificationMapper;
import com.throwit.app.model.Notification;
import com.throwit.app.service.NotificationService;
import com.throwit.app.service.SettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 提醒通知服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    
    private final NotificationMapper notificationMapper;
    private final SettingsService settingsService;
    
    @Value("${app.business.default-reminder.reminder-content}")
    private String defaultReminderContent;
    
    // 预设的提醒内容模板
    private static final List<String> REMINDER_TEMPLATES = Arrays.asList(
            "亲爱的，你已经娱乐一段时间了，是不是该休息一下或者做点其他事情呢？",
            "适度娱乐有益身心，但也别忘了自己的目标哦！要不要暂停一下，喝杯水休息一会？",
            "时间过得真快呢！你已经在这个应用上花了不少时间，考虑换个活动吗？",
            "嘿，该提醒你一下了！适当的休息能让你更有效率，要不要站起来走走？",
            "你的专注力很棒！不过现在是时候给大脑放个假了，做点别的事情怎么样？"
    );
    
    @Override
    public String generateReminderContent(Long userId, Long activityId) {
        log.debug("生成提醒内容：用户ID={}, 活动ID={}", userId, activityId);
        
        try {
            // 获取用户自定义的提醒内容
            var reminderSettings = settingsService.getUserRemindSettings(userId);
            String customContent = reminderSettings.getReminderContent();
            
            // 如果用户设置了自定义内容，使用自定义内容
            if (customContent != null && !customContent.trim().isEmpty()) {
                log.debug("使用用户自定义提醒内容");
                recordNotification(userId, activityId, customContent, "连续娱乐超时");
                return customContent;
            }
            
            // 否则随机选择一个预设模板
            Random random = new Random();
            String reminderContent = REMINDER_TEMPLATES.get(random.nextInt(REMINDER_TEMPLATES.size()));
            
            log.debug("使用随机提醒模板");
            recordNotification(userId, activityId, reminderContent, "连续娱乐超时");
            return reminderContent;
            
        } catch (Exception e) {
            log.error("生成提醒内容失败", e);
            // 返回默认内容
            String fallbackContent = defaultReminderContent != null ? 
                    defaultReminderContent : "该休息一下了，适度娱乐更健康！";
            recordNotification(userId, activityId, fallbackContent, "连续娱乐超时");
            return fallbackContent;
        }
    }
    
    @Override
    public void recordNotification(Long userId, Long activityId, String content, String triggerReason) {
        try {
            Notification notification = Notification.builder()
                    .userId(userId)
                    .activityId(activityId)
                    .content(content)
                    .triggerReason(triggerReason)
                    .createdAt(LocalDateTime.now())
                    .build();
            
            notificationMapper.insert(notification);
            log.debug("提醒记录已保存：用户ID={}, 内容={}", userId, content);
            
        } catch (Exception e) {
            log.error("保存提醒记录失败：用户ID={}", userId, e);
            // 不抛出异常，避免影响主要业务流程
        }
    }
}