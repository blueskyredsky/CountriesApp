package com.reza.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reza.common.R
import com.reza.common.domain.model.ResultState
import com.reza.common.util.stringresolver.StringResolver
import com.reza.details.domain.usecase.CountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val countriesUseCase: CountriesUseCase,
    private val stringResolver: StringResolver
) : ViewModel() {

    private val refreshTrigger = MutableStateFlow(Unit)

    private val continentCode = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val detailsUiState = combine(
        continentCode,
        refreshTrigger
    ) { countryCode, _ ->
        countryCode
    }.flatMapLatest { countryCode ->
        if (countryCode == null) {
            flowOf(DetailsUiState.Error(stringResolver.findString(R.string.general_error_message)))
        } else {
            val result = when (val result = countriesUseCase.getCountries(countryCode)) {
                is ResultState.Success -> {
                    DetailsUiState.Success(result.data)
                }

                is ResultState.Failure -> {
                    DetailsUiState.Error(result.error)
                }
            }
            flowOf(result)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DetailsUiState.Loading,
    )

    fun onEvent(event: DetailsEvent) {
        when (val value = event) {
            DetailsEvent.ConsumeErrorMessage -> {

            }

            is DetailsEvent.GetCountries -> {
                continentCode.value = value.continentCode
            }

            DetailsEvent.Refresh -> {
                refreshTrigger.value = Unit
            }
        }
    }
}