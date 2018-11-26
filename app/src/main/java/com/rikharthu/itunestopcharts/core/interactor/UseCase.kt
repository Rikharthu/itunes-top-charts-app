package com.rikharthu.itunestopcharts.core.interactor

import com.rikharthu.itunestopcharts.core.exception.Failure
import com.rikharthu.itunestopcharts.core.executors.computationContext
import com.rikharthu.itunestopcharts.core.executors.uiContext
import com.rikharthu.itunestopcharts.core.functional.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This abstraction represents an execution unit for different use cases (this means than any use
 * case in the application should implement this contract).
 *
 * By convention each [UseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.
 */
// TODO also pass CoroutineScope
// https://codelabs.developers.google.com/codelabs/kotlin-coroutines/#4
abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(scope: CoroutineScope, params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        // Execute job in a background
        val job = scope.async(computationContext) { run(params) }
        // And post the result in the UI thread
        scope.launch(uiContext) { onResult(job.await()) }
    }

    /**
     * Represents no parameters
     */
    class None
}