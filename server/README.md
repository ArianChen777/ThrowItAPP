# ThrowIt Server

基于Spring Boot + MyBatis的AI驱动反沉迷生产力工具后端服务。

## 项目简介

ThrowIt是一款创新的生产力工具，通过AI技术帮助用户戒断"娱乐沉迷"，将更多时间投入到学习和工作中。后端服务负责：

- 用户认证与管理
- 截图AI分析
- 活动记录与统计
- 智能提醒生成
- 用户设置管理

## 技术栈

- **开发语言**: Java 17
- **框架**: Spring Boot 3.2.5
- **数据库**: MySQL 8.0
- **ORM**: MyBatis 3.0.3
- **安全认证**: Spring Security + JWT
- **AI服务**: OpenAI GPT-4 Vision
- **构建工具**: Maven
- **部署**: Docker

## 快速开始

### 环境要求

- JDK 17+
- MySQL 8.0+
- Maven 3.8+

### 数据库准备

1. 创建数据库：
```sql
CREATE DATABASE throwit CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 创建用户：
```sql
CREATE USER 'throwit'@'localhost' IDENTIFIED BY 'throwit123456';
GRANT ALL PRIVILEGES ON throwit.* TO 'throwit'@'localhost';
FLUSH PRIVILEGES;
```

3. 执行数据库脚本：
```bash
# 进入项目目录
cd server

# 执行建表脚本
mysql -u throwit -p throwit < src/main/resources/db/migration/V001__Create_Tables.sql

# 执行测试数据脚本（可选）
mysql -u throwit -p throwit < src/main/resources/db/migration/V002__Insert_Test_Data.sql
```

### 配置文件

复制并修改配置文件：

```bash
# 开发环境配置
cp src/main/resources/application-dev.yml.example src/main/resources/application-dev.yml

# 编辑配置文件，修改数据库连接信息
vim src/main/resources/application-dev.yml
```

### 运行应用

```bash
# 编译项目
mvn clean compile

# 运行应用（开发环境）
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# 或者打包后运行
mvn clean package
java -jar target/throwit-server-1.0.0-SNAPSHOT.jar --spring.profiles.active=dev
```

应用启动后，访问 http://localhost:8080

### 测试账号

已预置测试账号（密码均为 `password`）：

- 用户名：`testuser`，邮箱：`test@throwit.com`
- 用户名：`demouser`，邮箱：`demo@throwit.com`

## API文档

### 认证接口

#### 用户注册
```http
POST /api/v1/register
Content-Type: application/json

{
  "username": "newuser",
  "password": "password123",
  "email": "newuser@example.com",
  "phone": "13800138000"
}
```

#### 用户登录
```http
POST /api/v1/login
Content-Type: application/json

{
  "username": "testuser",
  "password": "password"
}
```

### 截图分析接口

#### 上传截图
```http
POST /api/v1/app/upload
Authorization: Bearer <token>
Content-Type: multipart/form-data

screenshot: <image_file>
captureTime: 2025-07-10T14:30:00 (可选)
```

### 用户信息接口

#### 获取用户信息
```http
GET /api/v1/user/info
Authorization: Bearer <token>
```

#### 更新用户资料
```http
PUT /api/v1/user/profile
Authorization: Bearer <token>
Content-Type: application/json

{
  "email": "newemail@example.com",
  "phone": "13900139000",
  "avatarUrl": "https://example.com/avatar.jpg"
}
```

### 设置接口

#### 获取提醒设置
```http
GET /api/v1/user/settings/reminders
Authorization: Bearer <token>
```

#### 更新提醒设置
```http
PUT /api/v1/user/settings/reminders
Authorization: Bearer <token>
Content-Type: application/json

{
  "continuousEntertainmentLimit": 45,
  "reminderContent": "自定义提醒内容"
}
```

### 统计接口

#### 获取今日活动统计
```http
GET /api/v1/activity/today
Authorization: Bearer <token>
```

## 配置说明

### AI服务配置

在生产环境中，需要配置OpenAI API密钥：

```yaml
app:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY} # 通过环境变量设置
      model: gpt-4o # 可选，默认使用gpt-4o
```

或在启动时设置环境变量：
```bash
export OPENAI_API_KEY=your_api_key_here
java -jar throwit-server.jar
```

### JWT配置

生产环境中建议设置自定义JWT密钥：

```yaml
app:
  jwt:
    secret: ${JWT_SECRET} # 至少32位字符
    expiration: 86400 # 24小时
```

## 项目结构

```
server/
├── src/main/java/com/throwit/app/
│   ├── ThrowItApplication.java      # 启动类
│   ├── client/                      # 外部服务客户端
│   │   └── OpenAIVisionClient.java # OpenAI Vision客户端
│   ├── config/                      # 配置类
│   │   └── SecurityConfig.java     # 安全配置
│   ├── controller/                  # 控制器
│   │   ├── AuthController.java     # 认证控制器
│   │   ├── UserController.java     # 用户控制器
│   │   ├── ScreenshotController.java # 截图控制器
│   │   ├── ActivityController.java  # 活动控制器
│   │   └── SettingsController.java  # 设置控制器
│   ├── dto/                         # 数据传输对象
│   ├── exception/                   # 异常处理
│   │   ├── BusinessException.java  # 业务异常
│   │   └── GlobalExceptionHandler.java # 全局异常处理
│   ├── mapper/                      # MyBatis接口
│   ├── model/                       # 实体类
│   ├── security/                    # 安全相关
│   │   ├── JwtAuthenticationFilter.java
│   │   └── JwtAuthenticationEntryPoint.java
│   ├── service/                     # 业务服务
│   │   └── impl/                   # 服务实现
│   └── util/                        # 工具类
│       └── JwtUtil.java            # JWT工具
└── src/main/resources/
    ├── mappers/                     # MyBatis映射文件
    ├── db/migration/                # 数据库脚本
    ├── application.yml              # 应用配置
    ├── application-dev.yml          # 开发环境配置
    └── application-prod.yml         # 生产环境配置
```

## 开发指南

### 添加新功能

1. 在`model`包中定义实体类
2. 在`mapper`包中定义数据访问接口
3. 在`resources/mappers`中编写SQL映射
4. 在`service`包中定义业务接口和实现
5. 在`controller`包中定义API端点
6. 在`dto`包中定义请求/响应对象

### 代码规范

- 遵循阿里巴巴Java开发手册
- 使用Lombok减少样板代码
- 接口和实现分离
- 统一异常处理
- 详细的日志记录

### 测试

```bash
# 运行单元测试
mvn test

# 运行集成测试
mvn verify
```

## 部署

### Docker部署

```bash
# 构建镜像
docker build -t throwit-server .

# 运行容器
docker run -d \
  --name throwit-server \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DB_HOST=mysql \
  -e DB_PASSWORD=your_password \
  -e OPENAI_API_KEY=your_api_key \
  -e JWT_SECRET=your_jwt_secret \
  throwit-server
```

### 使用Docker Compose

```bash
# 启动所有服务
docker-compose up -d

# 查看日志
docker-compose logs -f
```

## 监控

应用集成了Spring Boot Actuator，提供健康检查和监控端点：

- 健康检查：`GET /actuator/health`
- 应用信息：`GET /actuator/info`
- 指标数据：`GET /actuator/metrics`

## 故障排查

### 常见问题

1. **数据库连接失败**
   - 检查数据库服务是否启动
   - 验证连接配置和凭据
   - 确认网络连通性

2. **JWT Token无效**
   - 检查密钥配置
   - 确认Token未过期
   - 验证请求头格式

3. **AI分析失败**
   - 检查API密钥配置
   - 验证网络连接
   - 查看错误日志

### 日志查看

```bash
# 查看应用日志
tail -f logs/throwit-server.log

# 查看特定级别日志
grep "ERROR" logs/throwit-server.log
```

## 许可证

MIT License

## 联系方式

- 项目地址：https://github.com/throwit/throwit-server
- 问题反馈：https://github.com/throwit/throwit-server/issues