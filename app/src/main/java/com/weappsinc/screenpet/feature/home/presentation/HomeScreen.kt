package com.weappsinc.screenpet.feature.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    state: HomeUiState,
    onActivateChanged: (Boolean) -> Unit,
    onSwarmChanged: (Boolean) -> Unit,
    onSlotClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        HomeScreenBackground(Modifier.matchParentSize())
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            HomeTitleLogo()
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
