package com.throwit.app.mapper;

import com.throwit.app.model.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 活动记录数据访问层接口
 */
@Mapper
public interface ActivityMapper {
    
    /**
     * 插入活动记录
     */
    int insert(Activity activity);
    
    /**
     * 根据用户ID和时间范围查询活动记录
     */
    List<Activity> findByUserIdAndTimeRange(
        @Param("userId") Long userId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );
    
    /**
     * 查询用户今日活动记录
     */
    List<Activity> findTodayActivitiesByUserId(@Param("userId") Long userId);
    
    /**
     * 查询用户最近的连续娱乐活动
     */
    List<Activity> findRecentEntertainmentActivities(
        @Param("userId") Long userId,
        @Param("lookbackMinutes") Integer lookbackMinutes
    );
    
    /**
     * 统计用户指定时间范围内各类型活动的数量
     */
    List<ActivityTypeStat> countActivitiesByType(
        @Param("userId") Long userId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );
    
    /**
     * 活动类型统计结果类
     */
    class ActivityTypeStat {
        private String activityType;
        private Long count;
        
        // getters and setters
        public String getActivityType() { return activityType; }
        public void setActivityType(String activityType) { this.activityType = activityType; }
        public Long getCount() { return count; }
        public void setCount(Long count) { this.count = count; }
    }
}