package com.throwit.app.service;

/**
 * 提醒通知服务接口
 * 负责生成和管理所有用户提醒
 */
public interface NotificationService {
    
    /**
     * 生成提醒内容
     * 
     * @param userId 用户ID
     * @param activityId 触发提醒的活动ID
     * @return 提醒内容
     */
    String generateReminderContent(Long userId, Long activityId);
    
    /**
     * 记录提醒发送历史
     * 
     * @param userId 用户ID
     * @param activityId 触发提醒的活动ID
     * @param content 提醒内容
     * @param triggerReason 触发原因
     */
    void recordNotification(Long userId, Long activityId, String content, String triggerReason);
}