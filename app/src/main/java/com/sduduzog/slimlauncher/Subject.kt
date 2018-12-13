package com.sduduzog.slimlauncher

abstract class Subject {
    abstract fun attachObserver(o: Observer)
    abstract fun detachObserver(o: Observer)
    abstract fun notifyObservers()
}