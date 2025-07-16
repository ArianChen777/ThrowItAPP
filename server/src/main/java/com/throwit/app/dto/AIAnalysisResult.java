package com.throwit.app.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * AI分析结果DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIAnalysisResult {
    
    /**
     * 活动类型：entertainment/study/work/life
     */
    private String activityType;
    
    /**
     * 应用名称
     */
    private String appName;
    
    /**
     * 活动描述
     */
    private String description;
    
    /**
     * AI分析置信度（0-1）
     */
    private Double confidence;
}