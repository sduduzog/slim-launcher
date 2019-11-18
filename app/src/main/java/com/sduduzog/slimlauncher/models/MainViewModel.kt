package com.sduduzog.slimlauncher.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sduduzog.slimlauncher.data.model.App
import javax.inject.Inject

class MainViewModel @Inject constructor(private val _baseRepository: Repository) : ViewModel() {

    private var _apps: LiveData<List<HomeApp>> = _baseRepository.apps

    val apps: LiveData<List<HomeApp>>
        get() = _apps

    fun add(app: App) {
        val index = (_apps.value ?: return).size
        _baseRepository.add(HomeApp.from(app, index))
    }
}