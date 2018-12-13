package com.sduduzog.slimlauncher.data

import android.os.AsyncTask
import java.util.*

class PopulateDatabaseTask(private val dao: NoteDao) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg p0: Void?): Void? {
        val demoNote = Note("""
            Dear reader.

            This is a demo note. Also a short summary to help you get started in how to use create, edit
            or delete note items.

            1. Creating a note: Pretty easy actually. Just tap the button with the pen icon and type away.
            To save your note, press back and exit the editing screen. And done! the note is saved :)

            2. Editing a note: When you open a note, like this one, it is on reader mode, double tap on this text to enter editor mode, and make your changes.

            3. Deleting a note: I think you know this one. Go back, swipe this note away, and its gone.

            That's it! You're ready to go. Remember to give us feedback on the app and on changes you might wanna see in the future.
            And don't forget to give us 5 stars ;)

            ## TO COME ##
            - Tasks : Like a todo list or whatever
            - Voice recorder/ voice note : I'd like to integrate this nicely with text notes. Like taking text notes, record sound all in one note.
            - Insights : See which apps you use the most, see which apps you haven't used in a long time,
            probably to suggest clearing some, and some informative usage patterns, All while we're offline :)

            Remember! All you need is less.

            Thanks for the support
        """.trimIndent(), Date().time)
        dao.saveNote(demoNote)
        return null
    }

}