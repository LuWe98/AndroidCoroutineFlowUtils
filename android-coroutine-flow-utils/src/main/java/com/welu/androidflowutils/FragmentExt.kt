package com.welu.androidflowutils

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Launches a coroutine in a [Fragment].
 * @param dispatcher The specified dispatcher -> Default: [Dispatchers.IO]
 * @param scope The specified CoroutineScope -> Defaults to the lifecycleScope of the viewLifecycleOwner
 * @param startDelay The initial start delay, before the Coroutine is executed
 * @param block The code to execute
 */
inline fun Fragment.launch(
    dispatcher: CoroutineContext = Dispatchers.IO,
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    startDelay: Long = 0,
    crossinline block: suspend CoroutineScope.() -> Unit
) = scope.launch(dispatcher) {
    delay(startDelay)
    block.invoke(this)
}

/**
 * Launches a coroutine in a [Fragment] when the specified [Lifecycle.State].
 * @param livecycleState Executes the code block on this livecycleState
 * @param dispatcher The specified dispatcher -> Default: [Dispatchers.IO]
 * @param scope The specified CoroutineScope -> Defaults to the lifecycleScope of the viewLifecycleOwner
 * @param startDelay The initial start delay, before the Coroutine is executed
 * @param block The code to execute
 */
inline fun Fragment.launchWhen(
    livecycleState: Lifecycle.State,
    dispatcher: CoroutineContext = Dispatchers.IO,
    scope: LifecycleCoroutineScope = viewLifecycleOwner.lifecycleScope,
    startDelay: Long = 0,
    crossinline block: suspend CoroutineScope.() -> Unit
) = scope.launch(context = dispatcher) {
    lifecycle.repeatOnLifecycle(livecycleState) {
        delay(startDelay)
        block.invoke(this)
    }
}

/**
 * Launches a coroutine in a [Fragment] when the [Lifecycle.State.CREATED] is reached.
 * @param dispatcher The specified dispatcher -> Default: [Dispatchers.IO]
 * @param scope The specified CoroutineScope -> Defaults to the lifecycleScope of the viewLifecycleOwner
 * @param startDelay The initial start delay, before the Coroutine is executed
 * @param block The code to execute
 */
inline fun Fragment.launchWhenCreated(
    dispatcher: CoroutineContext = Dispatchers.IO,
    scope: LifecycleCoroutineScope = viewLifecycleOwner.lifecycleScope,
    startDelay: Long = 0,
    crossinline block: suspend CoroutineScope.() -> Unit
) = this.launchWhen(Lifecycle.State.CREATED, dispatcher, scope, startDelay, block)

/**
 * Launches a coroutine in a [Fragment] when the specified [Lifecycle.State.STARTED] is reached.
 * @param dispatcher The specified dispatcher -> Default: [Dispatchers.IO]
 * @param scope The specified CoroutineScope -> Defaults to the lifecycleScope of the viewLifecycleOwner
 * @param startDelay The initial start delay, before the Coroutine is executed
 * @param block The code to execute
 */
inline fun Fragment.launchWhenStarted(
    dispatcher: CoroutineContext = Dispatchers.IO,
    scope: LifecycleCoroutineScope = viewLifecycleOwner.lifecycleScope,
    startDelay: Long = 0,
    crossinline block: suspend CoroutineScope.() -> Unit
) = this.launchWhen(Lifecycle.State.STARTED, dispatcher, scope, startDelay, block)

/**
 * Launches a coroutine in a [Fragment] when the specified [Lifecycle.State.RESUMED] is reached.
 * @param dispatcher The specified dispatcher -> Default: [Dispatchers.IO]
 * @param scope The specified CoroutineScope -> Defaults to the lifecycleScope of the viewLifecycleOwner
 * @param startDelay The initial start delay, before the Coroutine is executed
 * @param block The code to execute
 */
inline fun Fragment.launchWhenResumed(
    dispatcher: CoroutineContext = Dispatchers.IO,
    scope: LifecycleCoroutineScope = viewLifecycleOwner.lifecycleScope,
    startDelay: Long = 0,
    crossinline block: suspend CoroutineScope.() -> Unit
) = this.launchWhen(Lifecycle.State.RESUMED, dispatcher, scope, startDelay, block)




/**
 * Collects the Flow when the lifecycle of the provided LifecycleOwner reaches 'Lifecycle.State.STARTED'
 */
inline fun <reified T> Fragment.collectWhenStarted(
    flow: Flow<T>,
    lifecycleOwner: LifecycleOwner = viewLifecycleOwner,
    firstTimeDelay: Long = 0L,
    noinline collector: suspend (T) -> Unit
) = flow.collectWhen(lifecycleOwner, firstTimeDelay, Lifecycle.State.STARTED, collector)

/**
 * Collects the Flow when the lifecycle of the provided LifecycleOwner reaches 'Lifecycle.State.RESUMED'
 */
inline fun <reified T> Fragment.collectWhenResumed(
    flow: Flow<T>,
    lifecycleOwner: LifecycleOwner = viewLifecycleOwner,
    firstTimeDelay: Long = 0L,
    noinline collector: suspend (T) -> Unit
) = flow.collectWhen(lifecycleOwner, firstTimeDelay, Lifecycle.State.RESUMED, collector)

/**
 * Collects the Flow when the lifecycle of the provided LifecycleOwner reaches 'Lifecycle.State.CREATED'
 */
inline fun <reified T> Fragment.collectWhenCreated(
    flow: Flow<T>,
    lifecycleOwner: LifecycleOwner = viewLifecycleOwner,
    firstTimeDelay: Long = 0L,
    noinline collector: suspend (T) -> Unit
) = flow.collectWhen(lifecycleOwner, firstTimeDelay, Lifecycle.State.CREATED, collector)

/**
 * Collects the Flow when the lifecycle of the provided LifecycleOwner reaches the specified state
 */
inline fun <reified T> Fragment.collectWhen(
    flow: Flow<T>,
    lifecycleOwner: LifecycleOwner = viewLifecycleOwner,
    firstTimeDelay: Long = 0L,
    onLifeCycleState: Lifecycle.State,
    noinline collector: suspend (T) -> Unit
) = flow.collectWhen(lifecycleOwner, firstTimeDelay, onLifeCycleState, collector)