package com.bendg.bg.utility.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Context.showKeyboardFor(view: View) {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Activity.hideKeyboard() {
    val view: View = this.currentFocus!!
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}