package com.throwit.app.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 提醒设置响应DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RemindSettingsResponse {
    
    /**
     * 设置ID
     */
    private Long id;
    
    /**
     * 连续娱乐时长限制（分钟）
     */
    private Integer continuousEntertainmentLimit;
    
    /**
     * 固定提醒内容
     */
    private String reminderContent;
}