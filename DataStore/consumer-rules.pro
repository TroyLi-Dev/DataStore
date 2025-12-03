# =============================
# DataStoreCacheStrategy & PreferenceDelegate
# =============================

# 保留所有委托类，防止 property.name 被混淆
-keep class com.rui.datastore.PreferenceDelegate { *; }
-keep class com.rui.datastore.NullablePreferenceDelegate { *; }

-keep class com.rui.datastore.StringPreferenceDelegate { *; }
-keep class com.rui.datastore.IntPreferenceDelegate { *; }
-keep class com.rui.datastore.LongPreferenceDelegate { *; }
-keep class com.rui.datastore.FloatPreferenceDelegate { *; }
-keep class com.rui.datastore.DoublePreferenceDelegate { *; }
-keep class com.rui.datastore.BooleanPreferenceDelegate { *; }

-keep class com.rui.datastore.ObjectPreferenceDelegate { *; }
-keep class com.rui.datastore.NullableObjectPreferenceDelegate { *; }

# =============================
# Serializer
# =============================

# 保留序列化接口和实现
-keep interface com.rui.datastore.Serializer { *; }

# Gson 序列化时需要保留 model 类字段名
# 如果你使用 Gson，建议为 model 类加 @SerializedName 或全局保留字段名
# 例：
# -keepclassmembers class com.rui.datastore.models.** { *; }

# =============================
# IStorageStrategy 接口
# =============================
-keep interface com.rui.datastore.IStorageStrategy { *; }

# =============================
# 全局 DataStorePreferences 单例
# =============================
-keep class com.rui.datastore.DataStorePreferences { *; }

# =============================
# Kotlin 库混淆建议
# =============================
# 保留 Kotlin 元数据，避免委托、反射调用异常
-keepclassmembers class kotlin.Metadata { *; }
