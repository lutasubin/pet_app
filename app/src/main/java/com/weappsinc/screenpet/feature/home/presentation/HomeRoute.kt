package com.weappsinc.screenpet.feature.home.presentation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    pendingShopCharacterId: String? = null,
    onConsumedPendingShopSelection: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val activity = LocalContext.current as ComponentActivity
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LaunchedEffect(pendingShopCharacterId) {
        val id = pendingShopCharacterId ?: return@LaunchedEffect
        viewModel.onShopPetSelected(id)
        onConsumedPendingShopSelection()
    }
    val onActivate = rememberHomeActivatePermissionHandler(
        activity = activity,
        onActivateEnabled = { viewModel.onActivateChanged(true) },
        onActivateDisabled = { viewModel.onActivateChanged(false) },
    )
    HomeScreen(
        state = state,
        onActivateChanged = onActivate,
        onSwarmChanged = viewModel::onSwarmChanged,
        onMixRandomCountSelected = viewModel::onMixRandomCountSelected,
        onSlotClick = viewModel::onSlotTapped,
        onSlotRemove = viewModel::onSlotRemove,
        modifier = modifier,
    )
    val pickerSlot = state.pickerOpenForSlot
    if (pickerSlot != null) {
        HomeCharacterPickerSheet(
            slot = pickerSlot,
            catalog = state.catalog.filter { it.id in state.readyIds },
            unlockedIds = state.readyIds,
            onDismiss = viewModel::onPickerDismiss,
            onSelect = viewModel::onPickerSelect,
            onUnlock = viewModel::onUnlock,
        )
    }
}
