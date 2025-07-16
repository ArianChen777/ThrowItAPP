package com.throwit.app.controller;

import com.throwit.app.dto.*;
import com.throwit.app.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * 活动统计控制器
 * 处理用户活动查询和统计请求
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/activity")
@RequiredArgsConstructor
public class ActivityController {
    
    private final ActivityService activityService;
    
    /**
     * 获取今日活动统计
     */
    @GetMapping("/today")
    public ResponseEntity<ApiResponse<ActivityStatsResponse>> getTodayStats(
            @AuthenticationPrincipal Long userId) {
        log.debug("获取用户今日活动统计：{}", userId);
        
        try {
            ActivityStatsResponse stats = activityService.getTodayStats(userId);
            return ResponseEntity.ok(ApiResponse.success(stats));
        } catch (Exception e) {
            log.error("获取今日活动统计失败：{}, 错误：{}", userId, e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}