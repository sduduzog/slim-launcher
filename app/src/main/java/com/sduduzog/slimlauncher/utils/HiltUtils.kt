package com.sduduzog.slimlauncher.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController

typealias HiltAssisted = androidx.hilt.Assisted

inline fun <reified T : ViewModel> Fragment.hiltNavGraphViewModels(@IdRes navGraphIdRes: Int) =
        viewModels<T>(
                ownerProducer = { findNavController().getBackStackEntry(navGraphIdRes) },
                factoryProducer = { defaultViewModelProviderFactory }
        )