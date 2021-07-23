package com.sduduzog.slimlauncher.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sduduzog.slimlauncher.data.BaseDao
import com.sduduzog.slimlauncher.data.model.App
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(_baseRepository: Repository) : ViewModel() {


    private var _apps: LiveData<List<HomeApp>> = _baseRepository.apps

    val apps: LiveData<List<HomeApp>>
        get() = _apps
}