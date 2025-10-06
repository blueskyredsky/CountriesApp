package com.reza.feature.continents.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reza.common.R
import com.reza.common.domain.model.ResultState
import com.reza.common.util.stringresolver.StringResolver
import com.reza.feature.continents.domain.usecase.ContinentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContinentsViewModel @Inject constructor(
    private val continentsUseCase: ContinentsUseCase,
    private val stringResolver: StringResolver,
) : ViewModel() {

    private val _continentsUiState = MutableStateFlow<ContinentsUiState>(ContinentsUiState.Empty)
    val continentsUiState = _continentsUiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _continentsUiState.update {
            ContinentsUiState.Error(
                exception.message ?: stringResolver.findString(R.string.general_error_message)
            )
        }
    }

    private fun getContinents(isRefreshing: Boolean = false) {
        viewModelScope.launch(exceptionHandler) {
            if (_continentsUiState.value !is ContinentsUiState.Success || isRefreshing) { // to avoid calling api again when navigating back to ContinentsScreen
                // Loading state
                if (isRefreshing) {
                    _continentsUiState.value = ContinentsUiState.Refreshing
                } else {
                    _continentsUiState.value = ContinentsUiState.Loading
                }

                // Getting continents
                when (val result = continentsUseCase.getContinents()) {
                    is ResultState.Success -> {
                        setContinentsUiStateToSuccess(result)
                    }

                    is ResultState.Failure -> {
                        setContinentsUiStateToError(result)
                    }
                }
            }
        }
    }

    @VisibleForTesting
    fun setContinentsUiStateToSuccess(result: ResultState.Success<List<ContinentView>>) {
        _continentsUiState.update {
            ContinentsUiState.Success(result.data)
        }
    }

    @VisibleForTesting
    fun setContinentsUiStateToError(result: ResultState.Failure) {
        _continentsUiState.update {
            ContinentsUiState.Error(result.error)
        }
    }

    private fun consumeErrorMessage() {
        _continentsUiState.update {
            ContinentsUiState.Error(null)
        }
    }

    fun onEvent(event: ContinentsEvent) {
        when (event) {
            is ContinentsEvent.GetContinents -> getContinents(event.isRefreshing)
            ContinentsEvent.ConsumeErrorMessage -> consumeErrorMessage()
        }
    }
}