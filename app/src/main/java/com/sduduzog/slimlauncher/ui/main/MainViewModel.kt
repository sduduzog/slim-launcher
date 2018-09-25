package com.sduduzog.slimlauncher.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.sduduzog.slimlauncher.data.App
import com.sduduzog.slimlauncher.data.AppRepository
import com.sduduzog.slimlauncher.data.HomeApp

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var _repository: AppRepository = AppRepository(application)
    private var _homeApps: LiveData<List<HomeApp>>
    private var _apps: LiveData<List<App>>

    init {
        _homeApps = _repository.homeApps
        _apps = _repository.apps
    }

    val homeApps: LiveData<List<HomeApp>>
        get() = _homeApps

    val apps: LiveData<List<App>>
        get() = _apps

    fun deleteApp(app: HomeApp) {
        _repository.delete(app)
    }

    fun insert(app: App) {
        _repository.insert(app)
    }

    fun update(app: App) {
        _repository.update(app)
    }

    fun updateApps() {
        _repository.updateApps()
    }

    fun addToHomeScreen(app: App) {
        val home = HomeApp(app.appName, app.packageName, app.activityName)
        _repository.insertHomeApp(home)
    }
}
