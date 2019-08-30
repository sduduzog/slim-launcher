package com.sduduzog.slimlauncher.data.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sduduzog.slimlauncher.AppConstants
import com.sduduzog.slimlauncher.data.dao.ConfigDao
import com.sduduzog.slimlauncher.data.entity.Config
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigRepository @Inject constructor(private val configDao: ConfigDao) {

    val timeFormatLiveData: LiveData<String>
        get() = configDao.timeFormat

    val currentDate: MutableLiveData<Date> = MutableLiveData()

    fun tick() {
        currentDate.postValue(Date())
    }

    fun setTimeFormat(format: String) {
        val config = Config(AppConstants.TIME_FORMAT_KEY, format)
        UpdateTimeFormatAsyncTask(configDao).execute(config)
    }

    private class UpdateTimeFormatAsyncTask(private val mAsyncTaskDao: ConfigDao) : AsyncTask<Config, Void, Void>() {

        override fun doInBackground(vararg params: Config): Void? {
            mAsyncTaskDao.insertOrUpdate(*params)
            return null
        }
    }
}