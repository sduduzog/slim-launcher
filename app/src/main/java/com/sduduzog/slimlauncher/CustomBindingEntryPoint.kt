package com.sduduzog.slimlauncher

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn

@EntryPoint
@BindingScoped
@InstallIn(CustomBindingComponent::class)
interface CustomBindingEntryPoint {
    @BindingScoped
    fun getThemeBindingAdapter() : ThemeBindingAdapter
}