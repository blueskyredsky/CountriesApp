package com.reza.details.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val name: String,
    val emoji: String,
    val currency: String,
    val capital: String,
    val phone: String,
    val states: List<String>,
    val languages: List<String>
) : Parcelable {

    companion object {
        val LIST_OF_COUNTRIES = listOf(
            Country(
                name = "-",
                emoji = "-",
                currency = "-",
                capital = "-",
                phone = "-",
                states = emptyList(),
                languages = emptyList()
            )
        )

        val LIST_OF_COUNTRIES_WITH_REAL_VALUES = listOf(
            Country(
                name = "Iran",
                emoji = "-",
                currency = "-",
                capital = "Tehran",
                phone = "-",
                states = emptyList(),
                languages = emptyList()
            ),
            Country(
                name = "Iraq",
                emoji = "-",
                currency = "-",
                capital = "Baghdad",
                phone = "-",
                states = emptyList(),
                languages = emptyList()
            )
        )
    }
}
