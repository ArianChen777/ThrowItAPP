-- 插入测试数据
-- 版本：V2.0
-- 创建时间：2025-07-10

-- 插入测试用户
INSERT INTO `users` (`username`, `password`, `email`, `phone`, `status`, `created_at`, `updated_at`) VALUES
('testuser', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'test@throwit.com', '13800138000', 1, NOW(), NOW()),
('demouser', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'demo@throwit.com', '13800138001', 1, NOW(), NOW());

-- 为测试用户插入默认提醒设置
INSERT INTO `user_remind_setting` (`user_id`, `continuous_entertainment_limit`, `reminder_content`, `created_at`, `updated_at`) VALUES
(1, 30, '亲爱的，你已经娱乐一段时间了，是不是该休息一下或者做点其他事情呢？', NOW(), NOW()),
(2, 45, '适度娱乐有益身心，但也别忘了自己的目标哦！要不要暂停一下，喝杯水休息一会？', NOW(), NOW());

-- 插入一些测试活动记录
INSERT INTO `activities` (`user_id`, `activity_type`, `app_name`, `activity_detail`, `capture_time`, `created_at`) VALUES
(1, 'entertainment', '抖音', '正在观看搞笑视频', DATE_SUB(NOW(), INTERVAL 1 HOUR), NOW()),
(1, 'study', '网易云课堂', '正在学习Java编程课程', DATE_SUB(NOW(), INTERVAL 2 HOUR), NOW()),
(1, 'work', '微信', '正在与同事讨论工作项目', DATE_SUB(NOW(), INTERVAL 3 HOUR), NOW()),
(2, 'entertainment', '王者荣耀', '正在进行排位赛', DATE_SUB(NOW(), INTERVAL 30 MINUTE), NOW()),
(2, 'life', '美团', '正在浏览外卖菜单', DATE_SUB(NOW(), INTERVAL 1.5 HOUR), NOW());

-- 插入一些测试提醒记录
INSERT INTO `notifications` (`user_id`, `activity_id`, `content`, `trigger_reason`, `created_at`) VALUES
(1, 1, '亲爱的，你已经娱乐一段时间了，是不是该休息一下或者做点其他事情呢？', '连续娱乐超时', DATE_SUB(NOW(), INTERVAL 30 MINUTE)),
(2, 4, '适度娱乐有益身心，但也别忘了自己的目标哦！要不要暂停一下，喝杯水休息一会？', '连续娱乐超时', DATE_SUB(NOW(), INTERVAL 15 MINUTE));

-- 插入一些应用白名单测试数据
INSERT INTO `app_whitelist` (`user_id`, `app_name`, `app_package`, `reason`, `created_at`) VALUES
(1, '微信', 'com.tencent.mm', '工作必需的沟通工具', NOW()),
(1, 'QQ', 'com.tencent.mobileqq', '联系朋友和家人', NOW()),
(2, '钉钉', 'com.alibaba.android.rimet', '公司办公软件', NOW());