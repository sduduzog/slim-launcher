package com.sduduzog.slimlauncher.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sduduzog.slimlauncher.data.BaseDao
import com.sduduzog.slimlauncher.data.model.App
import javax.inject.Inject

class MainViewModel @Inject constructor(baseDao: BaseDao) : ViewModel() {

    private val _baseRepository = Repository(baseDao)

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