package com.reza.details.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.reza.details.presentation.DetailsViewModel

private const val DETAILS = "details"

fun NavGraphBuilder.details() {
    composable(DETAILS) { backStackEntry ->
        val viewModel = hiltViewModel<DetailsViewModel>(backStackEntry)
//        DetailsScreen(
//            viewModel = viewModel,
//            onSelectContinent = onSelectContinent
//        )
    }
}