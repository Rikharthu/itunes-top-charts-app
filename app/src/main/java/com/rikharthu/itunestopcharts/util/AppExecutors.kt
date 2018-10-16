package com.rikharthu.itunestopcharts.util

import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

open class AppExecutors {

    val ioContext: CoroutineContext = DefaultDispatcher
    val uiContext: CoroutineContext = UI
}