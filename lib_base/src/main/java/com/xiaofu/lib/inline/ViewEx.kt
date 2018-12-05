package com.xiaofu.lib.inline

import android.view.View
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay

/**
 * 幽雅的过滤重复点击，默认300ms
 */
@UseExperimental(ObsoleteCoroutinesApi::class)
fun View.onClick(time: Long = 300L, action: suspend (View) -> Unit) {
    // launch one actor
    val eventActor = GlobalScope.actor<View>(Dispatchers.Main) {
        for (event in channel) {
            action(event)
            delay(time)
        }
    }
    // install a listener to activate this actor
    setOnClickListener {
        eventActor.offer(it)
    }
}
