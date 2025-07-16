package com.throwit.app.service.impl;

import com.throwit.app.client.OpenAIVisionClient;
import com.throwit.app.dto.AIAnalysisResult;
import com.throwit.app.service.AIAnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * AI分析服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AIAnalysisServiceImpl implements AIAnalysisService {
    
    private final OpenAIVisionClient openAIVisionClient;
    
    @Override
    public AIAnalysisResult analyzeScreenshot(byte[] imageData) {
        log.debug("开始AI分析，图片大小：{} bytes", imageData.length);
        
        try {
            AIAnalysisResult result = openAIVisionClient.analyzeScreenshot(imageData);
            
            // 验证和清理结果
            result = validateAndCleanResult(result);
            
            log.info("AI分析完成：类型={}, 应用={}, 置信度={}", 
                    result.getActivityType(), result.getAppName(), result.getConfidence());
            
            return result;
            
        } catch (Exception e) {
            log.error("AI分析失败", e);
            // 返回默认的娱乐类型，避免系统崩溃
            return AIAnalysisResult.builder()
                    .activityType("entertainment")
                    .appName("未知应用")
                    .description("AI分析失败：" + e.getMessage())
                    .confidence(0.1)
                    .build();
        }
    }
    
    @Override
    public boolean isServiceAvailable() {
        return openAIVisionClient.isServiceAvailable();
    }
    
    /**
     * 验证和清理AI分析结果
     */
    private AIAnalysisResult validateAndCleanResult(AIAnalysisResult result) {
        // 验证活动类型
        String activityType = result.getActivityType();
        if (!isValidActivityType(activityType)) {
            log.warn("无效的活动类型：{}，使用默认值", activityType);
            result.setActivityType("entertainment");
        }
        
        // 清理应用名称
        String appName = result.getAppName();
        if (appName == null || appName.trim().isEmpty()) {
            result.setAppName("未知应用");
        } else {
            result.setAppName(appName.trim());
        }
        
        // 验证置信度
        Double confidence = result.getConfidence();
        if (confidence == null || confidence < 0 || confidence > 1) {
            result.setConfidence(0.8);
        }
        
        // 清理描述
        String description = result.getDescription();
        if (description != null && description.length() > 500) {
            result.setDescription(description.substring(0, 500));
        }
        
        return result;
    }
    
    /**
     * 验证活动类型是否有效
     */
    private boolean isValidActivityType(String activityType) {
        if (activityType == null) {
            return false;
        }
        return "entertainment".equals(activityType) || 
               "study".equals(activityType) || 
               "work".equals(activityType) || 
               "life".equals(activityType);
    }
}