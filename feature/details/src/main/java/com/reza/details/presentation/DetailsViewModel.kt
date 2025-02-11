package com.reza.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reza.common.util.navigation.CONTINENT_CODE
import com.reza.details.domain.usecase.CountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val countriesUseCase: CountriesUseCase
) : ViewModel() {

    private val _detailsUiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Empty)
    val detailsUiState = _detailsUiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _detailsUiState.update {
            DetailsUiState.Error(
                errorMessage = exception.message ?: "Something went wrong, please try again!"
            )
        }
    }

    private fun getCountries() {
        savedStateHandle.get<String>(CONTINENT_CODE)?.let { code ->
            viewModelScope.launch(exceptionHandler) {
                // Loading state
                /*_detailsState.update { state ->
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
                }*/
            }
        }
    }
}