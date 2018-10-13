package com.sduduzog.slimlauncher.ui.main.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sduduzog.slimlauncher.ui.main.model.App
import com.sduduzog.slimlauncher.ui.main.model.AppRepository
import com.sduduzog.slimlauncher.ui.main.model.HomeApp

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

    fun addToHomeScreen(app: App) {
        val home = HomeApp(app.appName, app.packageName, app.activityName)
        _repository.insert(home)
    }
}
