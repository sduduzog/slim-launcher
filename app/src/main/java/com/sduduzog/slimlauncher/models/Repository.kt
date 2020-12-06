package com.sduduzog.slimlauncher.models

import androidx.lifecycle.LiveData
import com.sduduzog.slimlauncher.data.BaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class Repository(private val baseDao: BaseDao): CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    private val _apps = baseDao.apps

    val apps: LiveData<List<HomeApp>>
        get() = _apps

    fun add(app: HomeApp) {
        CoroutineScope(coroutineContext).launch {
            baseDao.add(app)
        }
    }

    fun update(vararg list : HomeApp) {
        CoroutineScope(coroutineContext).launch {
            baseDao.update(*list)
        }
    }

    fun remove(app: HomeApp) {
        CoroutineScope(coroutineContext).launch {
            baseDao.remove(app)
        }
    }

    fun clearTable(){
        CoroutineScope(coroutineContext).launch {
            baseDao.clearTable()
        }
    }
}
