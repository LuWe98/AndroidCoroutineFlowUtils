package com.welu.androidflowutils

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

val View.viewScope: CoroutineScope get() {
    val storedScope = getTag(R.string.viewCoroutineScope) as? CoroutineScope
    if(storedScope != null) return storedScope

    val newScope = ViewCoroutineScope()
    if(!isAttachedToWindow){
        newScope.cancel()
    } else {
        addOnAttachStateChangeListener(newScope)
        setTag(R.string.viewCoroutineScope, newScope)
    }
    return newScope
}