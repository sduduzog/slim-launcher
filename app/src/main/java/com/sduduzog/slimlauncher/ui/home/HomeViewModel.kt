package com.sduduzog.slimlauncher.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.sduduzog.slimlauncher.data.entity.HomeApp
import com.sduduzog.slimlauncher.data.repository.AppRepository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    private val threshold = 4

    val time: MutableLiveData<String> = MutableLiveData()
    val date: MutableLiveData<String> = MutableLiveData()
    val firstAdapterApps: MutableLiveData<List<HomeApp>> = MutableLiveData()
    val secondAdapterApps: MutableLiveData<List<HomeApp>> = MutableLiveData()
    private val homeAppsObserver = Observer<List<HomeApp>> {
        it.let { apps ->
            val first = apps.filter { app -> app.sortingIndex < threshold }
            val second = apps.filter { app -> app.sortingIndex >= threshold }
            firstAdapterApps.postValue(first)
            secondAdapterApps.postValue(second)
        }
    }

    init {
        repository.apps.observeForever(homeAppsObserver)
    }

    fun clockTick(twenty4Hour: Boolean) {
        val clockFormat = if (twenty4Hour) SimpleDateFormat("h:mm aa", Locale.ROOT)
        else SimpleDateFormat("H:mm", Locale.ROOT)
        val dateFormat = SimpleDateFormat("EEE MMM dd", Locale.ROOT)
        val dateObject = Date()
        time.postValue(clockFormat.format(dateObject))
        date.postValue(dateFormat.format(dateObject))
    }

    override fun onCleared() {
        repository.apps.removeObserver(homeAppsObserver)
    }
}