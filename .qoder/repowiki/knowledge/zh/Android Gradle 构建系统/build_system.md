本项目采用 Android Gradle Plugin (AGP) + Kotlin DSL 的构建体系，通过版本目录（Version Catalog）集中管理依赖与插件版本。

核心工具链：AGP 8.5.2、Kotlin 2.0.21、Compose BOM 2024.12.01；使用 KSP 处理 Room 注解处理器，Hilt 仍保留 kapt；Java/Kotlin 目标级别为 17，minSdk 26，target/compileSdk 34。

版本与依赖管理：gradle/libs.versions.toml 统一声明所有版本号与库别名，模块通过 alias(libs.plugins.*) 和 libs.* 引用；settings.gradle.kts 启用 RepositoriesMode.FAIL_ON_PROJECT_REPOS，禁止子模块自行声明仓库；Compose 相关依赖通过 BOM 平台对齐版本。

模块结构：单模块应用，仅包含 :app 模块，根 build.gradle.kts 仅声明插件并 apply false，实际构建配置集中在 app/build.gradle.kts；命名空间 com.guzi.storage，applicationId 一致。

构建变体与优化：release 变体默认关闭代码混淆但保留了 ProGuard 规则文件路径；启用 Compose 构建特性；kapt { correctErrorTypes = true } 用于缓解 Hilt 注解处理的类型推断问题。

Gradle 性能调优：gradle.properties 设置 JVM 堆内存 2048m、UTF-8 编码、非传递性 RClass 以加速增量编译。

缺失能力：无 CI/CD 流水线、无签名配置、多渠道打包、自动化发布脚本、自定义 Gradle Task。