package com.sduduzog.slimlauncher.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [App::class, HomeApp::class, Note::class], version = 3, exportSchema = false)
abstract class DataRoomDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        @JvmStatic
        private var INSTANCE: DataRoomDatabase? = null

        fun getDatabase(context: Context): DataRoomDatabase? {
            synchronized(DataRoomDatabase::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            DataRoomDatabase::class.java, "app_database")
                            .addCallback(object : Callback(){
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    val database = DataRoomDatabase.getDatabase(context)!!
                                    val dao = database.noteDao()
                                    PopulateDatabaseTask(dao).execute()
                                }
                            })
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .build()
                }
                return INSTANCE
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE home_apps ADD COLUMN sorting_index INTEGER NOT NULL DEFAULT 0")
                val cursor = database.query("SELECT package_name FROM home_apps")
                cursor.moveToFirst()
                var index = 0
                while (!cursor.isAfterLast) {
                    val column = cursor.getString(cursor.getColumnIndex("package_name"))
                    database.execSQL("UPDATE home_apps SET sorting_index=$index WHERE package_name='$column'")
                    cursor.moveToNext()
                    index++
                }
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `notes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `title` TEXT, `body` TEXT NOT NULL, `edited` INTEGER NOT NULL)")
            }
        }
    }
}
