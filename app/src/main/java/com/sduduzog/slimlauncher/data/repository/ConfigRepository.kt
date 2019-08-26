package com.sduduzog.slimlauncher.data.repository

import androidx.lifecycle.MutableLiveData
import com.sduduzog.slimlauncher.data.ConfigDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigRepository @Inject constructor(private val configDao: ConfigDao) {

    val currentTime: MutableLiveData<String> = MutableLiveData()
    val currentDate: MutableLiveData<String> = MutableLiveData()

    fun tick() {

    }
}