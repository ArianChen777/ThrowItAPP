package com.throwit.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ThrowIt Application Main Class
 * 
 * 基于Spring Boot的AI驱动反沉迷生产力工具后端服务
 * 通过定时分析用户屏幕截图，智能识别用户行为并提供个性化提醒
 * 
 * @author ThrowIt Team
 * @since 1.0.0
 */
@SpringBootApplication
@MapperScan("com.throwit.app.mapper")
public class ThrowItApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThrowItApplication.class, args);
    }
}