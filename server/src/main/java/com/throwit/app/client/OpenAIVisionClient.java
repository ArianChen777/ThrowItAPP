package com.throwit.app.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.throwit.app.dto.AIAnalysisResult;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * OpenAI Vision API客户端
 * 负责调用OpenAI Vision API进行图片内容分析
 */
@Slf4j
@Component
public class OpenAIVisionClient {
    
    @Value("${app.ai.openai.api-key:}")
    private String apiKey;

    @Value("${app.ai.openai.url:https://api.openai.com/v1/chat/completions}") // 更新为新的配置路径
    private String apiUrl;

    @Value("${app.ai.openai.model:gpt-4o}")
    private String model;
    
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    
    public OpenAIVisionClient() {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * 分析截图内容
     */
    public AIAnalysisResult analyzeScreenshot(byte[] imageData) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            log.warn("OpenAI API Key未配置，使用模拟结果");
            return getMockAnalysisResult();
        }
        
        try {
            String base64Image = Base64.getEncoder().encodeToString(imageData);
            String requestBody = buildRequestBody(base64Image);
            
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(requestBody, MediaType.get("application/json")))
                    .build();
            
            long startTime = System.currentTimeMillis();
            
            try (Response response = httpClient.newCall(request).execute()) {
                long responseTime = System.currentTimeMillis() - startTime;
                
                if (!response.isSuccessful()) {
                    log.error("OpenAI API调用失败: code={}, body={}", response.code(), response.body().string());
                    throw new RuntimeException("AI分析服务暂时不可用");
                }
                
                String responseBody = response.body().string();
                AIAnalysisResult result = parseResponse(responseBody);
                
                log.info("AI分析完成，耗时：{}ms，结果：{}", responseTime, result.getActivityType());
                return result;
            }
            
        } catch (IOException e) {
            log.error("调用OpenAI API失败", e);
            throw new RuntimeException("AI分析服务连接失败", e);
        }
    }
    
    /**
     * 构建API请求体
     */
    private String buildRequestBody(String base64Image) {
        String prompt = buildPrompt();
        
        return String.format("""
            {
              "model": "%s",
              "messages": [
                {
                  "role": "user",
                  "content": [
                    {
                      "type": "text",
                      "text": "%s"
                    },
                    {
                      "type": "image_url",
                      "image_url": {
                        "url": "data:image/jpeg;base64,%s"
                      }
                    }
                  ]
                }
              ],
              "max_tokens": 500
            }
            """, model, prompt.replace("\n", "\\n").replace("\"", "\\\""), base64Image);
    }
    
    /**
     * 构建分析提示词
     */
    private String buildPrompt() {
        return """
            请分析这张手机截图，判断用户当前的活动类型。
            
            请根据以下规则分类：
            1. entertainment（娱乐）：观看视频、玩游戏、浏览社交媒体、听音乐等娱乐活动
            2. study（学习）：阅读学习资料、在线课程、笔记应用、学习软件等
            3. work（工作）：办公软件、邮件、会议、文档编辑、专业工具等
            4. life（生活）：购物、导航、天气、健康、银行、生活服务等
            
            请用JSON格式回复，包含以下字段：
            {
              "activityType": "entertainment|study|work|life",
              "appName": "应用名称",
              "description": "具体在做什么的简短描述",
              "confidence": 0.95
            }
            """;
    }
    
    /**
     * 解析API响应
     */
    private AIAnalysisResult parseResponse(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode choices = root.path("choices");
            
            if (choices.isArray() && choices.size() > 0) {
                JsonNode message = choices.get(0).path("message");
                String content = message.path("content").asText();
                
                // 清理内容，去除可能的markdown格式
                content = content.replaceAll("```json\\n?", "").replaceAll("```\\n?", "").trim();
                
                // 尝试解析AI返回的JSON
                JsonNode aiResult = objectMapper.readTree(content);
                
                return AIAnalysisResult.builder()
                        .activityType(aiResult.path("activityType").asText("entertainment"))
                        .appName(aiResult.path("appName").asText("未知应用"))
                        .description(aiResult.path("description").asText(""))
                        .confidence(aiResult.path("confidence").asDouble(0.8))
                        .build();
            }
            
        } catch (Exception e) {
            log.error("解析AI响应失败: {}", responseBody, e);
        }
        
        // 解析失败时返回默认结果
        return AIAnalysisResult.builder()
                .activityType("entertainment")
                .appName("未知应用")
                .description("AI分析结果解析失败")
                .confidence(0.5)
                .build();
    }
    
    /**
     * 模拟分析结果（用于测试）
     */
    private AIAnalysisResult getMockAnalysisResult() {
        return AIAnalysisResult.builder()
                .activityType("entertainment")
                .appName("模拟应用")
                .description("这是一个模拟的AI分析结果")
                .confidence(0.9)
                .build();
    }
    
    /**
     * 检查服务是否可用
     */
    public boolean isServiceAvailable() {
        return apiKey != null && !apiKey.trim().isEmpty();
    }
}