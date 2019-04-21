package com.sduduzog.slimlauncher.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "notes")
data class Note(
        @field:ColumnInfo(name = "body")
        var body: String,
        @field:ColumnInfo(name = "edited")
        var edited: Long = -1L,
        @field:ColumnInfo(name = "is_voice")
        var is_voice: Boolean = false,
        @field:ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,
        @field:ColumnInfo(name = "title")
        var title: String? = null,
        @field:ColumnInfo(name = "path")
        var path: String? = null
) : Serializable