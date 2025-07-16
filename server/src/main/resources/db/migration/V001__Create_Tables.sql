-- ThrowIt数据库初始化脚本
-- 版本：V1.0
-- 创建时间：2025-07-10

-- 1. 用户表（users）
CREATE TABLE `users` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` varchar(50) NOT NULL COMMENT '用户名',
    `password` varchar(255) NOT NULL COMMENT '密码（bcrypt加密）',
    `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
    `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
    `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像URL',
    `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：0-禁用，1-正常',
    `last_login_at` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted_at` timestamp NULL DEFAULT NULL COMMENT '删除时间（软删除）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`),
    KEY `idx_phone` (`phone`),
    KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 2. 用户提醒设置表（user_remind_setting）
CREATE TABLE `user_remind_setting` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '设置ID',
    `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
    `continuous_entertainment_limit` int NOT NULL DEFAULT '30' COMMENT '连续娱乐时长限制（分钟）',
    `reminder_content` text COMMENT '固定提醒内容',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_id` (`user_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户提醒设置表';

-- 3. 活动记录表（activities）
CREATE TABLE `activities` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '活动ID',
    `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
    `activity_type` enum('entertainment','study','work','life') NOT NULL COMMENT '活动类型',
    `app_name` varchar(100) NOT NULL COMMENT '应用名称',
    `activity_detail` text COMMENT 'AI识别的具体内容描述',
    `capture_time` timestamp NOT NULL COMMENT '截图时刻',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_user_type_time` (`user_id`,`activity_type`,`capture_time`),
    KEY `idx_user_capture_time` (`user_id`,`capture_time`),
    KEY `idx_app_name` (`app_name`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='活动记录表（瞬时状态）';

-- 4. AI分析记录表（ai_analysis_logs）
CREATE TABLE `ai_analysis_logs` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
    `activity_id` bigint unsigned DEFAULT NULL COMMENT '关联的活动ID',
    `image_hash` varchar(64) NOT NULL COMMENT '图片哈希值（用于去重）',
    `ai_model` varchar(50) NOT NULL DEFAULT 'qwen-vl' COMMENT '使用的AI模型',
    `analysis_result` json NOT NULL COMMENT 'AI分析结果（JSON格式）',
    `response_time` int unsigned NOT NULL COMMENT '响应时间（毫秒）',
    `error_code` varchar(20) DEFAULT NULL COMMENT 'API调用错误码',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_user_created` (`user_id`,`created_at`),
    KEY `idx_activity_id` (`activity_id`),
    KEY `idx_image_hash` (`image_hash`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_ai_model` (`ai_model`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='AI分析记录表';

-- 5. 提醒记录表（notifications）
CREATE TABLE `notifications` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '提醒ID',
    `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
    `activity_id` bigint unsigned DEFAULT NULL COMMENT '触发提醒的活动ID',
    `content` text NOT NULL COMMENT '提醒内容',
    `trigger_reason` varchar(100) NOT NULL COMMENT '触发原因',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_user_created` (`user_id`,`created_at`),
    KEY `idx_activity_id` (`activity_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='提醒记录表';

-- 6. 应用白名单表（app_whitelist）
CREATE TABLE `app_whitelist` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '白名单ID',
    `user_id` bigint unsigned NOT NULL COMMENT '用户ID',
    `app_name` varchar(100) NOT NULL COMMENT '应用名称',
    `app_package` varchar(200) DEFAULT NULL COMMENT '应用包名',
    `reason` varchar(200) DEFAULT NULL COMMENT '加入白名单原因',
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_app` (`user_id`,`app_name`),
    KEY `idx_app_package` (`app_package`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='应用白名单表';