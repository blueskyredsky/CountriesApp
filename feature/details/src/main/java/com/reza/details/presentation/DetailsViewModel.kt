package com.reza.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.reza.details.domain.usecase.CountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
}