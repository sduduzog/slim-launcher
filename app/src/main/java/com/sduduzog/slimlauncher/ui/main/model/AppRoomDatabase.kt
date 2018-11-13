package com.sduduzog.slimlauncher.ui.main.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [App::class, HomeApp::class], version = 2, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        @JvmStatic
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppRoomDatabase? {
            synchronized(AppRoomDatabase::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppRoomDatabase::class.java, "app_database")
                            .addMigrations(MIGRATION_1_2)
                            .build()
                }
                return INSTANCE
            }
        }

        private val MIGRATION_1_2 = object: Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE home_apps ADD COLUMN sorting_index INTEGER NOT NULL DEFAULT 0")
                val cursor = database.query("SELECT package_name FROM home_apps")
                cursor.moveToFirst()
                var index = 0
                while (!cursor.isAfterLast){
                    val column = cursor.getString(cursor.getColumnIndex("package_name"))
                    database.execSQL("UPDATE home_apps SET sorting_index=$index WHERE package_name='$column'")
                    cursor.moveToNext()
                    index++
                }
            }
        }
    }
}
