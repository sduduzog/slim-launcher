package com.sduduzog.slimlauncher.ui

import androidx.lifecycle.ViewModel
import com.sduduzog.slimlauncher.data.ConfigDao
import com.sduduzog.slimlauncher.data.repository.ConfigRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(configDao: ConfigDao) : ViewModel() {

    private val configRepository = ConfigRepository(configDao)


    fun tick() {}
}