package com.sduduzog.slimlauncher.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sduduzog.slimlauncher.data.model.HomeApp
import com.sduduzog.slimlauncher.data.model.Note
import com.sduduzog.slimlauncher.data.model.Task

@Dao
interface BaseDao {

    @get:Query("SELECT * FROM home_apps ORDER BY sorting_index ASC")
    val apps: LiveData<List<HomeApp>>

    @get:Query("SELECT * FROM `notes` ORDER BY `id` DESC")
    val notes: LiveData<List<Note>>

    @get:Query("SELECT * FROM `tasks` ORDER BY `sorting_index` DESC")
    val tasks: LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(app: HomeApp)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(task: Task)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg apps: HomeApp)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg task: Task)

    @Delete
    fun remove(vararg app: HomeApp)

    @Delete
    fun remove(vararg note: Note)

    @Delete
    fun remove(vararg task: Task)

}