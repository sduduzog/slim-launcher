package com.sduduzog.slimlauncher.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sduduzog.slimlauncher.data.model.App
import com.sduduzog.slimlauncher.data.model.HomeApp
import com.sduduzog.slimlauncher.data.model.Note

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _appRepository = AppRepository(application)
    private val _noteRepository  = NoteRepository(application)

    private var _apps: LiveData<List<HomeApp>>
    private var _notes: LiveData<List<Note>>

    init {
        _apps = _appRepository.apps
        _notes = _noteRepository.notes
    }

    val apps: LiveData<List<HomeApp>>
        get() = _apps

    val installedApps = MutableLiveData<List<App>>()

    fun add(app: App){
        val index = _apps.value!!.size
        _appRepository.add(HomeApp.from(app, index))
    }

    fun update(vararg args: HomeApp){
        _appRepository.update(*args)
    }

    fun remove(app: HomeApp){
        _appRepository.remove(app)
    }
}