package com.reza.feature.continents.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reza.common.domain.model.ResultState
import com.reza.common.util.stringresolver.StringResolver
import com.reza.common.R.string
import com.reza.feature.continents.domain.model.Continent
import com.reza.feature.continents.domain.usecase.ContinentImageUseCase
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
    private val continentsImageUseCase: ContinentImageUseCase,
    private val stringResolver: StringResolver,
) : ViewModel() {

    private val _continentsUiState = MutableStateFlow<ContinentsUiState>(ContinentsUiState.Empty)
    val continentsUiState = _continentsUiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _continentsUiState.update {
            ContinentsUiState.Error(
                exception.message ?: stringResolver.findString(string.general_error_message)
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
    suspend fun setContinentsUiStateToSuccess(result: ResultState.Success<List<Continent>>) {
        _continentsUiState.update {
            ContinentsUiState.Success(result.data.transformToContinentViews {
                continentsImageUseCase.findContinentImage(it)
            })
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

/**
 * Transforms a list of [Continent] objects into a list of [ContinentView] objects.
 *
 * This extension function iterates through the list of continents and creates a corresponding
 * [ContinentView] for each continent. It uses the provided `imageResource` function to
 * retrieve the image resource ID for each continent based on its name.
 *
 * @param imageResource A function that takes a continent name as input and returns the
 * corresponding image resource ID.
 * @return A list of [ContinentView] objects.
 */
suspend fun List<Continent>.transformToContinentViews(imageResource: suspend (String) -> Int): List<ContinentView> =
    this.map { continent ->
        ContinentView(
            continent = continent,
            imageResource = imageResource(continent.name)
        )
    }