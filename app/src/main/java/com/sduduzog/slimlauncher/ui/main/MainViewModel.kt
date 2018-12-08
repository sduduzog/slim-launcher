package com.sduduzog.slimlauncher.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sduduzog.slimlauncher.data.App
import com.sduduzog.slimlauncher.data.DataRepository
import com.sduduzog.slimlauncher.data.HomeApp

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var _repository: DataRepository = DataRepository.getInstance(application)
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
        _repository.deleteHomeApp(app)
    }

    fun updateApps(list: List<HomeApp>) {
        _repository.updateApps(list)
    }

    fun refreshApps() {
        _repository.refreshApps()
    }

    fun addToHomeScreen(app: HomeApp) {
        _repository.insertHomeApp(app)
    }
    fun renameApp(app: HomeApp){
        _repository.renameApp(app)
    }

    fun addToHomeScreen(apps: List<App>) {
        for (i in apps.indices){
            val app = apps[i]
            val home = HomeApp(app.appName, app.packageName, app.activityName, i)
            _repository.insertHomeApp(home)
        }
    }
}
