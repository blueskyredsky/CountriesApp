package com.reza.countriesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.reza.countriesapp.presentation.home.ContinentsScreen
import com.reza.countriesapp.presentation.navigation.AppNavGraph
import com.reza.countriesapp.ui.theme.CountriesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountriesAppTheme {
                val viewModel = hiltViewModel<ContinentViewModel>()
                val state by viewModel.continentsState.collectAsState()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContinentsScreen(
                        state = state,
                        onSelectContinent = { continent ->
                            viewModel.selectContinent(continent)
                        }
                    )
//                    val navController = rememberNavController()
//                    MainScreen(navController = navController)
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    navController: NavHostController
) {
    Scaffold(
        content = { innerPaddingModifier ->
            AppNavGraph(
                modifier = Modifier.padding(innerPaddingModifier),
                navController = navController
            )
        }
    )
}