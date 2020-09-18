package com.sduduzog.slimlauncher.ui.setup

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.sduduzog.slimlauncher.navigation.NavigationDispatcher

class SetupViewModel @ViewModelInject constructor(
        private val navigationDispatcher: NavigationDispatcher
): ViewModel()