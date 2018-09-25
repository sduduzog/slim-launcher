package com.sduduzog.slimlauncher.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "apps")
class App{
        @field:ColumnInfo(name = "app_name")
        lateinit var appName: String
        @PrimaryKey
        @field:ColumnInfo(name = "package_name")
        lateinit var packageName: String
        @field:ColumnInfo(name = "activity_name")
        lateinit var activityName: String
}