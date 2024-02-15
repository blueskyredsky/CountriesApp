package com.reza.countriesapp.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reza.countriesapp.data.di.MainDispatcher
import com.reza.countriesapp.domain.usecase.CountriesUseCase
import com.reza.countriesapp.presentation.navigation.CONTINENT_CODE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: CountriesUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _countriesState = MutableStateFlow(CountriesState())
    val countriesState = _countriesState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _countriesState.update { continentsState ->
            continentsState.copy(
                errorMessage = exception.message ?: "Something went wrong, please try again!"
            )
        }
    }

    val filteredData: StateFlow<String> =
        savedStateHandle.getStateFlow<String>("continentCode", "")

    init {
        // TODO: calling api
        savedStateHandle.get<String>(CONTINENT_CODE)?.let { code ->
            viewModelScope.launch(mainDispatcher + exceptionHandler) {

            }
        }


    }



    // TODO: getting args from saveStateHandle
}