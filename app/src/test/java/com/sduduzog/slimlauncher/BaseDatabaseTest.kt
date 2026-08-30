package com.sduduzog.slimlauncher

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.os.Process
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.sduduzog.slimlauncher.data.BaseDao
import com.sduduzog.slimlauncher.data.BaseDatabase
import com.sduduzog.slimlauncher.models.HomeApp
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.Q])
class BaseDatabaseTest {

    private lateinit var context: Context
    private lateinit var db: BaseDatabase
    private lateinit var baseDao: BaseDao

    @Rule
    @JvmField
    val instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(
            context,
            BaseDatabase::class.java
        )
            .addMigrations(*BaseDatabase.ALL_MIGRATIONS)
            .allowMainThreadQueries()
            .build()
        baseDao = db.baseDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
        context.deleteDatabase(MIGRATION_DATABASE_NAME)
    }

    @Test
    fun writeAndReadHomeApp() {
        val app = HomeApp(
            "AppName",
            "package.name",
            "ActivityName",
            0,
            null, 12345
        )
        baseDao.add(app)
        assertThat(LiveDataTestUtil.getValue(baseDao.apps)).contains(app)
    }

    @Test
    fun migrateFromVersionOnePreservesHomeApps() {
        context.deleteDatabase(MIGRATION_DATABASE_NAME)
        VersionOneDatabase(context).use { database ->
            database.writableDatabase.insertOrThrow(
                "home_apps",
                null,
                homeAppValues("Calendar", "com.example.calendar", "CalendarActivity")
            )
            database.writableDatabase.insertOrThrow(
                "home_apps",
                null,
                homeAppValues("Camera", "com.example.camera", "CameraActivity")
            )
        }

        val migratedDatabase = Room.databaseBuilder(
            context,
            BaseDatabase::class.java,
            MIGRATION_DATABASE_NAME
        )
            .addMigrations(*BaseDatabase.ALL_MIGRATIONS)
            .allowMainThreadQueries()
            .build()
        try {
            val apps = checkNotNull(
                LiveDataTestUtil.getValue(migratedDatabase.baseDao().apps)
            ).associateBy(HomeApp::packageName)

            assertThat(apps.keys).containsExactly(
                "com.example.calendar",
                "com.example.camera"
            )
            val calendar = apps.getValue("com.example.calendar")
            assertThat(calendar.appName).isEqualTo("Calendar")
            assertThat(calendar.activityName).isEqualTo("CalendarActivity")
            assertThat(calendar.appNickname).isNull()
            assertThat(calendar.userSerial)
                .isEqualTo(Process.myUserHandle().hashCode().toLong())
            assertThat(apps.values.map(HomeApp::sortingIndex)).containsExactly(0, 1)
        } finally {
            migratedDatabase.close()
        }
    }

    private fun homeAppValues(
        appName: String,
        packageName: String,
        activityName: String
    ) = ContentValues().apply {
        put("app_name", appName)
        put("package_name", packageName)
        put("activity_name", activityName)
    }

    private class VersionOneDatabase(context: Context) : SQLiteOpenHelper(
        context,
        MIGRATION_DATABASE_NAME,
        null,
        1
    ) {
        override fun onCreate(database: SQLiteDatabase) {
            database.execSQL(
                """
                CREATE TABLE `apps` (
                    `app_name` TEXT NOT NULL,
                    `package_name` TEXT NOT NULL,
                    `activity_name` TEXT NOT NULL,
                    PRIMARY KEY(`package_name`)
                )
                """.trimIndent()
            )
            database.execSQL(
                """
                CREATE TABLE `home_apps` (
                    `app_name` TEXT NOT NULL,
                    `package_name` TEXT NOT NULL,
                    `activity_name` TEXT NOT NULL,
                    PRIMARY KEY(`package_name`)
                )
                """.trimIndent()
            )
        }

        override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) =
            Unit
    }

    companion object {
        private const val MIGRATION_DATABASE_NAME = "migration-test"
    }
}