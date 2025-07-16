package com.throwit.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 提醒记录实体类
 * 对应数据库表：notifications
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Notification {
    
    /**
     * 提醒ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 触发提醒的活动ID
     */
    private Long activityId;
    
    /**
     * 提醒内容
     */
    private String content;
    
    /**
     * 触发原因
     */
    private String triggerReason;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}