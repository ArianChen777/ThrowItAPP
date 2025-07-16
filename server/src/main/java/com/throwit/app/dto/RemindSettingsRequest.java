package com.throwit.app.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

/**
 * 提醒设置请求DTO
 */
@Data
public class RemindSettingsRequest {
    
    /**
     * 连续娱乐时长限制（分钟）
     */
    @Min(value = 5, message = "连续娱乐时长限制不能少于5分钟")
    @Max(value = 480, message = "连续娱乐时长限制不能超过8小时")
    private Integer continuousEntertainmentLimit;
    
    /**
     * 固定提醒内容
     */
    private String reminderContent;
}