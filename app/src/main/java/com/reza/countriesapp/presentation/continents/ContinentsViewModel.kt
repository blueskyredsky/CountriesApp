package com.reza.countriesapp.presentation.continents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reza.countriesapp.data.di.MainDispatcher
import com.reza.countriesapp.domain.model.ResultState
import com.reza.countriesapp.domain.usecase.ContinentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContinentsViewModel @Inject constructor(
    private val continentsUseCase: ContinentsUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _continentsState = MutableStateFlow(ContinentsState())
    val continentsState = _continentsState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _continentsState.update { state ->
            state.copy(
                errorMessage = exception.message ?: "Something went wrong, please try again!"
            )
        }
    }

    init {
        getContinents()
    }

    private fun getContinents() {
        viewModelScope.launch(mainDispatcher + exceptionHandler) {
            // Loading state
            _continentsState.update { state ->
                state.copy(
                    isLoading = true
                )
            }

            // Getting continents
            when (val result = continentsUseCase.getContinents()) {
                is ResultState.Success -> {
                    _continentsState.update { state ->
                        state.copy(
                            continents = result.data, isLoading = false, errorMessage = null
                        )
                    }
                }

                is ResultState.Failure -> {
                    _continentsState.update { state ->
                        state.copy(
                            isLoading = false, errorMessage = result.error
                        )
                    }
                }
            }
        }
    }

    fun consumeErrorMessage() {
        _continentsState.update { state ->
            state.copy(errorMessage = null)
        }
    }

    fun onEvent(event: ContinentsEvent) {
        when (event) {
            is ContinentsEvent.RequestContinents -> getContinents()
        }
    }
}