package com.throwit.app.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 截图分析响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenshotAnalysisResponse {
    
    /**
     * 活动记录ID
     */
    private Long activityId;
    
    /**
     * 活动类型
     */
    private String activityType;
    
    /**
     * 应用名称
     */
    private String appName;
    
    /**
     * 活动详情
     */
    private String activityDetail;
    
    /**
     * 是否需要提醒
     */
    private Boolean needReminder;
    
    /**
     * 提醒内容（如果需要提醒）
     */
    private String reminderContent;
    
    /**
     * 连续娱乐时长（分钟）
     */
    private Integer continuousEntertainmentMinutes;
}