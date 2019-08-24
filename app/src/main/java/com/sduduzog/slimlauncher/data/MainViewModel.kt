package com.sduduzog.slimlauncher.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sduduzog.slimlauncher.data.model.App
import com.sduduzog.slimlauncher.data.model.HomeApp
import com.sduduzog.slimlauncher.models.Repository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _baseRepository = Repository(application)

    private var _apps: LiveData<List<HomeApp>>

    init {
        _apps = _baseRepository.apps
    }

    val apps: LiveData<List<HomeApp>>
        get() = _apps

    val installedApps = MutableLiveData<List<App>>()

    fun add(app: App) {
        val index = _apps.value!!.size
        _baseRepository.add(HomeApp.from(app, index))
    }

    fun update(vararg args: HomeApp) {
        _baseRepository.update(*args)
    }

    fun remove(vararg app: HomeApp) {
        _baseRepository.remove(*app)
    }
}