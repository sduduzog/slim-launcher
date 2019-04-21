package com.sduduzog.slimlauncher.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "notes")
data class Note(
        @field:ColumnInfo(name = "id")
        @PrimaryKey
        var id: Long,
        @field:ColumnInfo(name = "body")
        var body: String,
        @field:ColumnInfo(name = "title")
        var title: String? = null,
        @field:ColumnInfo(name = "edited")
        var edited: Long = -1L,
        @field:ColumnInfo(name = "type")
        var type: Int = 0,
        @field:ColumnInfo(name = "path")
        var path: String? = null
) : Serializable {
    companion object {
        const val TYPE_TEXT = 0
        const val TYPE_VOICE = 2
    }
}