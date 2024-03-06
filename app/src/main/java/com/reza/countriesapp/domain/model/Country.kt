package com.reza.countriesapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val name: String?,
    val emoji: String?,
    val currency: String?,
    val capital: String?,
    val phone: String?,
    val states: List<String?>?,
    val languages: List<String?>?
) : Parcelable {
    companion object {
        val LIST_OF_COUNTRIES = listOf<Country>(
            Country(
                name = "",
                emoji = "",
                currency = "",
                capital = "",
                phone = "",
                states = emptyList(),
                languages = emptyList()
            )
        )
    }
}
