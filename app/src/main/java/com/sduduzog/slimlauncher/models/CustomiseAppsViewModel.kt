package com.sduduzog.slimlauncher.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sduduzog.slimlauncher.data.BaseDao
import javax.inject.Inject

class CustomiseAppsViewModel @Inject constructor(baseDao: BaseDao) : ViewModel() {

    private val repository = Repository(baseDao)
    private var _apps: LiveData<List<HomeApp>>

    init {
        _apps = repository.apps
    }

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