package com.welu.androidflowutils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

/**
 * Collects the Flow when the lifecycle of the provided LifecycleOwner reaches 'Lifecycle.State.STARTED'
 */
inline fun <reified T> Flow<T>.collectWhenStarted(
    lifecycleOwner: LifecycleOwner,
    firstTimeDelay: Long = 0L,
    noinline collector: suspend (T) -> Unit
) = collectWhen(lifecycleOwner, firstTimeDelay, Lifecycle.State.STARTED, collector)

/**
 * Collects the Flow when the lifecycle of the provided LifecycleOwner reaches 'Lifecycle.State.RESUMED'
 */
inline fun <reified T> Flow<T>.collectWhenResumed(
    lifecycleOwner: LifecycleOwner,
    firstTimeDelay: Long = 0L,
    noinline collector: suspend (T) -> Unit
) = collectWhen(lifecycleOwner, firstTimeDelay, Lifecycle.State.RESUMED, collector)

/**
 * Collects the Flow when the lifecycle of the provided LifecycleOwner reaches 'Lifecycle.State.CREATED'
 */
inline fun <reified T> Flow<T>.collectWhenCreated(
    lifecycleOwner: LifecycleOwner,
    firstTimeDelay: Long = 0L,
    noinline collector: suspend (T) -> Unit
) = collectWhen(lifecycleOwner, firstTimeDelay, Lifecycle.State.CREATED, collector)

/**
 * Collects the Flow when the lifecycle of the provided LifecycleOwner reaches the specified state
 */
inline fun <reified T> Flow<T>.collectWhen(
    lifecycleOwner: LifecycleOwner,
    firstTimeDelay: Long = 0L,
    onLifeCycleState: Lifecycle.State,
    noinline collector: suspend (T) -> Unit
): Job {
    val block: suspend CoroutineScope.() -> Unit = {
        delay(firstTimeDelay)
        lifecycleOwner.lifecycle.repeatOnLifecycle(onLifeCycleState) {
            collect {
                collector(it)
            }
        }
    }
    return when(onLifeCycleState) {
        Lifecycle.State.CREATED, Lifecycle.State.STARTED, Lifecycle.State.RESUMED -> lifecycleOwner.lifecycleScope.launchWhenCreated(block)
        else -> throw IllegalArgumentException("Illegal Lifecycle State!")
    }
}