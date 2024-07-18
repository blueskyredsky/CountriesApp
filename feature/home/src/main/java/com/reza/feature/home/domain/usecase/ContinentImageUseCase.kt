package com.reza.feature.home.domain.usecase

internal interface ContinentImageUseCase {
    fun findContinentImage(name: String): Int
}