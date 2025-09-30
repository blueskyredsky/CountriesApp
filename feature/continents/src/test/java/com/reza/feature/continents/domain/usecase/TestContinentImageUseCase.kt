package com.reza.feature.continents.domain.usecase

class TestContinentImageUseCase : ContinentImageUseCase {
    override suspend fun findContinentImage(name: String): Int {
        return -1
    }
}