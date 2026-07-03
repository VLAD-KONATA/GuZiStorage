本项目采用 Gradle Version Catalog（libs.versions.toml）进行集中式的第三方库版本管理与声明，配合 dependencyResolutionManagement 统一仓库源策略，实现全项目一致的依赖治理。

1. 系统/工具
- Gradle Version Catalog：所有版本号集中在 gradle/libs.versions.toml 的 [versions] 段中定义，通过 version.ref = xxx 引用；库坐标在 [libraries] 段以 group/name 形式注册，模块内使用 libs.xxx 别名引用。
- Compose BOM：通过 platform(libs.androidx.compose.bom) 引入 Compose 组件的版本对齐，避免子库版本冲突。
- KSP + Hilt KAPT 混用：Room 注解处理器使用 KSP（ksp(libs.androidx.room.compiler)），Hilt 仍使用传统 KAPT（kapt(libs.hilt.android.compiler)），并在根 build.gradle.kts 中统一声明插件别名。

2. 关键文件
- GuZiStorage/gradle/libs.versions.toml：全局版本与库别名定义中心。
- GuZiStorage/settings.gradle.kts：仓库源与 RepositoriesMode.FAIL_ON_PROJECT_REPOS 强制策略。
- GuZiStorage/build.gradle.kts：根级插件别名集中声明（android、kotlin、compose、hilt、ksp）。
- GuZiStorage/app/build.gradle.kts：应用模块依赖消费入口，仅通过 implementation(libs.xxx) / ksp(...) / kapt(...) 引用。

3. 架构与约定
- 单一仓库源策略：settings.gradle.kts 中设置 repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)，禁止各子模块自行添加仓库，确保依赖来源可审计。
- 插件与版本解耦：根 build.gradle.kts 只 apply false 声明插件别名，实际 apply 发生在模块层，便于后续迁移到多模块或共享配置。
- 依赖分类清晰：按 AndroidX Core、Lifecycle、Compose、Navigation、Room、Hilt、Coil、Coroutines、Test 等维度分组注释，新增依赖需先在 libs.versions.toml 注册再在模块中使用。
- 测试依赖隔离：testImplementation / androidTestImplementation / debugImplementation 明确区分运行期与调试期依赖，避免污染发布产物。

4. 开发者应遵循的规则
- 新增第三方库时，先在 gradle/libs.versions.toml 的 [versions] 定义版本号，再在 [libraries] 注册别名，最后在模块 dependencies 块中以 libs.<alias> 引用，禁止直接写死 group/version。
- 不要在任何子模块的 build.gradle.kts 中添加 repositories { ... }，否则构建会因 FAIL_ON_PROJECT_REPOS 失败；如需私有仓库，应在 settings.gradle.kts 的 dependencyResolutionManagement.repositories 中统一配置。
- 升级 Compose 相关库时优先更新 composeBom 版本，让 BOM 自动协调各子库版本，避免手动逐个调整。
- Room 注解处理器使用 KSP，Hilt 注解处理器使用 KAPT，两者并存时需保持各自对应的 ksp(...) / kapt(...) 调用，不要混用。
- 新增依赖后检查是否属于 debugImplementation 或 androidTestImplementation，避免将调试/测试库带入 release 包。