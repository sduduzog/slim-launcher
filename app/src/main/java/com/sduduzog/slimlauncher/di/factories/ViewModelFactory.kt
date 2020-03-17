package com.sduduzog.slimlauncher.di.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject
constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator = viewModels[modelClass]
                ?: viewModels.asIterable().firstOrNull{ modelClass.isAssignableFrom(it.key)}?.value
        return try {
            creator?.get() as T
        } catch (e: Exception){
            throw RuntimeException(e)
        }
    }
}