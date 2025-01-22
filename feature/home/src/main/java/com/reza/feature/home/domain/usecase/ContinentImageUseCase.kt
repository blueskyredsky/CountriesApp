package com.reza.feature.home.domain.usecase

/**
 * An internal use case interface for finding continent images.
 */
internal interface ContinentImageUseCase {
    /**
     * Finds the image resource ID for a continent based on its name.
     */
    fun findContinentImage(name: String): Int
}