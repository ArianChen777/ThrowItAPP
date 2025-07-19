# ThrowIt Android App

基于 Jetpack Compose 的 Android 应用，实现 AI 驱动的反沉迷生产力工具。

## 项目结构

```
app/
├── src/main/java/com/throwit/app/
│   ├── ThrowItApplication.kt          # 应用入口
│   ├── ui/                           # UI 层
│   │   ├── MainActivity.kt           # 主活动
│   │   ├── ThrowItNavGraph.kt        # 导航图
│   │   ├── main/                     # 主页面
│   │   │   ├── MainScreen.kt         # 主屏幕组件
│   │   │   └── MainViewModel.kt      # 主页面视图模型
│   │   └── theme/                    # 主题配置
│   │       ├── Color.kt              # 颜色定义
│   │       ├── Theme.kt              # 主题配置
│   │       └── Type.kt               # 字体配置
│   ├── data/                         # 数据层
│   │   ├── api/                      # API 接口
│   │   │   └── ApiService.kt         # Retrofit 服务接口
│   │   ├── model/                    # 数据模型
│   │   │   ├── User.kt               # 用户模型
│   │   │   ├── Activity.kt           # 活动模型
│   │   │   └── ApiResponse.kt        # API 响应模型
│   │   └── repository/               # 数据仓库
│   │       ├── UserRepository.kt     # 用户数据仓库
│   │       └── ActivityRepository.kt # 活动数据仓库
│   ├── di/                           # 依赖注入
│   │   ├── NetworkModule.kt          # 网络模块
│   │   ├── RepositoryModule.kt       # 仓库模块
│   │   └── AppModule.kt              # 应用模块
│   └── util/                         # 工具类
│       ├── Constants.kt              # 常量定义
│       └── PreferencesManager.kt     # SharedPreferences 管理
└── res/                              # 资源文件
    ├── values/
    │   ├── strings.xml               # 字符串资源
    │   └── themes.xml                # 主题资源
    └── xml/
        ├── backup_rules.xml          # 备份规则
        └── data_extraction_rules.xml # 数据提取规则
```

## 技术栈

- **开发语言**: Kotlin
- **UI 框架**: Jetpack Compose
- **架构模式**: MVVM
- **网络请求**: Retrofit + OkHttp
- **本地存储**: SharedPreferences (后续可扩展 Room)
- **异步编程**: Kotlin Coroutines + Flow
- **依赖注入**: Hilt
- **图片加载**: Coil

## 核心功能

1. **主页面展示**
   - 定时监控开关
   - 实时状态显示
   - 统计数据展示

2. **用户管理**
   - 用户注册/登录
   - 用户信息管理

3. **活动监控**
   - 截图分析
   - 活动记录
   - 统计报告

4. **设置管理**
   - 提醒阈值设置
   - 个性化配置

## 启动说明

1. 确保后端服务正在运行
2. 修改 `NetworkModule.kt` 中的 `BASE_URL` 为实际的后端地址
3. 构建并运行 Android 应用

## 开发规范

- 遵循 Material Design 3 设计规范
- 使用 Jetpack Compose 声明式 UI
- 采用单向数据流架构
- 所有网络请求使用 Flow 进行响应式处理
- 统一错误处理和状态管理