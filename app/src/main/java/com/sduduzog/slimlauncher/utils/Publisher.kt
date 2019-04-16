package com.sduduzog.slimlauncher.utils

interface Publisher {
    fun attatchSubscriber(s: Subscriber)
    fun detachSubscriber(s: Subscriber)
    fun dispatchBack()
}