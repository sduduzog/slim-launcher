package com.sduduzog.slimlauncher.ui.options

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sduduzog.slimlauncher.data.repository.ConfigRepository
import javax.inject.Inject

class OptionsViewModel @Inject constructor(private val configRepository: ConfigRepository) : ViewModel() {

    val timeFormat: LiveData<String> = configRepository.timeFormatLiveData
    fun setTimeFormat(format: String): Unit = configRepository.setTimeFormat(format)
}