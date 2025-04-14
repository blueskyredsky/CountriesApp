package com.reza.details.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reza.common.R
import com.reza.common.domain.model.ResultState
import com.reza.common.util.stringresolver.StringResolver
import com.reza.details.domain.model.Country
import com.reza.details.domain.usecase.CountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val countriesUseCase: CountriesUseCase,
    private val stringResolver: StringResolver
) : ViewModel() {

    // todo list of countries should be kept in memory in a property

    private val _detailsUiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Empty)
    val detailsUiState = _detailsUiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _detailsUiState.update {
            DetailsUiState.Error(
                exception.message ?: stringResolver.findString(R.string.general_error_message)
            )
        }
    }

    private fun getCountries(
        isRefreshing: Boolean = false,
        continentCode: String
    ) {
        viewModelScope.launch(exceptionHandler) {
            if (_detailsUiState.value !is DetailsUiState.Success || isRefreshing) { // to avoid calling api again when navigating back to HomeScreen
                // Loading state
                if (isRefreshing) {
                    _detailsUiState.value = DetailsUiState.Refreshing
                } else {
                    _detailsUiState.value = DetailsUiState.Loading
                }

                // Getting continents
                when (val result = countriesUseCase.getCountries(continentCode)) {
                    is ResultState.Success -> {
                        setDetailsUiStateToSuccess(result = result)
                    }

                    is ResultState.Failure -> {
                        setDetailsUiStateToError(result)
                    }
                }
            }
        }
    }

    @VisibleForTesting
    fun setDetailsUiStateToSuccess(
        result: ResultState.Success<List<Country>>
    ) {
        _detailsUiState.update {
            DetailsUiState.Success(result.data)
        }
    }

    @VisibleForTesting
    fun setDetailsUiStateToError(result: ResultState.Failure) {
        _detailsUiState.update {
            DetailsUiState.Error(result.error)
        }
    }

    private fun consumeErrorMessage() {
        _detailsUiState.update {
            DetailsUiState.Error(null)
        }
    }

    fun onEvent(event: DetailsEvent) {
        when (event) {
            DetailsEvent.ConsumeErrorMessage -> {
                consumeErrorMessage()
            }

            is DetailsEvent.GetCountries -> {
                getCountries(isRefreshing = event.isRefreshing, continentCode = event.continentCode)
            }

            is DetailsEvent.Search -> {
                _searchQuery.value = event.query
            }
        }
    }
}