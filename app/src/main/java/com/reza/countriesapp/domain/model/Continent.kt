package com.reza.countriesapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Continent(
    val name: String?,
    val code: String?,
) : Parcelable {
    companion object {
        val DEFAULT_CONTINENT = Continent(name = "default_name", code = "default_code")
    }
}
