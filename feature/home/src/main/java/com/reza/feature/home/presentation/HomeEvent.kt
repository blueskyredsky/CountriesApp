package com.reza.feature.home.presentation

sealed interface HomeEvent {
    data class GetContinents(val isRefreshing: Boolean = false) : HomeEvent
}