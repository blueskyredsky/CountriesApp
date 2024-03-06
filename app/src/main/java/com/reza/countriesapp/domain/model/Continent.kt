package com.reza.countriesapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Continent(
    val name: String?,
    val code: String?,
) : Parcelable {
    companion object {
        val LIST_OF_CONTINENTS = listOf(Continent("Africa", "AF"), Continent("Asia", "AS"))
    }
}
