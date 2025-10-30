package com.reza.feature.continents.presentation.components

import android.content.res.Configuration
import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.reza.countriesapp.ui.theme.CountriesAppTheme
import com.reza.feature.continents.R
import com.reza.feature.continents.domain.model.Continent
import com.reza.feature.continents.presentation.ContinentView
import com.reza.systemdesign.ui.util.UiTags

@Composable
internal fun ContinentList(
    continents: List<ContinentView>,
    modifier: Modifier = Modifier,
    onSelectContinent: (Continent) -> Unit,
) {
    ReportDrawnWhen { continents.isNotEmpty() }

    LazyColumn(
        modifier = modifier
            .testTag("continent_list")
            .semantics { contentDescription = "continent_list" }
            .fillMaxSize()
    ) {
        items(
            key = { item -> item.continent.code },
            items = continents
        ) { continentView ->
            ContinentItem(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                continentView = continentView,
                onSelectContinent = onSelectContinent
            )
        }
    }
}

@Preview(name = "Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewContinentsList() {
    CountriesAppTheme {
        ContinentList(
            continents = (1..5).map { index ->
                ContinentView(
                    continent = Continent(code = index.toString(), name = "Continent $index"),
                    imageResource = R.drawable.ic_south_america
                )
            },
            onSelectContinent = {}
        )
    }
}
