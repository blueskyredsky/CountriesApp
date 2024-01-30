package com.reza.countriesapp.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.reza.countriesapp.domain.usecase.CountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: CountriesUseCase
) : ViewModel() {

    val filteredData: StateFlow<String> =
        savedStateHandle.getStateFlow<String>("continentCode", "")



    // TODO: getting args from saveStateHandle
}