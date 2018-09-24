package com.sduduzog.slimlauncher.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.sduduzog.slimlauncher.data.App
import com.sduduzog.slimlauncher.data.AppRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var _repository: AppRepository = AppRepository(application)
    private var _apps: LiveData<List<App>>

    init {
        _apps = _repository.allApps
    }

    val apps: LiveData<List<App>>
        get() = _apps

    fun insert(app: App) {
        _repository.insert(app)
    }

    fun deleteApp(packageName: String) {
        _repository.delete(packageName)
    }
}
