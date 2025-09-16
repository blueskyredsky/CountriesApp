package com.reza.countries.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reza.common.R
import com.reza.common.domain.model.ResultState
import com.reza.common.util.stringresolver.StringResolver
import com.reza.countries.domain.model.Country
import com.reza.countries.domain.usecase.CountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CountriesViewModel @Inject constructor(
    private val countriesUseCase: CountriesUseCase,
    private val stringResolver: StringResolver
) : ViewModel() {

    private val _countriesUiState = MutableStateFlow<CountriesUiState>(CountriesUiState.Empty)
    val countriesUiState = _countriesUiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _countriesUiState.update {
            CountriesUiState.Error(
                exception.message ?: stringResolver.findString(R.string.general_error_message)
            )
        }
    }

    val filteredCountries = combine(countriesUiState, searchQuery) { uiState, query ->
        if (uiState is CountriesUiState.Success) {
            filterCountries(uiState.countries, query)
        } else {
            emptyList()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    private fun filterCountries(countries: List<Country>, query: String): List<Country> {
        return countries.filter { country ->
            country.name.lowercase().contains(query.lowercase()) ||
                    country.capital.lowercase().contains(query.lowercase())
        }
    }

    private fun getCountries(
        isRefreshing: Boolean = false,
        continentCode: String
    ) {
        viewModelScope.launch(exceptionHandler) {
            if (_countriesUiState.value !is CountriesUiState.Success || isRefreshing) { // to avoid calling api again when navigating back to CountriesScreen
                // Loading state
                if (isRefreshing) {
                    _countriesUiState.value = CountriesUiState.Refreshing
                } else {
                    _countriesUiState.value = CountriesUiState.Loading
                }

                // Getting continents
                when (val result = countriesUseCase.getCountries(continentCode)) {
                    is ResultState.Success -> {
                        setCountriesUiStateToSuccess(result = result)
                    }

                    is ResultState.Failure -> {
                        setCountriesUiStateToError(result)
                    }
                }
            }
        }
    }

    @VisibleForTesting
    fun setCountriesUiStateToSuccess(
        result: ResultState.Success<List<Country>>
    ) {
        _countriesUiState.update {
            CountriesUiState.Success(result.data)
        }
    }

    @VisibleForTesting
    fun setCountriesUiStateToError(result: ResultState.Failure) {
        _countriesUiState.update {
            CountriesUiState.Error(result.error)
        }
    }

    private fun consumeErrorMessage() {
        _countriesUiState.update {
            CountriesUiState.Error(null)
        }
    }

    fun onEvent(event: CountriesEvent) {
        when (event) {
            CountriesEvent.ConsumeErrorMessage -> {
                consumeErrorMessage()
            }

            is CountriesEvent.GetCountries -> {
                getCountries(isRefreshing = event.isRefreshing, continentCode = event.continentCode)
            }

            is CountriesEvent.Search -> {
                _searchQuery.value = event.query
            }
        }
    }
}