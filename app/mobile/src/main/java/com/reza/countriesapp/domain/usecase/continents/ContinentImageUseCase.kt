package com.reza.countriesapp.domain.usecase.continents

interface ContinentImageUseCase {
    fun findContinentImage(name: String): Int
}