package com.throwit.app.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * 活动统计响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityStatsResponse {
    
    /**
     * 各类型活动数量统计
     * key: 活动类型(entertainment/study/work/life)
     * value: 活动次数
     */
    private Map<String, Long> activityCounts;
    
    /**
     * 总活动次数
     */
    private Long totalActivities;
    
    /**
     * 今日娱乐活动次数
     */
    private Long entertainmentCount;
    
    /**
     * 今日学习活动次数
     */
    private Long studyCount;
    
    /**
     * 今日工作活动次数
     */
    private Long workCount;
    
    /**
     * 今日生活活动次数
     */
    private Long lifeCount;
}