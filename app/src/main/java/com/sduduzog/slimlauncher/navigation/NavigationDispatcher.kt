package com.sduduzog.slimlauncher.navigation

import com.zhuinden.eventemitter.EventEmitter
import com.zhuinden.eventemitter.EventSource
import javax.inject.Inject

class NavigationDispatcher @Inject constructor() {
    private val navigationEmitter: EventEmitter<NavigationCommand> = EventEmitter()
    val navigationCommands: EventSource<NavigationCommand> = navigationEmitter;

    fun emit(navigationCommand: NavigationCommand) {
        navigationEmitter.emit(navigationCommand)
    }
}