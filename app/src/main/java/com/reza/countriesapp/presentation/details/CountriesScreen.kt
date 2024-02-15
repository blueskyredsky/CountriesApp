package com.reza.countriesapp.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailsScreen(
    continentCode: String?,
    viewModel: CountriesViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val text by viewModel.filteredData.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.testTag("naghi"),
            text = text ?: "")
    }
}