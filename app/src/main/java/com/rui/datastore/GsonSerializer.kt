package com.rui.datastore

import com.google.gson.Gson
import java.lang.reflect.Type


object GsonSerializer : Serializer {

    private val gson = Gson()


    override fun <T> toJson(value: T): String = gson.toJson(value)


    override fun <T> fromJson(json: String?, clazz: Class<T>): T? {
        if (json.isNullOrBlank()) {
            return null
        }
        return gson.fromJson(json, clazz)
    }


    override fun <T> fromJson(json: String?, typeToken: Type): T? {
        if (json.isNullOrBlank()) {
            return null
        }
        return gson.fromJson(json, typeToken)
    }

}