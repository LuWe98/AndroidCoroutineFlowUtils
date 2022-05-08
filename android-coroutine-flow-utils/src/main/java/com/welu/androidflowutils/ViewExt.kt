package com.welu.androidflowutils

import android.view.View
import kotlinx.coroutines.*

val View.viewScope: CoroutineScope
    get() {
        val storedScope = getTag(R.string.viewCoroutineScope) as? CoroutineScope
        if (storedScope != null) return storedScope

        return ViewCoroutineScope().apply {
            if (!isAttachedToWindow) {
                cancel()
            } else {
                addOnAttachStateChangeListener(this)
                setTag(R.string.viewCoroutineScope, this)
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