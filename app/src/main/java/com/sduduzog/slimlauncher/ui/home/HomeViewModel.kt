package com.sduduzog.slimlauncher.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.sduduzog.slimlauncher.AppConstants
import com.sduduzog.slimlauncher.data.entity.HomeApp
import com.sduduzog.slimlauncher.data.repository.AppRepository
import com.sduduzog.slimlauncher.data.repository.ConfigRepository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val appRepository: AppRepository,
                                        private val configRepository: ConfigRepository)
    : ViewModel() {

    private val threshold = 4
    private var timeFormatString: String? = null
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

    private val timeFormatObserver = Observer<String> {
        this.timeFormatString = (it ?: return@Observer)
        configRepository.tick()
    }

    private val currentDateObserver = Observer<Date> {
        it.let { date ->
            val clockFormat = SimpleDateFormat(timeFormatString
                    ?: AppConstants.DEFAULT_TIME_FORMAT, Locale.getDefault())
            time.postValue(clockFormat.format(date))
        }
    }

    init {
        appRepository.apps.observeForever(homeAppsObserver)
        configRepository.timeFormatLiveData.observeForever(timeFormatObserver)
        configRepository.currentDate.observeForever(currentDateObserver)
    }

    override fun onCleared() {
        appRepository.apps.removeObserver(homeAppsObserver)
        configRepository.timeFormatLiveData.removeObserver(timeFormatObserver)
        configRepository.currentDate.removeObserver(currentDateObserver)
    }
}