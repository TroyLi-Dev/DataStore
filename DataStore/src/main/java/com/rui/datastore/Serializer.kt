package com.rui.datastore


import java.lang.reflect.Type

/**
 * Serializer 接口
 *
 * 用于对象与 JSON 字符串之间的序列化和反序列化。
 * 支持通用对象类型（Class<T>）以及复杂泛型类型（Type）。
 */
interface Serializer {

    /**
     * 将任意对象序列化为 JSON 字符串
     *
     * @param value 待序列化对象
     * @return 对象对应的 JSON 字符串
     */
    fun <T> toJson(value: T): String

    /**
     * 将 JSON 字符串反序列化为指定类型的对象
     *
     * @param json JSON 字符串
     * @param clazz 对象的 Class 类型
     * @return 反序列化后的对象
     */
    fun <T> fromJson(json: String?, clazz: Class<T>): T?

    /**
     * 将 JSON 字符串反序列化为指定 Type 的对象
     *
     * 适用于泛型类型，例如 List<User>、Map<String, Any> 等
     *
     * @param json JSON 字符串
     * @param typeToken 对象的 Type 类型
     * @return 反序列化后的对象
     */
    fun <T> fromJson(json: String?, typeToken: Type): T?
}