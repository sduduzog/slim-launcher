package com.sduduzog.slimlauncher.ui.main.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {App.class, HomeApp.class}, version = 1, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {
    private static volatile AppRoomDatabase INSTANCE;

    public static AppRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class, "app_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract AppDao appDao();
}
