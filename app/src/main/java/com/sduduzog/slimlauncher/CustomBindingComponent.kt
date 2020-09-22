package com.sduduzog.slimlauncher

import androidx.databinding.DataBindingComponent
import dagger.hilt.DefineComponent
import dagger.hilt.android.components.ApplicationComponent

@BindingScoped
@DefineComponent(parent = ApplicationComponent::class)
interface CustomBindingComponent : DataBindingComponent