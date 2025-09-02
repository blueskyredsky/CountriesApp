package com.reza.feature.continents.domain.usecase

import com.reza.feature.continents.R
import com.reza.feature.continents.domain.model.Continent
import javax.inject.Inject

class FakeContinentImageUseCase @Inject constructor() : ContinentImageUseCase {

    private val continentImageMap = mutableMapOf<String, Int>()
    private var defaultImageResId: Int = com.reza.systemdesign.R.drawable.ic_close

    init {
        continentImageMap[Continent.ASIA] = R.drawable.ic_asia
        continentImageMap[Continent.EUROPE] = R.drawable.ic_europe
        continentImageMap[Continent.AFRICA] = R.drawable.ic_africa
        continentImageMap[Continent.NORTH_AMERICA] = R.drawable.ic_north_america
        continentImageMap[Continent.SOUTH_AMERICA] = R.drawable.ic_south_america
        continentImageMap[Continent.OCEANIA] = R.drawable.ic_australia
        continentImageMap[Continent.ANTARCTICA] = R.drawable.ic_antarctica
    }

    override fun findContinentImage(name: String): Int {
        return continentImageMap[name] ?: defaultImageResId
    }

    fun setContinentImage(name: String, @Suppress("SameParameterValue") imageResId: Int) {
        continentImageMap[name] = imageResId
    }

    fun setDefaultImageResId(@Suppress("SameParameterValue") imageResId: Int) {
        this.defaultImageResId = imageResId
    }

    fun clearMappings() {
        continentImageMap.clear()
    }
}