package com.throwit.app.mapper;

import com.throwit.app.model.UserRemindSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * 用户提醒设置数据访问层接口
 */
@Mapper
public interface UserRemindSettingMapper {
    
    /**
     * 根据用户ID查询提醒设置
     */
    Optional<UserRemindSetting> findByUserId(@Param("userId") Long userId);
    
    /**
     * 插入用户提醒设置
     */
    int insert(UserRemindSetting setting);
    
    /**
     * 更新用户提醒设置
     */
    int update(UserRemindSetting setting);
    
    /**
     * 根据用户ID删除提醒设置
     */
    int deleteByUserId(@Param("userId") Long userId);
}