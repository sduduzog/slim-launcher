package com.sduduzog.slimlauncher.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_config")
data class Config(
        @PrimaryKey
        @field:ColumnInfo(name = "key")
        val key: String,
        @field:ColumnInfo(name = "value")
        val value: String
)
