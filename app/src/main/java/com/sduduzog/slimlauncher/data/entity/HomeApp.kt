package com.sduduzog.slimlauncher.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sduduzog.slimlauncher.data.model.App

@Entity(tableName = "home_apps")
data class HomeApp(
        @field:ColumnInfo(name = "app_name")
        var appName: String,
        @PrimaryKey
        @field:ColumnInfo(name = "package_name")
        var packageName: String,
        @field:ColumnInfo(name = "activity_name")
        var activityName: String,
        @field:ColumnInfo(name = "sorting_index")
        var sortingIndex: Int,
        @field:ColumnInfo(name = "app_nickname")
        var appNickname: String? = null
) {
    companion object {
        fun from(app: App, sortingIndex: Int = 0): HomeApp {
            return HomeApp(appName = app.appName, activityName = app.activityName, packageName = app.packageName, sortingIndex = sortingIndex)
        }
    }
}