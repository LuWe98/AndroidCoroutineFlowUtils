package com.welu.androidflowutils

import android.view.View
import kotlinx.coroutines.*

val View.viewScope: CoroutineScope
    get() {
        (getTag(R.string.viewCoroutineScopeTagKey) as? CoroutineScope)?.let { storedScope ->
            return storedScope
        }
        return ViewCoroutineScope().also { newScope ->
            if (!isAttachedToWindow) {
                newScope.cancel()
            } else {
                addOnAttachStateChangeListener(newScope)
                setTag(R.string.viewCoroutineScopeTagKey, newScope)
            }
        }
    }

inline fun View.launch(
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    scope: CoroutineScope = viewScope,
    startDelay: Long = 0,
    crossinline block: suspend CoroutineScope.() -> Unit
) = scope.launch(context = dispatcher) {
    if (startDelay > 0) {
        delay(startDelay)
    }
    block.invoke(this)
}