package com.reza.countriesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reza.countriesapp.di.MainDispatcher
import com.reza.countriesapp.domain.model.Continent
import com.reza.countriesapp.domain.usecase.ContinentsUseCase
import com.reza.countriesapp.domain.usecase.CountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class ContinentViewModel @Inject constructor(
    private val continentsUseCase: ContinentsUseCase,
    private val countriesUseCase: CountriesUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _continentsState = MutableStateFlow(ContinentsState())
    val continentsState = _continentsState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _continentsState.update { continentsState ->
            continentsState.copy(
                errorMessage = exception.message ?: "Something went wrong, please try again!"
            )
        }
    }

    init {
        viewModelScope.launch(mainDispatcher + exceptionHandler) {
            // Loading state
            _continentsState.update { continentsState ->
                continentsState.copy(
                    isLoading = true
                )
            }

            // Getting continents
            _continentsState.update { continentsState ->
                continentsState.copy(
                    continents = continentsUseCase.getContinents(),
                    isLoading = false
                )
            }
        }
    }

    fun selectContinent(continent: Continent) {
        viewModelScope.launch(mainDispatcher + exceptionHandler) {
            _continentsState.update { continentsState ->
                continentsState.copy(
                    selectedContinent = continent
                )
            }
        }
    }

    fun resetSelectedCountry() {
        viewModelScope.launch(mainDispatcher + exceptionHandler) {
            _continentsState.update { continentsState ->
                continentsState.copy(
                    selectedContinent = null
                )
            }
        }
    }

    @Immutable
    data class ContinentsState(
        val continents: List<Continent> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: String? = null,
        val selectedContinent: Continent? = null
    )
}