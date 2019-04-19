package com.sduduzog.slimlauncher.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sduduzog.slimlauncher.data.model.HomeApp
import com.sduduzog.slimlauncher.data.model.Note
import com.sduduzog.slimlauncher.data.model.Task


@Database(entities = [HomeApp::class, Note::class, Task::class], version = 4, exportSchema = false)
abstract class BaseDatabase : RoomDatabase() {

    abstract fun baseDao(): BaseDao

    companion object {
        @Volatile
        @JvmStatic
        private var INSTANCE: BaseDatabase? = null

        fun getDatabase(context: Context): BaseDatabase? {
            synchronized(Database::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            BaseDatabase::class.java, "app_database")
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
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

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE IF EXISTS `apps`")
                database.execSQL("CREATE TABLE IF NOT EXISTS `tasks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `body` TEXT NOT NULL, `is_complete` INTEGER NOT NULL, `sorting_index` INTEGER NOT NULL)")
            }
        }
    }
}
