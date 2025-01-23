package com.reza.details.domain.usecase

import com.reza.details.domain.model.Country
import com.reza.details.domain.repository.CountriesRepository
import javax.inject.Inject
import com.reza.common.domain.model.ResultState

internal class DefaultCountriesUseCase @Inject constructor(
    private val countriesRepository: CountriesRepository
) : CountriesUseCase {
    override suspend fun getCountries(code: String): ResultState<List<Country>> {
        return countriesRepository.getCountries(code)
    }
}