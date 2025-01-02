package com.reza.feature.home.presentation

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reza.common.domain.model.ResultState
import com.reza.feature.home.domain.model.Continent
import com.reza.feature.home.domain.usecase.ContinentImageUseCase
import com.reza.feature.home.domain.usecase.ContinentsUseCase
import com.reza.threading.common.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val continentsUseCase: ContinentsUseCase,
    private val continentsImageUseCase: ContinentImageUseCase
) : ViewModel() {

    private val _homeState = MutableStateFlow<HomeUiState>(HomeUiState.Empty)
    val homeState = _homeState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _homeState.update {
            HomeUiState.Error(exception.message ?: "Something went wrong, please try again!")
        }
    }

    private fun getContinents() {
        viewModelScope.launch(exceptionHandler) {
            // Loading state
            _homeState.update {
                HomeUiState.Loading
            }

            // Getting continents
            when (val result = continentsUseCase.getContinents()) {
                is ResultState.Success -> {
                    _homeState.update {
                        HomeUiState.Success(result.data.transformToContinentViews {
                            continentsImageUseCase.findContinentImage(it)
                        })
                    }
                }

                is ResultState.Failure -> {
                    _homeState.update {
                        HomeUiState.Error(result.error)
                    }
                }
            }
        }
    }

    fun consumeErrorMessage() {
//        _homeState.update { state ->
//            state.copy(errorMessage = null)
//        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetContinents -> getContinents()
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
fun List<Continent>.transformToContinentViews(imageResource: (String) -> Int): List<ContinentView> =
    this.map { continent ->
        ContinentView(
            continent = continent,
            imageResource = imageResource(continent.name)
        )
    }