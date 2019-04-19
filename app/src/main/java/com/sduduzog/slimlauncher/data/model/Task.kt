package com.sduduzog.slimlauncher.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
        @field:ColumnInfo(name = "body")
        val body: String,
        @field:ColumnInfo(name = "is_complete")
        var isCompleted: Boolean,
        @field:ColumnInfo(name = "sorting_index")
        var sortingIndex: Int
) {
    @field:ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}