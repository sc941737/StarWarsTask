package com.sc941737.lib.ui

import android.content.Context

fun Context.isRunningOnTablet(): Boolean {
    return resources.getBoolean(R.bool.isTablet)
}