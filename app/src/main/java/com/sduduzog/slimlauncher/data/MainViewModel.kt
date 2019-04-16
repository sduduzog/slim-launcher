package com.sduduzog.slimlauncher.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _appRepository = AppRepository(application)

    private var _apps: LiveData<List<HomeApp>>

    init {
        _apps = _appRepository.apps
    }

    val apps: LiveData<List<HomeApp>>
        get() = _apps

    val installedApps = MutableLiveData<List<App>>()

    fun add(app: App){
        val index = _apps.value!!.size
        _appRepository.add(HomeApp.from(app, index))
    }

    fun update(vararg args: HomeApp){
        _appRepository.update(*args)
    }

    fun remove(app: HomeApp){
        _appRepository.remove(app)
    }
}