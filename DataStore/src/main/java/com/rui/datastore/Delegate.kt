package com.rui.datastore

import kotlinx.coroutines.runBlocking
import kotlin.reflect.KProperty

/**
 * 基础偏好项委托（Preference Delegate）
 *
 * 该抽象类负责：
 * - 自动根据属性名生成存储 key（在 provideDelegate 阶段）
 * - 定义 get()/set() 的统一调度入口
 * - 提供阻塞式读取能力（用于 Application 初始化场景）
 *
 * T：表示偏好项实际类型，包括可空类型
 */
abstract class PreferenceDelegate<T : Any?>(
    protected val storeStrategy: IStorageStrategy,
    protected val defaultValue: T
) {

    /** 最终的存储 Key，Kotlin Delegate 在编译期注入 */
    protected var key: String = ""
        private set

    /**
     * Kotlin 在属性声明时调用，用于创建委托实例
     * 此阶段根据属性名动态生成 key。
     */
    operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): PreferenceDelegate<T> {
        key = resolveKey(property)
        return this
    }

    /**
     * 委托读取入口（getValue），返回委托对象本身，
     * 使用者需调用 get()/set() 操作真正读写数据。
     */
    operator fun getValue(thisRef: Any?, property: KProperty<*>): PreferenceDelegate<T> {
        if (key.isBlank()) {
            key = resolveKey(property)
        }
        return this
    }

    /** 读取实际值（挂起函数） */
    abstract suspend fun get(): T

    /** 写入实际值（挂起函数） */
    abstract suspend fun set(value: T)

    /**
     * 提供同步读取能力，常用于应用初始化或需要同步结果的场景。
     * 建议谨慎使用，避免阻塞 UI。
     */
    fun getBlocking(): T = runBlocking { get() }

    /** 根据属性生成 key，可按需加入命名空间或前缀 */
    private fun resolveKey(property: KProperty<*>): String = property.name
}

/**
 * 支持可空类型的偏好项委托基类
 *
 * 用来处理业务上确实需要 null 语义的配置项。
 */
abstract class NullablePreferenceDelegate<T>(
    storeStrategy: IStorageStrategy
) : PreferenceDelegate<T?>(storeStrategy, defaultValue = null)

/**
 * 字符串偏好项委托
 */
class StringPreferenceDelegate(
    storeStrategy: IStorageStrategy,
    defaultValue: String
) : PreferenceDelegate<String>(storeStrategy, defaultValue) {

    override suspend fun get(): String {
        return storeStrategy.getString(key) ?: defaultValue
    }

    override suspend fun set(value: String) {
        storeStrategy.putString(key, value)
    }
}

class IntPreferenceDelegate(
    storeStrategy: IStorageStrategy,
    defaultValue: Int
) : PreferenceDelegate<Int>(storeStrategy, defaultValue) {

    override suspend fun get(): Int {
        return storeStrategy.getInt(key) ?: defaultValue
    }

    override suspend fun set(value: Int) {
        storeStrategy.putInt(key, value)
    }
}


class LongPreferenceDelegate(
    storeStrategy: IStorageStrategy,
    defaultValue: Long
) : PreferenceDelegate<Long>(storeStrategy, defaultValue) {

    override suspend fun get(): Long {
        return storeStrategy.getLong(key) ?: defaultValue
    }

    override suspend fun set(value: Long) {
        storeStrategy.putLong(key, value)
    }
}

class FloatPreferenceDelegate(
    storeStrategy: IStorageStrategy,
    defaultValue: Float
) : PreferenceDelegate<Float>(storeStrategy, defaultValue) {

    override suspend fun get(): Float {
        return storeStrategy.getFloat(key) ?: defaultValue
    }

    override suspend fun set(value: Float) {
        storeStrategy.putFloat(key, value)
    }
}

class DoublePreferenceDelegate(
    storeStrategy: IStorageStrategy,
    defaultValue: Double
) : PreferenceDelegate<Double>(storeStrategy, defaultValue) {

    override suspend fun get(): Double {
        return storeStrategy.getDouble(key) ?: defaultValue
    }

    override suspend fun set(value: Double) {
        storeStrategy.putDouble(key, value)
    }
}

class BooleanPreferenceDelegate(
    storeStrategy: IStorageStrategy,
    defaultValue: Boolean
) : PreferenceDelegate<Boolean>(storeStrategy, defaultValue) {

    override suspend fun get(): Boolean {
        return storeStrategy.getBoolean(key) ?: defaultValue
    }

    override suspend fun set(value: Boolean) {
        storeStrategy.putBoolean(key, value)
    }
}

/**
 * 非空对象序列化委托
 *
 * 用于存储业务对象（配置模型、用户缓存、小型状态模型等）。
 */
class ObjectPreferenceDelegate<T>(
    storeStrategy: IStorageStrategy,
    private val serializer: Serializer,
    private val clazz: Class<T>,
    defaultValue: T,
) : PreferenceDelegate<T>(storeStrategy, defaultValue) {

    override suspend fun get(): T {
        val json = storeStrategy.getString(key)
        if (json.isNullOrBlank()) return defaultValue
        return serializer.fromJson(json, clazz) ?: defaultValue
    }

    override suspend fun set(value: T) {
        val json = serializer.toJson(value)
        storeStrategy.putString(key, json)
    }
}

/**
 * 可空对象序列化委托（区分 null 语义）
 */
class NullableObjectPreferenceDelegate<T>(
    storeStrategy: IStorageStrategy,
    private val serializer: Serializer,
    private val clazz: Class<T>
) : NullablePreferenceDelegate<T>(storeStrategy) {

    override suspend fun get(): T? {
        val json = storeStrategy.getString(key)
        if (json.isNullOrBlank()) return null
        return serializer.fromJson(json, clazz)
    }

    override suspend fun set(value: T?) {
        val json = value?.let { serializer.toJson(it) }
        storeStrategy.putString(key, json)
    }
}

