package com.throwit.app.service.impl;

import com.throwit.app.dto.*;
import com.throwit.app.mapper.ActivityMapper;
import com.throwit.app.model.Activity;
import com.throwit.app.service.ActivityService;
import com.throwit.app.service.NotificationService;
import com.throwit.app.service.SettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    
    private final ActivityMapper activityMapper;
    private final NotificationService notificationService;
    private final SettingsService settingsService;
    
    @Override
    @Transactional
    public ScreenshotAnalysisResponse recordActivity(Long userId, ActivityAnalysisRequest request) {
        log.info("记录用户活动：用户ID={}, 活动类型={}, 应用={}", 
                userId, request.getActivityType(), request.getAppName());
        
        // 创建活动记录
        Activity activity = Activity.builder()
                .userId(userId)
                .activityType(request.getActivityType())
                .appName(request.getAppName())
                .activityDetail(request.getActivityDetail())
                .captureTime(request.getCaptureTime())
                .createdAt(LocalDateTime.now())
                .build();
        
        // 保存活动记录
        int result = activityMapper.insert(activity);
        if (result <= 0) {
            throw new RuntimeException("保存活动记录失败");
        }
        
        // 构建基础响应
        ScreenshotAnalysisResponse.ScreenshotAnalysisResponseBuilder responseBuilder = ScreenshotAnalysisResponse.builder()
                .activityId(activity.getId())
                .activityType(activity.getActivityType())
                .appName(activity.getAppName())
                .activityDetail(activity.getActivityDetail())
                .needReminder(false);
        
        // 如果是娱乐活动，检查是否需要提醒
        if ("entertainment".equals(request.getActivityType())) {
            // 获取用户提醒设置
            var reminderSettings = settingsService.getUserRemindSettings(userId);
            Integer entertainmentLimit = reminderSettings.getContinuousEntertainmentLimit();
            
            // 检查是否超过娱乐阈值
            boolean shouldRemind = checkEntertainmentThreshold(userId, entertainmentLimit);
            
            if (shouldRemind) {
                // 生成提醒内容
                String reminderContent = notificationService.generateReminderContent(userId, activity.getId());
                
                // 计算连续娱乐时长
                List<Activity> recentEntertainment = getRecentEntertainmentActivities(userId, entertainmentLimit);
                int continuousMinutes = recentEntertainment.size() * 10; // 假设每10分钟截图一次
                
                responseBuilder
                        .needReminder(true)
                        .reminderContent(reminderContent)
                        .continuousEntertainmentMinutes(continuousMinutes);
                
                log.info("触发娱乐提醒：用户ID={}, 连续娱乐{}分钟", userId, continuousMinutes);
            }
        }
        
        return responseBuilder.build();
    }
    
    @Override
    public ActivityStatsResponse getTodayStats(Long userId) {
        log.debug("获取用户今日活动统计：{}", userId);
        
        List<ActivityMapper.ActivityTypeStat> stats = activityMapper.countActivitiesByType(
                userId, 
                LocalDateTime.now().toLocalDate().atStartOfDay(),
                LocalDateTime.now().toLocalDate().plusDays(1).atStartOfDay()
        );
        
        Map<String, Long> activityCounts = new HashMap<>();
        long totalActivities = 0;
        long entertainmentCount = 0;
        long studyCount = 0;
        long workCount = 0;
        long lifeCount = 0;
        
        for (ActivityMapper.ActivityTypeStat stat : stats) {
            activityCounts.put(stat.getActivityType(), stat.getCount());
            totalActivities += stat.getCount();
            
            switch (stat.getActivityType()) {
                case "entertainment" -> entertainmentCount = stat.getCount();
                case "study" -> studyCount = stat.getCount();
                case "work" -> workCount = stat.getCount();
                case "life" -> lifeCount = stat.getCount();
            }
        }
        
        return ActivityStatsResponse.builder()
                .activityCounts(activityCounts)
                .totalActivities(totalActivities)
                .entertainmentCount(entertainmentCount)
                .studyCount(studyCount)
                .workCount(workCount)
                .lifeCount(lifeCount)
                .build();
    }
    
    @Override
    public boolean checkEntertainmentThreshold(Long userId, Integer entertainmentLimitMinutes) {
        if (entertainmentLimitMinutes == null || entertainmentLimitMinutes <= 0) {
            return false;
        }
        
        List<Activity> recentEntertainment = getRecentEntertainmentActivities(userId, entertainmentLimitMinutes);
        
        // 简化逻辑：如果最近时间窗口内的娱乐活动次数超过阈值，则触发提醒
        // 假设每10分钟截图一次，那么30分钟限制对应3次活动
        int thresholdCount = entertainmentLimitMinutes / 10;
        
        boolean shouldRemind = recentEntertainment.size() >= thresholdCount;
        
        log.debug("娱乐阈值检查：用户ID={}, 限制{}分钟, 最近活动{}次, 阈值{}次, 需要提醒={}", 
                userId, entertainmentLimitMinutes, recentEntertainment.size(), thresholdCount, shouldRemind);
        
        return shouldRemind;
    }
    
    @Override
    public List<Activity> getRecentEntertainmentActivities(Long userId, Integer lookbackMinutes) {
        return activityMapper.findRecentEntertainmentActivities(userId, lookbackMinutes);
    }
}