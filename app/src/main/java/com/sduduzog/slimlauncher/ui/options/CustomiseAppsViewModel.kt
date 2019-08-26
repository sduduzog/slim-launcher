package com.sduduzog.slimlauncher.ui.options

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sduduzog.slimlauncher.data.entity.HomeApp
import com.sduduzog.slimlauncher.data.repository.AppRepository
import javax.inject.Inject

class CustomiseAppsViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    private var _apps: LiveData<List<HomeApp>> = repository.apps

    val apps: LiveData<List<HomeApp>>
        get() = _apps

    fun update(vararg args: HomeApp) {
        repository.update(*args)
    }

    fun reset(homeApp: HomeApp) {
        homeApp.appNickname = null
        update(homeApp)
    }

    fun remove(vararg app: HomeApp) {
        repository.remove(*app)
    }
}