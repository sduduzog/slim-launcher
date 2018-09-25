package com.sduduzog.slimlauncher.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.sduduzog.slimlauncher.data.App
import com.sduduzog.slimlauncher.data.AppRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var _repository: AppRepository = AppRepository(application)
    private var _apps: LiveData<List<App>>
    private var _homeApps: LiveData<List<App>>
    private var _availableApps: LiveData<List<App>>

    init {
        _apps = _repository.allApps
        _homeApps = _repository.homeApps
        _availableApps = _repository.availableApps
    }

    val apps: LiveData<List<App>>
        get() = _apps

    val homeApps: LiveData<List<App>>
        get() = _homeApps

    val availableApps: LiveData<List<App>>
        get() = _availableApps

    fun insert(app: App) {
        _repository.insert(app)
    }

    fun update(app: App) {
        _repository.update(app)
    }

    fun deleteApp(packageName: String) {
        _repository.delete(packageName)
    }

    fun bulkInsert(apps: MutableList<App>) {
        _repository.bulkInsert(apps)
    }
}
