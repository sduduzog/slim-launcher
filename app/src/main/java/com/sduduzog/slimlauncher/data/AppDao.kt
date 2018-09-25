package com.sduduzog.slimlauncher.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface AppDao {

    @get:Query("SELECT * from apps")
    val allApps: LiveData<List<App>>

    @get:Query("SELECT * from apps WHERE home=1")
    val homeApps: LiveData<List<App>>

    @get:Query("SELECT * from apps WHERE home=0")
    val availableApps: LiveData<List<App>>

    @Insert
    fun insert(app: App)

    @Update
    fun update(app: App)

    @Query("DELETE FROM apps")
    fun deleteAll()

    @Query("DELETE FROM apps WHERE package_name=:packageName")
    fun delete(packageName: String)
}
