package com.reza.feature.home.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Continent(
    val name: String?,
    val code: String?
) : Parcelable {
    companion object {
        val AFRICA = "Africa"
        val ASIA = "Asia"
        val ANTARCTICA = "Antarctica"
        val EUROPE = "Europe"
        val NORTH_AMERICA = "North America"
        val SOUTH_AMERICA = "South America"
        val OCEANIA = "Oceania"

        val LIST_OF_CONTINENTS = listOf(
            Continent(AFRICA, "AF"),
            Continent(ASIA, "AS")
        )
    }
}
