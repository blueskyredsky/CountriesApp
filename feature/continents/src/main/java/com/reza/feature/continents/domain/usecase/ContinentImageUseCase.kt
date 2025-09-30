package com.reza.feature.continents.domain.usecase

/**
 * A use case interface for finding continent images.
 */
interface ContinentImageUseCase {
    /**
     * Finds the image resource ID for a continent based on its name.
     */
    suspend fun findContinentImage(name: String): Int
}