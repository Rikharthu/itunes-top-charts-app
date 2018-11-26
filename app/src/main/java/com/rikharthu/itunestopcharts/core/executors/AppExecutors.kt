package com.rikharthu.itunestopcharts.core.executors

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

val ioContext: CoroutineContext = Dispatchers.IO
val uiContext: CoroutineContext = Dispatchers.Main
val computationContext: CoroutineContext = Dispatchers.Default