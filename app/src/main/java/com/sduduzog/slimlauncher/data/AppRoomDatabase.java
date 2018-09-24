package com.sduduzog.slimlauncher.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {App.class}, version = 1)
public abstract class AppRoomDatabase extends RoomDatabase {
    private static volatile AppRoomDatabase INSTANCE;
    static AppRoomDatabase getDatabase(final Context context) {
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
