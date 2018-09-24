package com.sduduzog.slimlauncher.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface AppDao {

    @get:Query("SELECT * from apps")
    val allApps: LiveData<List<App>>

    @Insert
    fun insert(app: App)

    @Query("DELETE FROM apps")
    fun deleteAll()

    @Query("DELETE FROM apps WHERE package_name=:packageName")
    fun delete(packageName: String)
}
