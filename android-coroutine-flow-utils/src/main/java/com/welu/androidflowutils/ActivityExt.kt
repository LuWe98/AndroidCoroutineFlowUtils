package com.welu.androidflowutils

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Launches a coroutine in an [AppCompatActivity].
 * @param dispatcher The specified dispatcher -> Default: [Dispatchers.IO]
 * @param scope The specified CoroutineScope -> Defaults to the lifecycleScope of the Activity
 * @param startDelay The initial start delay, before the Coroutine is executed
 * @param block The code to execute
 */
inline fun AppCompatActivity.launch(
    dispatcher: CoroutineContext = Dispatchers.IO,
    scope: CoroutineScope = lifecycleScope,
    startDelay: Long = 0,
    crossinline block: suspend CoroutineScope.() -> Unit
) = scope.launch(dispatcher) {
    if(startDelay > 0){
        delay(startDelay)
    }
    block.invoke(this)
}

/**
 * Launches a coroutine in an [AppCompatActivity] when the specified [Lifecycle.State].
 * @param livecycleState Executes the code block on this livecycleState
 * @param dispatcher The specified dispatcher -> Default: [Dispatchers.IO]
 * @param scope The specified CoroutineScope -> Defaults to the lifecycleScope of the Activity
 * @param startDelay The initial start delay, before the Coroutine is executed
 * @param block The code to execute
 */
inline fun AppCompatActivity.launchWhen(
    livecycleState: Lifecycle.State,
    dispatcher: CoroutineContext = Dispatchers.IO,
    scope: LifecycleCoroutineScope = lifecycleScope,
    startDelay: Long = 0,
    crossinline block: suspend CoroutineScope.() -> Unit
) = scope.launch(context = dispatcher) {
    lifecycle.repeatOnLifecycle(livecycleState) {
        if(startDelay > 0){
            delay(startDelay)
        }
        block.invoke(this)
    }
}

/**
 * Launches a coroutine in an [AppCompatActivity] when the [Lifecycle.State.CREATED] is reached.
 * @param dispatcher The specified dispatcher -> Default: [Dispatchers.IO]
 * @param scope The specified CoroutineScope -> Defaults to the lifecycleScope of the Activity
 * @param startDelay The initial start delay, before the Coroutine is executed
 * @param block The code to execute
 */
inline fun AppCompatActivity.launchWhenCreated(
    dispatcher: CoroutineContext = Dispatchers.IO,
    scope: LifecycleCoroutineScope = lifecycleScope,
    startDelay: Long = 0,
    crossinline block: suspend CoroutineScope.() -> Unit
) = this.launchWhen(Lifecycle.State.CREATED, dispatcher, scope, startDelay, block)

/**
 * Launches a coroutine in an [AppCompatActivity] when the [Lifecycle.State.STARTED] is reached.
 * @param dispatcher The specified dispatcher -> Default: [Dispatchers.IO]
 * @param scope The specified CoroutineScope -> Defaults to the lifecycleScope of the Activity
 * @param startDelay The initial start delay, before the Coroutine is executed
 * @param block The code to execute
 */
inline fun AppCompatActivity.launchWhenStarted(
    dispatcher: CoroutineContext = Dispatchers.IO,
    scope: LifecycleCoroutineScope = lifecycleScope,
    startDelay: Long = 0,
    crossinline block: suspend CoroutineScope.() -> Unit
) = this.launchWhen(Lifecycle.State.STARTED, dispatcher, scope, startDelay, block)

/**
 * Launches a coroutine in an [AppCompatActivity] when the [Lifecycle.State.RESUMED] is reached.
 * @param dispatcher The specified dispatcher -> Default: [Dispatchers.IO]
 * @param scope The specified CoroutineScope -> Defaults to the lifecycleScope of the Activity
 * @param startDelay The initial start delay, before the Coroutine is executed
 * @param block The code to execute
 */
inline fun AppCompatActivity.launchWhenResumed(
    dispatcher: CoroutineContext = Dispatchers.IO,
    scope: LifecycleCoroutineScope = lifecycleScope,
    startDelay: Long = 0,
    crossinline block: suspend CoroutineScope.() -> Unit
) = this.launchWhen(Lifecycle.State.RESUMED, dispatcher, scope, startDelay, block)
