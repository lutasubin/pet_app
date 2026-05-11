package com.weappsinc.screenpet.feature.home.presentation.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ShopRoute(
    modifier: Modifier = Modifier,
    viewModel: ShopViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    ShopScreen(
        state = state,
        onQueryChange = viewModel::onQueryChange,
        onUnlock = viewModel::onUnlock,
        onDownload = viewModel::onDownload,
        modifier = modifier,
    )
}
