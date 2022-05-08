package com.welu.androidflowutils

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

class ViewCoroutineScope: CoroutineScope, View.OnAttachStateChangeListener {

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main

    override fun onViewAttachedToWindow(p0: View?) = Unit

    override fun onViewDetachedFromWindow(p0: View?) {
        coroutineContext.cancel()
        p0?.setTag(R.string.viewCoroutineScope, null)
    }

}