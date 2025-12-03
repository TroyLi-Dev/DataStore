# Kotlin Cache Strategy Framework [![](https://jitpack.io/v/TroyLi-Dev/DataStore.svg)](https://jitpack.io/#TroyLi-Dev/DataStore)

一个轻量级 **缓存策略抽象框架**，为 Kotlin 项目提供类型安全的缓存接口和委托封装。  
该框架 **不依赖任何具体存储实现**（如 DataStore 或 MMKV），完全对外提供缓存策略接口，存储由使用者自行实现。

## 特性

- ✅ 提供统一的缓存策略接口（`IStorageStrategy` / `IDataStorePreferences`）  
- ✅ 支持基础数据类型（String, Int, Long, Float, Double, Boolean）  
- ✅ 支持对象序列化存储（Gson 或自定义 Serializer）  
- ✅ Kotlin 委托属性方式访问，类型安全，简化调用  
- ✅ 支持多组缓存（Group/Namespace）  
- ✅ 完全解耦具体存储实现，可任意切换 DataStore、MMKV 或 SharedPreferences  

## 核心概念

- `IStorageStrategy`：缓存策略接口，定义存储/读取/清除等操作  
- `PreferenceDelegate<T>`：Kotlin 委托封装，简化缓存访问  
- `Serializer`：对象序列化接口，可自定义实现  

## 使用示例 Demo

你可以在仓库中查看完整示例：  

- [DataStoreCacheStrategy.kt](./app/src/main/java/com/rui/datastore/DataStoreCacheStrategy.kt) – 使用 Android DataStore 实现的缓存策略  
- [GsonSerializer.kt](./app/src/main/java/com/rui/datastore/GsonSerializer.kt) – Serializer 使用 Gson 实现  
- [AppDataStorePrefs.kt](./app/src/main/java/com/rui/datastore/AppDataStorePrefs.kt) – 集成缓存策略的示例  
- [MainActivity.kt](./app/src/main/java/com/rui/datastore/MainActivity.kt) – 演示如何在应用中使用  

或者直接访问在线 GitHub 仓库 demo：[Demo 示例](app/src/main/java/com/rui/datastore)

## 快速引入

### Step 1: 添加 JitPack 仓库

在项目根目录的 `settings.gradle` 或 `settings.gradle.kts` 文件末尾添加：

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

Step 2: 添加依赖

在模块的 build.gradle 或 build.gradle.kts 文件中添加：

dependencies {
    implementation 'com.github.troyli-dev:datastore:Tag'
}
