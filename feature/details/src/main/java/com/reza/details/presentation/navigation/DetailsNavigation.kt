package com.reza.details.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.reza.common.util.navigation.NavigationRoute
import com.reza.details.presentation.DetailsScreen
import com.reza.details.presentation.DetailsViewModel

fun NavGraphBuilder.details(onBackClick: () -> Unit) {
    composable(NavigationRoute.DETAIL.route) { backStackEntry ->
        val viewModel = hiltViewModel<DetailsViewModel>(backStackEntry)
        DetailsScreen(
            viewModel = viewModel,
            onBackClick = onBackClick
        )
    }
}