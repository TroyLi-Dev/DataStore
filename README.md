# Kotlin Cache Strategy Framework

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

DataStore实现方式：[DataStoreCacheStrategy](app/src/main/java/com/rui/datastore/DataStoreCacheStrategy.kt)
Gson实现Serializer：[GsonSerializer](app/src/main/java/com/rui/datastore/GsonSerializer.kt)


## see





```kotlin
// 使用者自定义存储策略实现
class DataStoreStrategy(...) : IStorageStrategy {
    override suspend fun putString(key: String, value: String?) { ... }
    override suspend fun getString(key: String): String? { ... }
    ...
}

val userPrefs = DataStorePreferences.create(
    strategy = DataStoreStrategy(...),
    serializer = GsonSerializer()
)

var userName by userPrefs.string("默认用户名")
var userAge by userPrefs.int(18)


