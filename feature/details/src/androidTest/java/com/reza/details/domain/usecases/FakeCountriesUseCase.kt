package com.reza.details.domain.usecases

import com.reza.common.domain.model.ResultState
import com.reza.details.domain.model.Country
import com.reza.details.domain.usecase.CountriesUseCase
import javax.inject.Inject

class FakeCountriesUseCase @Inject constructor() : CountriesUseCase {

    private val fakeCountries = listOf(
        Country(
            name = "Canada",
            emoji = "\uD83C\uDDE8\uD83C\uDDE6",
            currency = "CAD",
            capital = "Ottawa",
            phone = "1",
            states = listOf("Alberta", "British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador", "Northwest Territories", "Nova Scotia", "Nunavut", "Ontario", "Prince Edward Island", "Quebec", "Saskatchewan", "Yukon"),
            languages = listOf("English", "French")
        ),
        Country(
            name = "Antigua and Barbuda",
            emoji = "\uD83C\uDDE6\uD83C\uDDEC",
            currency = "XCD",
            capital = "Saint John's",
            phone = "1268",
            states = emptyList(),
            languages = listOf("English")
        ),
    )

    var shouldReturnError = false

    override suspend fun getCountries(code: String): ResultState<List<Country>> {
        return if (shouldReturnError) {
            ResultState.Failure("Test Error: Could not retrieve countries.")
        } else {
            ResultState.Success(fakeCountries)
        }
    }
}