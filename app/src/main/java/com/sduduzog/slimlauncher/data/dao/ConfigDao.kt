package com.sduduzog.slimlauncher.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sduduzog.slimlauncher.AppConstants
import com.sduduzog.slimlauncher.data.entity.Config

@Dao
interface ConfigDao {

    @get:Query("SELECT `value` FROM `app_config` WHERE `key`='${AppConstants.TIME_FORMAT_KEY}'")
    val timeFormat: LiveData<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(vararg config: Config)
}