package com.sduduzog.slimlauncher.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sduduzog.slimlauncher.data.model.App
import com.sduduzog.slimlauncher.data.model.HomeApp
import com.sduduzog.slimlauncher.data.model.Note
import com.sduduzog.slimlauncher.data.model.Task

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _baseRepository = Repository(application)

    private var _apps: LiveData<List<HomeApp>>
    private var _notes: LiveData<List<Note>>
    private var _tasks: LiveData<List<Task>>

    init {
        _apps = _baseRepository.apps
        _notes = _baseRepository.notes
        _tasks = _baseRepository.tasks
    }

    val apps: LiveData<List<HomeApp>>
        get() = _apps

    val installedApps = MutableLiveData<List<App>>()

    val notes: LiveData<List<Note>>
        get() = _notes

    val tasks: LiveData<List<Task>>
        get() = _tasks

    fun add(app: App) {
        val index = _apps.value!!.size
        _baseRepository.add(HomeApp.from(app, index))
    }

    fun add(note: Note) {
        _baseRepository.add(note)
    }

    fun add(task: Task) {
        _baseRepository.add(task)
    }

    fun update(vararg args: HomeApp) {
        _baseRepository.update(*args)
    }

    fun update(vararg args: Note) {
        _baseRepository.update(*args)
    }

    fun update(vararg args: Task) {
        _baseRepository.update(*args)
    }

    fun remove(vararg app: HomeApp) {
        _baseRepository.remove(*app)
    }

    fun remove(note: Note) {
        _baseRepository.remove(note)
    }

    fun remove(vararg args: Task) {
        _baseRepository.remove(*args)
    }
}