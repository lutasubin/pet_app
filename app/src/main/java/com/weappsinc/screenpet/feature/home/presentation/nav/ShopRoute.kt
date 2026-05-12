package com.weappsinc.screenpet.feature.home.presentation.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.weappsinc.screenpet.feature.pet.presentation.PetViewModel

@Composable
fun ShopRoute(
    modifier: Modifier = Modifier,
    viewModel: ShopViewModel = hiltViewModel(),
    petViewModel: PetViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var previewCharacterId by remember { mutableStateOf<String?>(null) }
    val previewCharacter = state.catalog.firstOrNull { it.id == previewCharacterId }
    ShopScreen(
        state = state,
        onQueryChange = viewModel::onQueryChange,
        onUnlock = viewModel::onUnlock,
        onDownload = viewModel::onDownload,
        onSelect = { previewCharacterId = it },
        modifier = modifier,
    )
    if (previewCharacter != null) {
        ShopPetPreviewScreen(
            petViewModel = petViewModel,
            onBack = { previewCharacterId = null },
        )
    }
}
