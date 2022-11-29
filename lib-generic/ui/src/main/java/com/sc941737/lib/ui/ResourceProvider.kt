package com.sc941737.lib.ui

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

interface ResourceProvider {
    fun getString(@StringRes stringId: Int): String
    fun getString(@StringRes stringId: Int, vararg args: Any): String
    @ColorInt
    fun getColor(@ColorRes colorId: Int): Int
}

class ResourceProviderImpl(
    private val context: Context,
) : ResourceProvider {

    override fun getString(stringId: Int) = context.getString(stringId)

    override fun getString(@StringRes stringId: Int, vararg args: Any)
        = context.getString(stringId, *args)

    override fun getColor(colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }
}