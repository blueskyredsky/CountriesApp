package com.reza.details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.reza.details.domain.usecase.CountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val countriesUseCase: CountriesUseCase
) : ViewModel() {

}