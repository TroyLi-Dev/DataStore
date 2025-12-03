package com.rui.datastore

import android.app.Application
import android.content.Context

object AppContext {

    @Volatile
    private var application: Application? = null

    /**
     * 初始化全局 Application
     * 请务必在 Application.onCreate 中调用
     */
    fun init(app: Application) {
        if (application == null) {
            synchronized(this) {
                if (application == null) {
                    application = app
                }
            }
        }
    }

    /**
     * 获取全局 Application
     * 若未初始化则抛异常，避免业务层错误使用
     */
    fun application(): Application {
        return application
            ?: throw IllegalStateException("AppContext not initialized. Call AppContext.init(app) in Application.onCreate()")
    }

    /**
     * 获取全局 ApplicationContext
     */
    fun context(): Context {
        return application().applicationContext
    }
}
