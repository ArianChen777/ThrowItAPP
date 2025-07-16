package com.throwit.app.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * 活动分析请求DTO
 */
@Data
public class ActivityAnalysisRequest {
    
    /**
     * AI分析得到的活动类型
     */
    @NotBlank(message = "活动类型不能为空")
    private String activityType;
    
    /**
     * 应用名称
     */
    @NotBlank(message = "应用名称不能为空")
    private String appName;
    
    /**
     * AI分析得到的具体内容描述
     */
    private String activityDetail;
    
    /**
     * 截图时间
     */
    @NotNull(message = "截图时间不能为空")
    private LocalDateTime captureTime;
}