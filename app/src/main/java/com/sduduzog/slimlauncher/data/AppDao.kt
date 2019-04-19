package com.sduduzog.slimlauncher.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sduduzog.slimlauncher.data.model.HomeApp

@Dao
interface AppDao {

    @get:Query("SELECT * FROM home_apps ORDER BY sorting_index ASC")
    val apps: LiveData<List<HomeApp>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg apps: HomeApp)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(app: HomeApp)

    @Query("DELETE FROM home_apps")
    fun clearHomeApps()

    @Delete
    fun remove(vararg app: HomeApp)

}
