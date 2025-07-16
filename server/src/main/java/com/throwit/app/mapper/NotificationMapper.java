package com.throwit.app.mapper;

import com.throwit.app.model.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 提醒记录数据访问层接口
 */
@Mapper
public interface NotificationMapper {
    
    /**
     * 插入提醒记录
     */
    int insert(Notification notification);
    
    /**
     * 根据用户ID查询提醒记录列表
     */
    List<Notification> findByUserId(
        @Param("userId") Long userId,
        @Param("limit") Integer limit
    );
    
    /**
     * 根据用户ID和时间范围查询提醒记录
     */
    List<Notification> findByUserIdAndTimeRange(
        @Param("userId") Long userId,
        @Param("startTime") LocalDateTime startTime,
        @Param("endTime") LocalDateTime endTime
    );
    
    /**
     * 统计用户提醒次数
     */
    Long countByUserId(@Param("userId") Long userId);
    
    /**
     * 删除用户的提醒记录
     */
    int deleteByUserId(@Param("userId") Long userId);
}