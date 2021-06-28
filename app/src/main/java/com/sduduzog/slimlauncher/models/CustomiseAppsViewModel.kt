package com.sduduzog.slimlauncher.models

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sduduzog.slimlauncher.data.BaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomiseAppsViewModel @ViewModelInject constructor(baseDao: BaseDao) : ViewModel() {

    private val repository = Repository(baseDao)
    private var _apps: LiveData<List<HomeApp>> = repository.apps

    val apps: LiveData<List<HomeApp>>
        get() = _apps

    fun update(vararg args: HomeApp) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(*args)
        }
    }

    fun reset(homeApp: HomeApp) {
        homeApp.appNickname = null
        update(homeApp)
    }
    fun remove(app: HomeApp) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.remove(app)
        }
    }

    fun clearTable(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearTable()
        }
    }
}