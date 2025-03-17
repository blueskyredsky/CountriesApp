package com.reza.details.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.reza.details.presentation.DetailsScreen
import com.reza.details.presentation.DetailsViewModel
import kotlinx.serialization.Serializable

@Serializable
data class DetailsRoute(val contentId: String? = null) // route to details screen

fun NavGraphBuilder.detailsSection(onBackClick: () -> Unit) {
    composable<DetailsRoute> { backStackEntry ->
        val viewModel = hiltViewModel<DetailsViewModel>(backStackEntry)
        DetailsScreen(
            viewModel = viewModel,
            onBackClick = onBackClick
        )
    }
}