package com.welu.androidflowutils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Launches a coroutine in an [ViewModel].
 * @param dispatcher The specified dispatcher -> Default: [Dispatchers.IO]
 * @param scope The specified CoroutineScope -> Defaults to the calling viewModelScope
 * @param startDelay The initial start delay, before the Coroutine is executed
 * @param block The code to execute
 */
inline fun ViewModel.launch(
    dispatcher: CoroutineContext = Dispatchers.IO,
    scope: CoroutineScope = viewModelScope,
    startDelay: Long = 0,
    crossinline block: suspend CoroutineScope.() -> Unit
) = scope.launch(dispatcher) {
    delay(startDelay)
    block.invoke(this)
}