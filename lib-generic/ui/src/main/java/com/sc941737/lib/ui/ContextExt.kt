package com.sc941737.lib.ui

import android.content.Context

fun Context.isRunningOnTablet(): Boolean =
    resources.getBoolean(R.bool.isTablet)

fun Context.drawableId(name: String) =
    resources.getIdentifier(name, "drawable", packageName)