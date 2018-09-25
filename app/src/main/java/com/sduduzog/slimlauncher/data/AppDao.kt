package com.sduduzog.slimlauncher.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface AppDao {


    @get:Query("SELECT * FROM apps WHERE package_name NOT IN (SELECT apps.package_name from apps JOIN home_apps ON home_apps.package_name=apps.package_name)")
    val apps: LiveData<List<App>>

    @get:Query("SELECT * FROM home_apps")
    val homeApps: LiveData<List<HomeApp>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(app: App)

    @Update
    fun update(app: App)

    @Insert
    fun addHomeApp(app: HomeApp)

    @Query("DELETE FROM apps")
    fun deleteAll()

    @Delete
    fun delete(app: HomeApp)

}
