package com.sduduzog.slimlauncher.utils

import com.sduduzog.slimlauncher.data.model.Note

interface OnShitDoneToNotesListener{
    fun onView(note: Note)
    fun onDelete(note: Note)
}