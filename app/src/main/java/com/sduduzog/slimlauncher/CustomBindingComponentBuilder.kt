package com.sduduzog.slimlauncher

import dagger.hilt.DefineComponent

@DefineComponent.Builder
interface CustomBindingComponentBuilder {
    fun build(): CustomBindingComponent
}