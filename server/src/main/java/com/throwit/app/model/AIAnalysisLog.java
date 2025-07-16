package com.throwit.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * AI分析记录实体类
 * 对应数据库表：ai_analysis_logs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AIAnalysisLog {
    
    /**
     * 记录ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 关联的活动ID
     */
    private Long activityId;
    
    /**
     * 图片哈希值（用于去重）
     */
    private String imageHash;
    
    /**
     * 使用的AI模型
     */
    private String aiModel;
    
    /**
     * AI分析结果（JSON格式）
     */
    private String analysisResult;
    
    /**
     * 响应时间（毫秒）
     */
    private Integer responseTime;
    
    /**
     * API调用错误码
     */
    private String errorCode;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}