package com.reza.countriesapp.domain.usecase.continents

import com.reza.countriesapp.R

interface ContinentImageUseCase {
    fun findContinentImage(name: String): Int
}