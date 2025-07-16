package com.throwit.app.controller;

import com.throwit.app.dto.*;
import com.throwit.app.service.SettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * 用户设置控制器
 * 处理用户设置相关请求
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user/settings")
@RequiredArgsConstructor
@Validated
public class SettingsController {
    
    private final SettingsService settingsService;
    
    /**
     * 获取用户提醒设置
     */
    @GetMapping("/reminders")
    public ResponseEntity<ApiResponse<RemindSettingsResponse>> getRemindSettings(
            @AuthenticationPrincipal Long userId) {
        log.debug("获取用户提醒设置：{}", userId);
        
        try {
            RemindSettingsResponse settings = settingsService.getUserRemindSettings(userId);
            return ResponseEntity.ok(ApiResponse.success(settings));
        } catch (Exception e) {
            log.error("获取用户提醒设置失败：{}, 错误：{}", userId, e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
    
    /**
     * 更新用户提醒设置
     */
    @PutMapping("/reminders")
    public ResponseEntity<ApiResponse<RemindSettingsResponse>> updateRemindSettings(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestBody RemindSettingsRequest request) {
        log.info("更新用户提醒设置：{}", userId);
        
        try {
            RemindSettingsResponse settings = settingsService.updateRemindSettings(userId, request);
            return ResponseEntity.ok(ApiResponse.success(settings));
        } catch (Exception e) {
            log.error("更新用户提醒设置失败：{}, 错误：{}", userId, e.getMessage());
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}