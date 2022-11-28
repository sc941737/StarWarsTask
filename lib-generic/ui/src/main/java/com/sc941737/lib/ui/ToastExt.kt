package com.sc941737.lib.ui

import android.content.Context
import android.widget.Toast

// Average reading speed is around 240 wpm, so 4 wps.
// Short Toast/Snackbar duration is 2 seconds, hence 8.
const val SHORT_DISPLAY_WORD_LIMIT = 8

fun Context.showToast(message: String) {
    val displayLength =
        if (message.wordCount > SHORT_DISPLAY_WORD_LIMIT) Toast.LENGTH_LONG
        else Toast.LENGTH_SHORT
    Toast.makeText(this, message, displayLength).show()
}