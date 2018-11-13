package com.sduduzog.slimlauncher.ui.main.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

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

    fun updateApps() {
        _repository.updateApps()
    }

    fun addToHomeScreen(app: HomeApp) {
        _repository.insert(app)
    }

    fun addToHomeScreen(apps: List<App>) {
        for (i in apps.indices){
            val app = apps[i]
            val home = HomeApp(app.appName, app.packageName, app.activityName, i)
            _repository.insert(home)
        }
    }
}
