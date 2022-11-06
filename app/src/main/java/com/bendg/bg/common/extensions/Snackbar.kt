package com.bendg.bg.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, message, length)
    snack.show()
}

