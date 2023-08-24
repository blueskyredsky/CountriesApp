package com.reza.countriesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reza.countriesapp.di.MainDispatcher
import com.reza.countriesapp.domain.entity.ContinentEntity
import com.reza.countriesapp.domain.usecase.ContinentDetailsUseCase
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
class ContinentViewModel @Inject constructor(
    private val continentsUseCase: ContinentsUseCase,
    private val continentDetailsUseCase: ContinentDetailsUseCase,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _continentsState = MutableStateFlow(ContinentsState())
    val continentsState = _continentsState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _continentsState.update { continentsState ->
            continentsState.copy(
                continents = emptyList(),
                isLoading = false,
                errorMessage = exception.message ?: "Something went wrong, please try again!"
            )
        }
    }

    init {
        viewModelScope.launch(mainDispatcher) {

        }
    }


    data class ContinentsState(
        val continents: List<ContinentEntity> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )
}