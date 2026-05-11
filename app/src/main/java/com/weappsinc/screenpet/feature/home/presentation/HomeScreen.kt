package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weappsinc.screenpet.R
import com.weappsinc.screenpet.ui.theme.HomeTokens

@Composable
fun HomeScreen(
    state: HomeUiState,
    onActivateChanged: (Boolean) -> Unit,
    onSwarmChanged: (Boolean) -> Unit,
    onSlotClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize(), color = HomeTokens.Background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            HomeTitleText(text = stringResource(R.string.home_title))
            HomeActivateCard(
                enabled = state.settings.activateEnabled,
                onChanged = onActivateChanged,
            )
            HomeSwarmCard(
                enabled = state.settings.swarmEnabled,
                onChanged = onSwarmChanged,
            )
            HomeBannerCard()
            HomeMixHeader()
            HomeShimejiGrid(slots = state.slots, onSlotClick = onSlotClick)
        }
    }
}
