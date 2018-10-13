package com.sduduzog.slimlauncher.ui.main.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [App::class, HomeApp::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppRoomDatabase? {
            synchronized(AppRoomDatabase::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppRoomDatabase::class.java, "app_database")
                            .build()
                }
                return INSTANCE
            }
        }
    }
}
