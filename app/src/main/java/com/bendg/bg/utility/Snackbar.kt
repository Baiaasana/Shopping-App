package com.bendg.bg.utility

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

//inline fun View.snack(@StringRes messageRes: Int, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
//    snack(resources.getString(messageRes), length, f)
//}
//
//inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
//    val snack = Snackbar.make(this, message, length)
//    snack.f()
//    snack.show()
//}
//
//@SuppressLint("ResourceType")
//fun Snackbar.action(@StringRes actionRes: Int, color: Int? = null, listener: (View) -> Unit) {
//    action(view.resources.getString(actionRes), color, listener)
//}
//
//fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
//    setAction(action, listener)
//    color?.let { setActionTextColor(color) }
//}

/**
 * Show a snackbar with [message], execute [f] and show it
 */
inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG) {
    val snack = Snackbar.make(this, message, length)
    snack.show()
}
