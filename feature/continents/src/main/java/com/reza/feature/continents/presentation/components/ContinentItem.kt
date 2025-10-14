package com.reza.feature.continents.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.reza.countriesapp.ui.theme.CountriesAppTheme
import com.reza.feature.continents.R
import com.reza.feature.continents.domain.model.Continent
import com.reza.feature.continents.presentation.ContinentView
import com.reza.systemdesign.ui.util.UiTags

@Composable
internal fun ContinentItem(
    modifier: Modifier = Modifier,
    continentView: ContinentView,
    onSelectContinent: (Continent) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
            .clickable { onSelectContinent(continentView.continent) }
            .testTag(UiTags.ContinentScreen.CONTINENT_ITEM)
            .clip(MaterialTheme.shapes.small),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp), text = continentView.continent.name
            )
            Image(
                modifier = Modifier.padding(16.dp),
                painter = painterResource(id = continentView.imageResource),
                contentDescription = null
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
private fun ContinentItemPreview() {
    CountriesAppTheme {
        ContinentItem(
            modifier = Modifier.fillMaxWidth(),
            continentView = ContinentView(
                continent = Continent(
                    name = "name",
                    code = "code"
                ),
                imageResource = R.drawable.ic_africa
            ),
            onSelectContinent = {}
        )
    }
}