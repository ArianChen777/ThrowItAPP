package com.throwit.app.service;

import com.throwit.app.dto.ActivityAnalysisRequest;
import com.throwit.app.dto.ActivityStatsResponse;
import com.throwit.app.dto.ScreenshotAnalysisResponse;
import com.throwit.app.model.Activity;

import java.util.List;

/**
 * 活动服务接口
 * 负责记录和管理用户活动，并根据规则分析用户行为
 */
public interface ActivityService {
    
    /**
     * 记录用户活动
     * 
     * @param userId 用户ID
     * @param request 活动分析请求
     * @return 活动记录结果（包含是否需要提醒）
     */
    ScreenshotAnalysisResponse recordActivity(Long userId, ActivityAnalysisRequest request);
    
    /**
     * 获取用户今日活动统计
     * 
     * @param userId 用户ID
     * @return 今日活动统计
     */
    ActivityStatsResponse getTodayStats(Long userId);
    
    /**
     * 检查用户是否连续娱乐超过阈值
     * 
     * @param userId 用户ID
     * @param entertainmentLimitMinutes 娱乐限制时间（分钟）
     * @return true-超过阈值，false-未超过
     */
    boolean checkEntertainmentThreshold(Long userId, Integer entertainmentLimitMinutes);
    
    /**
     * 获取用户最近的娱乐活动列表
     * 
     * @param userId 用户ID
     * @param lookbackMinutes 回看时间（分钟）
     * @return 娱乐活动列表
     */
    List<Activity> getRecentEntertainmentActivities(Long userId, Integer lookbackMinutes);
}