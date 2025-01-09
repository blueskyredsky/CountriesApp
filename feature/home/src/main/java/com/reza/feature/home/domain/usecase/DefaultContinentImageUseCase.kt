package com.reza.feature.home.domain.usecase

import com.reza.feature.home.R
import com.reza.feature.home.domain.model.Continent
import javax.inject.Inject

internal class DefaultContinentImageUseCase @Inject constructor() : ContinentImageUseCase {

    override fun findContinentImage(name: String): Int {
        return when (name) {
            Continent.AFRICA -> R.drawable.ic_africa
            Continent.ANTARCTICA -> R.drawable.ic_antarctica
            Continent.ASIA -> R.drawable.ic_asia
            Continent.EUROPE -> R.drawable.ic_europe
            Continent.NORTH_AMERICA -> R.drawable.ic_north_america
            Continent.OCEANIA -> R.drawable.ic_australia
            Continent.SOUTH_AMERICA -> R.drawable.ic_south_america
            // TODO: a default icon should be replaced here
            else -> R.drawable.ic_africa
        }
    }
}