package com.sduduzog.slimlauncher.ui

import androidx.lifecycle.ViewModel
import com.sduduzog.slimlauncher.data.repository.ConfigRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(private val configRepository: ConfigRepository) : ViewModel() {
    fun tick(): Unit = configRepository.tick()
}