package com.rui.datastore

object AppDataStorePrefs: IDataStorePreferences by DataStorePreferences.create(
    strategy = DataStoreCacheStrategy(groupName = "AppInfoCache"),
    serializer = GsonSerializer
)  {


    val testText by string(default = "我是一条默认数据")

}