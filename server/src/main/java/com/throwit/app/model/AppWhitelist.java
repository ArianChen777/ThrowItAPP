package com.throwit.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 应用白名单实体类
 * 对应数据库表：app_whitelist
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class AppWhitelist {
    
    /**
     * 白名单ID
     */
    private Long id;
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 应用名称
     */
    private String appName;
    
    /**
     * 应用包名
     */
    private String appPackage;
    
    /**
     * 加入白名单原因
     */
    private String reason;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}