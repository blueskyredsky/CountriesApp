package com.reza.countriesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.reza.common.util.navigation.CONTINENT_CODE
import com.reza.common.util.navigation.NavigationRoute
import com.reza.details.presentation.navigation.details
import com.reza.feature.home.presentation.navigation.HomeBaseRoute
import com.reza.feature.home.presentation.navigation.homeSection

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeBaseRoute
    ) {
        homeSection { continent ->
            val route = NavigationRoute.DETAIL.route.replace(CONTINENT_CODE, continent.code)
            navController.navigate(route = route)
        }

        details {
            navController.navigateUp()
        }
    }
}