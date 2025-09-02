package com.reza.feature.continents.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Continent(
    val name: String,
    val code: String
) : Parcelable {

    companion object {
        const val AFRICA = "Africa"
        const val AFRICA_CODE = "AF"
        const val ANTARCTICA = "Antarctica"
        const val ANTARCTICA_CODE = "AN"
        const val ASIA = "Asia"
        const val ASIA_CODE = "AS"
        const val EUROPE = "Europe"
        const val EUROPE_CODE = "EU"
        const val NORTH_AMERICA = "North America"
        const val NORTH_AMERICA_CODE = "NA"
        const val SOUTH_AMERICA = "South America"
        const val SOUTH_AMERICA_CODE = "SA"
        const val OCEANIA = "Oceania"
        const val OCEANIA_CODE = "OC"

        val LIST_OF_CONTINENTS = listOf(
            Continent(AFRICA, AFRICA_CODE),
            Continent(ANTARCTICA, ANTARCTICA_CODE),
        )
    }
}
