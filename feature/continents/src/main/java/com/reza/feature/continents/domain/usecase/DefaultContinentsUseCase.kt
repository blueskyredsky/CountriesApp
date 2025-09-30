package com.reza.feature.continents.domain.usecase

import com.reza.common.domain.model.ResultState
import com.reza.feature.continents.domain.model.Continent
import com.reza.feature.continents.domain.repository.ContinentRepository
import com.reza.threading.common.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultContinentsUseCase @Inject constructor(
    private val continentRepository: ContinentRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ContinentsUseCase {
    override suspend fun getContinents(): ResultState<List<Continent>> = withContext(ioDispatcher) {
        continentRepository.getContinents()
    }
}