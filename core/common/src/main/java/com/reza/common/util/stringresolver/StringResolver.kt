package com.reza.common.util.stringresolver

import androidx.annotation.StringRes

fun interface StringResolver {
    fun findString(@StringRes stringId: Int): String
}