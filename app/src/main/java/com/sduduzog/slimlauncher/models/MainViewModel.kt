package com.sduduzog.slimlauncher.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sduduzog.slimlauncher.data.model.App

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _baseRepository = Repository(application)

    private var _apps: LiveData<List<HomeApp>>

    init {
        _apps = _baseRepository.apps
    }

    val apps: LiveData<List<HomeApp>>
        get() = _apps

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