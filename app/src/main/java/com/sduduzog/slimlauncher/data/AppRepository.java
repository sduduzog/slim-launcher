package com.sduduzog.slimlauncher.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class AppRepository {
    private AppDao appDao;
    private LiveData<List<App>> apps;

    public AppRepository(Application application){
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        appDao = db.appDao();
        apps = appDao.getAllApps();
    }

    public LiveData<List<App>> getAllApps() {
        return apps;
    }

    public void insert(App app){
        new insertAsyncTask(appDao).execute(app);
    }

    public void delete(String packageName) {
        new deleteAsyncTask(appDao).execute(packageName);
    }

    private static class insertAsyncTask extends AsyncTask<App, Void, Void> {

        private AppDao mAsyncTaskDao;

        insertAsyncTask(AppDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final App... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<String, Void, Void> {

        private AppDao mAsyncTaskDao;

        deleteAsyncTask(AppDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
