package com.marvel.characters.library.views

import android.view.View

private const val CLICK_INTERVAL = 1000
private var lastClickTime = 0L

abstract class SingleClickListener : View.OnClickListener {
    abstract fun onSingleClick(v: View)

    override fun onClick(v: View) {
        if (!canDoClick()) return

        onSingleClick(v)
    }
}

private fun canDoClick(): Boolean {
    val currentTime = System.currentTimeMillis()
    val elapsedTime = currentTime - lastClickTime
    lastClickTime = currentTime

    return elapsedTime > CLICK_INTERVAL
}

inline fun View.onSingleClick(crossinline action: () -> Unit) {
    setOnClickListener(
        object : SingleClickListener() {
            override fun onSingleClick(v: View) {
                action.invoke()
            }
        }
    )
}