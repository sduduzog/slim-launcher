package com.sduduzog.slimlauncher.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "apps")
data class App(@field:ColumnInfo(name = "label")
          var label: String, @field:ColumnInfo(name = "activity_name")
          var activityName: String, @field:PrimaryKey @field:ColumnInfo(name = "package_name")
          var packageName: String, @field:ColumnInfo(name = "home") var home: Boolean = false)
