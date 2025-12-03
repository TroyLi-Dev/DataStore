package com.rui.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


class DataStoreCacheStrategy(
    private val groupName: String,
) : IStorageStrategy {

    private fun applicationContext(): Context{
        return AppContext.context()
    }

    private val Context.dataStore by preferencesDataStore(name = groupName)

    override fun groupName(): String {
        return groupName
    }

    override suspend fun putString(key: String, value: String?) {
        val prefKey = stringPreferencesKey(key)
        applicationContext().dataStore.edit { prefs ->
            if (value == null) {
                prefs.remove(prefKey)
            } else {
                prefs[prefKey] = value
            }
        }
    }

    override suspend fun getString(key: String): String? {
        return applicationContext().dataStore.data
            .map { prefs -> prefs[stringPreferencesKey(key)] }
            .firstOrNull()
    }

    override suspend fun putInt(key: String, value: Int?) {
        val prefKey = intPreferencesKey(key)
        applicationContext().dataStore.edit { prefs ->
            if (value == null) {
                prefs.remove(prefKey)
            } else {
                prefs[prefKey] = value
            }
        }
    }

    override suspend fun getInt(key: String): Int? {
        return applicationContext().dataStore.data
            .map { prefs -> prefs[intPreferencesKey(key)] }
            .firstOrNull()
    }

    override suspend fun putLong(key: String, value: Long?) {
        val prefKey = longPreferencesKey(key)
        applicationContext().dataStore.edit { prefs ->
            if (value == null) {
                prefs.remove(prefKey)
            } else {
                prefs[prefKey] = value
            }
        }
    }

    override suspend fun getLong(key: String): Long? {
        return applicationContext().dataStore.data.map {
            it[longPreferencesKey(key)]
        }.firstOrNull()
    }

    override suspend fun putFloat(key: String, value: Float?) {
        val prefKey = floatPreferencesKey(key)
        applicationContext().dataStore.edit { prefs ->
            if (value == null) {
                prefs.remove(prefKey)
            } else {
                prefs[prefKey] = value
            }
        }
    }

    override suspend fun getFloat(key: String): Float? {
        return applicationContext().dataStore.data.map {
            it[floatPreferencesKey(key)]
        }.firstOrNull()
    }

    override suspend fun putDouble(key: String, value: Double?) {
        val prefKey = doublePreferencesKey(key)
        applicationContext().dataStore.edit { prefs ->
            if (value == null) {
                prefs.remove(prefKey)
            } else {
                prefs[prefKey] = value
            }
        }
    }

    override suspend fun getDouble(key: String): Double? {
        return applicationContext().dataStore.data.map {
            it[doublePreferencesKey(key)]
        }.firstOrNull()
    }

    override suspend fun putBoolean(key: String, value: Boolean?) {
        val prefKey = booleanPreferencesKey(key)
        applicationContext().dataStore.edit { prefs ->
            if (value == null) {
                prefs.remove(prefKey)
            } else {
                prefs[prefKey] = value
            }
        }
    }

    override suspend fun getBoolean(key: String): Boolean? {
        return applicationContext().dataStore.data.map {
            it[booleanPreferencesKey(key)]
        }.firstOrNull()
    }

    override suspend fun clearAll() {
        applicationContext().dataStore.edit { it.clear() }
    }

}

