package com.sduduzog.slimlauncher.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sduduzog.slimlauncher.models.HomeApp


@Database(entities = [HomeApp::class], version = 7, exportSchema = false)
abstract class BaseDatabase : RoomDatabase() {

    abstract fun baseDao(): BaseDao

    companion object {

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE `home_apps` ADD COLUMN `sorting_index` INTEGER NOT NULL DEFAULT 0")
                val cursor = database.query("SELECT package_name FROM home_apps")
                cursor.moveToFirst()
                var index = 0
                while (!cursor.isAfterLast) {
                    val column = cursor.getString(cursor.getColumnIndex("package_name"))
                    database.execSQL("UPDATE `home_apps` SET `sorting_index`=$index WHERE `package_name`='$column'")
                    cursor.moveToNext()
                    index++
                }
            }
        }

        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `notes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `title` TEXT, `body` TEXT NOT NULL, `edited` INTEGER NOT NULL)")
            }
        }

        val MIGRATION_3_4: Migration = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE IF EXISTS `apps`")
                database.execSQL("CREATE TABLE IF NOT EXISTS `tasks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `body` TEXT NOT NULL, `is_complete` INTEGER NOT NULL, `sorting_index` INTEGER NOT NULL)")
            }
        }

        val MIGRATION_4_5: Migration = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE `notes` RENAME TO `notes_old`")
                database.execSQL("CREATE TABLE IF NOT EXISTS `notes` (`id` INTEGER PRIMARY KEY NOT NULL, `body` TEXT NOT NULL, `title` TEXT, `edited` INTEGER NOT NULL)")
                database.execSQL("INSERT INTO `notes` (`id`, `body`, `edited`, `title`) SELECT `id`, `body`, `edited`, `title` FROM `notes_old`")
                database.execSQL("ALTER TABLE `notes` ADD COLUMN `type` INTEGER NOT NULL DEFAULT 0")
                database.execSQL("ALTER TABLE `notes` ADD COLUMN `filename` TEXT")
            }
        }
        val MIGRATION_5_6: Migration = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE IF EXISTS `notes`")
                database.execSQL("DROP TABLE IF EXISTS `tasks`")
            }
        }
        val MIGRATION_6_7: Migration = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE `home_apps` ADD COLUMN `app_nickname` TEXT")
            }
        }
    }
}
