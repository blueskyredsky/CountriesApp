package com.reza.ui.util

import androidx.annotation.StringRes
import com.reza.common.util.stringresolver.StringResolver

class FakeStringResolver : StringResolver {
    private val stringMap = mutableMapOf<Int, String>()

    fun stubString(@StringRes stringId: Int, value: String) {
        stringMap[stringId] = value
    }

    override fun findString(@StringRes stringId: Int): String {
        return stringMap[stringId] ?: "DefaultStringForId:$stringId"
    }

    fun clearStubs() {
        stringMap.clear()
    }
}