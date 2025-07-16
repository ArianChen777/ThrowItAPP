package com.throwit.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 活动记录实体类
 * 对应数据库表：activities
 * 记录用户的瞬时活动状态，每次截图分析后记录当前时刻的活动
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Activity {
    
    /**
     * 活动ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 活动类型：entertainment/study/work/life
     */
    private String activityType;
    
    /**
     * 应用名称
     */
    private String appName;
    
    /**
     * AI识别的具体内容描述
     */
    private String activityDetail;
    
    /**
     * 截图时刻
     */
    private LocalDateTime captureTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}