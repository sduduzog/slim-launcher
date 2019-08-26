package com.sduduzog.slimlauncher.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sduduzog.slimlauncher.data.entity.HomeApp

@Dao
interface AppDao {

    @get:Query("SELECT * FROM home_apps ORDER BY sorting_index ASC")
    val apps: LiveData<List<HomeApp>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(app: HomeApp)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg apps: HomeApp)

    @Delete
    fun remove(vararg app: HomeApp)
}