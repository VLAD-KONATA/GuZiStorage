该 Android 应用采用 Jetpack Compose + Material Design 3 作为前端样式体系，通过集中式主题文件管理视觉风格。

核心架构与文件组织：
- ui/theme/Color.kt：定义设计令牌（Design Tokens），包含明暗两套配色方案及自定义主色调（柔和紫色系，契合二次元主题）
- ui/theme/Theme.kt：封装 GuZiStorageTheme Composable，统一配置 ColorScheme、Typography、Shapes 等主题属性
- res/values/themes.xml：传统 Android 主题，继承自 Material.Light.NoActionBar，仅保留最简配置

关键设计决策：
1. 动态色彩支持：在 Android 12+（API 31+）上启用系统级动态取色（dynamicLightColorScheme / dynamicDarkColorScheme），低版本回退到静态配色
2. 深色模式适配：通过 isSystemInDarkTheme() 自动跟随系统主题，同时提供独立的 DarkColorScheme
3. Material3 语义化颜色：使用 primary/secondary/tertiary/background/surface 等语义化角色而非硬编码颜色值
4. Typography 规范：组件文本样式通过 MaterialTheme.typography 访问，保证字体层级一致性

开发约定：
- 所有 UI 组件必须包裹在 GuZiStorageTheme 中，禁止直接使用硬编码颜色或字体
- 新增颜色应添加到 Color.kt 并遵循命名规范（Primary/Secondary/Background/Surface/Error）
- 主题扩展点集中在 Theme.kt 的 GuZiStorageTheme Composable 内