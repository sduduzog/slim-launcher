package com.sduduzog.slimlauncher.ui.main.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apps")
data class App(
        @field:ColumnInfo(name = "app_name")
        var appName: String,
        @PrimaryKey
        @field:ColumnInfo(name = "package_name")
        var packageName: String,
        @field:ColumnInfo(name = "activity_name")
        var activityName: String
)