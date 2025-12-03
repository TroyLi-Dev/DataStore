package com.rui.datastore

import java.util.concurrent.ConcurrentHashMap

/**
 * 全局偏好存储访问入口（抽象层）
 *
 * 用于为业务域提供统一的键值对存储能力，屏蔽底层存储介质
 *（如 DataStore、MMKV、数据库等）。
 *
 * 每种数据类型通过对应的 PreferenceDelegate 实现
 * 读取/写入逻辑。
 */
interface IDataStorePreferences {

    fun dataStore(): IStorageStrategy

    /**
     * 构建 String 类型的委托属性
     */
    fun string(default: String): PreferenceDelegate<String>

    /**
     * 构建 Int 类型的委托属性
     */
    fun int(default: Int): PreferenceDelegate<Int>

    /**
     * 构建 Long 类型的委托属性
     */
    fun long(default: Long): PreferenceDelegate<Long>

    /**
     * 枠建 Float 类型的委托属性
     */
    fun float(default: Float): PreferenceDelegate<Float>

    /**
     * 构建 Double 类型的委托属性
     */
    fun double(default: Double): PreferenceDelegate<Double>

    /**
     * 构建 Boolean 类型的委托属性
     */
    fun boolean(default: Boolean): PreferenceDelegate<Boolean>

    /**
     * 构建泛型对象的委托属性（非空版本）
     */
    fun <T : Any> json(clazz: Class<T>, default: T): PreferenceDelegate<T>

    /**
     * 构建泛型对象的委托属性（可空版本）
     */
    fun <T : Any> json(clazz: Class<T>): PreferenceDelegate<T?>

    /**
     * 清空当前 group 名下所有数据
     */
    suspend fun clearAll()

}

/**
 * PreferenceStore 默认实现类（基于 IStorageStrategy + JSON Serializer）
 *
 * 提供：
 * - 类型安全的偏好存储访问
 * - 全局实例缓存（以 groupName 维度隔离）
 * - 所有基础类型及 JSON 对象的委托构建能力
 */
class DataStorePreferences private constructor(
    private val strategy: IStorageStrategy,
    private val serializer: Serializer
) : IDataStorePreferences {

    companion object {

        /**
         * 全局实例池：
         * key = Strategy.groupName()
         */
        private val instances: MutableMap<String, IDataStorePreferences> = ConcurrentHashMap()

        /**
         * 获取或创建 PreferenceStore 实例
         *
         * 保证同 groupName 只会存在一个实例，避免重复初始化
         */
        fun create(
            strategy: IStorageStrategy,
            serializer: Serializer
        ): IDataStorePreferences {
            val instanceKey = strategy.groupName()
            return instances.computeIfAbsent(instanceKey) {
                build(
                    strategy = strategy,
                    serializer = serializer
                )
            }
        }

        /**
         * 构建新的 PreferenceStore 实例
         */
        private fun build(
            strategy: IStorageStrategy,
            serializer: Serializer
        ): IDataStorePreferences {
            return DataStorePreferences(
                strategy = strategy,
                serializer = serializer
            )
        }
    }

    override fun dataStore(): IStorageStrategy {
        return strategy
    }

    override fun string(default: String): PreferenceDelegate<String> {
        return StringPreferenceDelegate(storeStrategy = strategy, defaultValue = default)
    }

    override fun int(default: Int): PreferenceDelegate<Int> {
        return IntPreferenceDelegate(storeStrategy = strategy, defaultValue = default)
    }

    override fun long(default: Long): PreferenceDelegate<Long> {
        return LongPreferenceDelegate(storeStrategy = strategy, defaultValue = default)
    }

    override fun float(default: Float): PreferenceDelegate<Float> {
        return FloatPreferenceDelegate(storeStrategy = strategy, defaultValue = default)
    }

    override fun double(default: Double): PreferenceDelegate<Double> {
        return DoublePreferenceDelegate(storeStrategy = strategy, defaultValue = default)
    }

    override fun boolean(default: Boolean): PreferenceDelegate<Boolean> {
        return BooleanPreferenceDelegate(storeStrategy = strategy, defaultValue = default)
    }

    override fun <T : Any> json(clazz: Class<T>, default: T): PreferenceDelegate<T> {
        return ObjectPreferenceDelegate(
            storeStrategy = strategy,
            serializer = serializer,
            clazz = clazz,
            defaultValue = default
        )
    }

    override fun <T : Any> json(clazz: Class<T>): PreferenceDelegate<T?> {
        return NullableObjectPreferenceDelegate(
            storeStrategy = strategy,
            serializer = serializer,
            clazz = clazz
        )
    }

    override suspend fun clearAll() {
        strategy.clearAll()
    }

}
