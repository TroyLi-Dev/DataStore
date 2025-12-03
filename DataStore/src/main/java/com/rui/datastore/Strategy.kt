package com.rui.datastore


/**
 * 定义统一的 Key-Value 存储策略抽象层。
 *
 * 该接口旨在屏蔽底层存储实现（如 DataStore / MMKV / SharedPreferences），
 * 为上层业务提供稳定、可扩展、类型安全的 Key-Value 操作能力。
 *
 * 使用场景：
 * - 用户配置存储
 * - 应用本地状态存储
 * - 轻量级业务信息本地化
 *
 * 设计原则：
 * 1. 方法类型化（String/Int/Boolean…），避免外部直接访问底层 Preferences API。
 * 2. 所有操作均为 suspend，确保与 DataStore 异步范式一致。
 * 3. 支持 nullable，value == null 时将视为删除该 Key。
 * 4. 强制暴露 groupName，便于多 DataStore 分组管理。
 */
interface IStorageStrategy {

    /**
     * 返回当前存储策略的组名。
     *
     * 用于区分不同存储空间（如 user_config / app_state ），
     * 通常映射到单独的 DataStore 文件名。
     */
    fun groupName(): String

    /** -------------------- String -------------------- */

    /**
     * 保存字符串；value 为 null 时删除该键。
     */
    suspend fun putString(key: String, value: String?)

    /**
     * 获取字符串；若 key 不存在，返回 null。
     */
    suspend fun getString(key: String): String?


    /** -------------------- Int -------------------- */

    suspend fun putInt(key: String, value: Int?)
    suspend fun getInt(key: String): Int?


    /** -------------------- Long -------------------- */

    suspend fun putLong(key: String, value: Long?)
    suspend fun getLong(key: String): Long?


    /** -------------------- Float -------------------- */

    suspend fun putFloat(key: String, value: Float?)
    suspend fun getFloat(key: String): Float?


    /** -------------------- Double -------------------- */

    suspend fun putDouble(key: String, value: Double?)
    suspend fun getDouble(key: String): Double?


    /** -------------------- Boolean -------------------- */

    suspend fun putBoolean(key: String, value: Boolean?)
    suspend fun getBoolean(key: String): Boolean?


    /** -------------------- Clear Ops -------------------- */

    /**
     * 清空当前存储空间内所有 Key。
     *
     * 常用于：
     * - 用户登出
     * - 环境切换
     * - 配置重置
     */
    suspend fun clearAll()
}

