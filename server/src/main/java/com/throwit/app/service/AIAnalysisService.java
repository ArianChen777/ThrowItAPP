package com.throwit.app.service;

import com.throwit.app.dto.AIAnalysisResult;

/**
 * AI分析服务接口
 * 负责与第三方AI服务交互，获取图片分析结果
 */
public interface AIAnalysisService {
    
    /**
     * 分析截图内容
     * 
     * @param imageData 图片数据
     * @return AI分析结果
     */
    AIAnalysisResult analyzeScreenshot(byte[] imageData);
    
    /**
     * 检查AI服务是否可用
     * 
     * @return true-可用，false-不可用
     */
    boolean isServiceAvailable();
}