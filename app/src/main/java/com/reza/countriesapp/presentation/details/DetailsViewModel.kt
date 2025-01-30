package com.reza.countriesapp.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reza.countriesapp.domain.model.ResultState
import com.reza.countriesapp.domain.usecase.countries.CountriesUseCase
import com.reza.countriesapp.presentation.navigation.CONTINENT_CODE
import com.reza.feature.home.presentation.HomeEvent
import com.reza.threading.common.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val countriesUseCase: CountriesUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _detailsState = MutableStateFlow(DetailsState())
    val countriesState = _detailsState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _detailsState.update { continentsState ->
            continentsState.copy(
                errorMessage = exception.message ?: "Something went wrong, please try again!"
            )
        }
    }

    init {
        getCountries()
    }

//    fun onEvent(event: HomeEvent) {
//        when (event) {
//            is HomeEvent.GetContinents -> getCountries()
//        }
//    }

    private fun getCountries() {
        savedStateHandle.get<String>(CONTINENT_CODE)?.let { code ->
            viewModelScope.launch(mainDispatcher + exceptionHandler) {
                // Loading state
                _detailsState.update { state ->
                    state.copy(
                        isLoading = true
                    )
                }

                when (val result = countriesUseCase.getCountries(code)) {
                    is ResultState.Success -> {
                        _detailsState.update { state ->
                            state.copy(
                                countries = result.data,
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }

                    is ResultState.Failure -> {
                        _detailsState.update { state ->
                            state.copy(
                                countries = null,
                                isLoading = false,
                                errorMessage = result.error
                            )
                        }
                    }
                }
            }
        }
    }

    fun consumeErrorMessage() {
        _detailsState.update { state ->
            state.copy(errorMessage = null)
        }
    }
}