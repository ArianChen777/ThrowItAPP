package com.throwit.app.controller;

import com.throwit.app.dto.*;
import com.throwit.app.service.ActivityService;
import com.throwit.app.service.AIAnalysisService;
import com.throwit.app.service.ScreenshotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

/**
 * 截图分析控制器
 * 处理截图上传和AI分析请求
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/app")
@RequiredArgsConstructor
@Validated
public class ScreenshotController {
    
    private final ScreenshotService screenshotService;
    private final AIAnalysisService aiAnalysisService;
    private final ActivityService activityService;
    
    /**
     * 上传截图并进行AI分析
     */
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ScreenshotAnalysisResponse>> uploadScreenshot(
            @AuthenticationPrincipal Long userId,
            @RequestParam("screenshot") MultipartFile screenshot,
            @RequestParam(value = "captureTime", required = false) String captureTimeStr) {
        
        log.info("用户{}上传截图，文件大小：{} bytes", userId, screenshot.getSize());
        
        try {
            // 解析截图时间，默认为当前时间
            LocalDateTime captureTime = captureTimeStr != null ? 
                    LocalDateTime.parse(captureTimeStr) : LocalDateTime.now();
            
            // 1. 压缩处理截图
            byte[] compressedImage = screenshotService.compressImage(screenshot);
            log.debug("图片压缩完成，压缩后大小：{} bytes", compressedImage.length);
            
            // 2. 调用AI分析
            AIAnalysisResult analysisResult = aiAnalysisService.analyzeScreenshot(compressedImage);
            log.debug("AI分析完成：活动类型={}, 应用={}", 
                    analysisResult.getActivityType(), analysisResult.getAppName());

            // 3. 记录活动并检查是否需要提醒
            ActivityAnalysisRequest activityRequest = new ActivityAnalysisRequest();
            activityRequest.setActivityType(analysisResult.getActivityType());
            activityRequest.setAppName(analysisResult.getAppName());
            activityRequest.setActivityDetail(analysisResult.getDescription());
            activityRequest.setCaptureTime(captureTime);
            
            ScreenshotAnalysisResponse response = activityService.recordActivity(userId, activityRequest);
            
            log.info("截图分析完成：用户={}, 活动类型={}, 需要提醒={}", 
                    userId, response.getActivityType(), response.getNeedReminder());
            
            return ResponseEntity.ok(ApiResponse.success(response));
            
        } catch (Exception e) {
            log.error("截图分析失败：用户={}, 错误：{}", userId, e.getMessage(), e);
            return ResponseEntity.badRequest().body(ApiResponse.error("截图分析失败：" + e.getMessage()));
        }
    }
}