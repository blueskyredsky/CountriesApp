package com.reza.countriesapp.domain.usecase.continents

import com.reza.countriesapp.R

class DefaultContinentImageUseCase : ContinentImageUseCase {
    override fun findContinentImage(name: String): Int {
        return when (name) {
            "Africa" -> R.drawable.ic_africa
            "Antarctica" -> R.drawable.ic_antarctica
            "Asia" -> R.drawable.ic_asia
            "Europe" -> R.drawable.ic_europe
            "North America" -> R.drawable.ic_north_america
            "Oceania" -> R.drawable.ic_australia
            "South America" -> R.drawable.ic_south_america
            // TODO: a default icon should be added here
            else -> R.drawable.ic_africa
        }
    }
}