package com.weappsinc.screenpet.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weappsinc.screenpet.feature.home.domain.usecase.LoadShimejiCatalogUseCase
import com.weappsinc.screenpet.feature.home.domain.usecase.ObserveHomeStateUseCase
import com.weappsinc.screenpet.feature.home.domain.usecase.SeedDefaultUnlockedUseCase
import com.weappsinc.screenpet.feature.home.domain.usecase.SelectShimejiAtSlotUseCase
import com.weappsinc.screenpet.feature.home.domain.usecase.ToggleActivateUseCase
import com.weappsinc.screenpet.feature.home.domain.usecase.ToggleSwarmUseCase
import com.weappsinc.screenpet.feature.home.domain.usecase.UnlockNextSlotUseCase
import com.weappsinc.screenpet.feature.home.domain.usecase.UnlockShimejiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadCatalog: LoadShimejiCatalogUseCase,
    private val observeHomeState: ObserveHomeStateUseCase,
    private val seedDefaultUnlocked: SeedDefaultUnlockedUseCase,
    private val selectSlot: SelectShimejiAtSlotUseCase,
    private val unlockUseCase: UnlockShimejiUseCase,
    private val unlockNextSlotUseCase: UnlockNextSlotUseCase,
    private val toggleActivate: ToggleActivateUseCase,
    private val toggleSwarm: ToggleSwarmUseCase,
    private val overlayController: PetOverlayController,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            seedDefaultUnlocked()
            val catalog = loadCatalog().getOrDefault(emptyList())
            _uiState.update { it.copy(catalog = catalog, isLoading = false) }
            observeHomeState().collect { state ->
                _uiState.update {
                    it.copy(
                        settings = state.settings,
                        unlockedIds = state.unlockedIds,
                        downloadedIds = state.downloadedIds,
                    )
                }
                overlayController.apply(state.settings, state.unlockedIds intersect state.downloadedIds, catalog)
            }
        }
    }

    fun onActivateChanged(enabled: Boolean) {
        viewModelScope.launch { toggleActivate(enabled) }
    }

    fun onSwarmChanged(enabled: Boolean) {
        viewModelScope.launch { toggleSwarm(enabled) }
    }

    fun onSlotTapped(slot: Int) {
        val slotModel = _uiState.value.slots.getOrNull(slot) ?: return
        if (slotModel is HomeSlotUiModel.SlotLocked) {
            viewModelScope.launch { unlockNextSlotUseCase() }
            return
        }
        if (slotModel is HomeSlotUiModel.CharacterLocked) return
        _uiState.update { it.copy(pickerOpenForSlot = slot) }
    }

    fun onPickerDismiss() {
        _uiState.update { it.copy(pickerOpenForSlot = null) }
    }

    fun onPickerSelect(slot: Int, characterId: String?) {
        viewModelScope.launch {
            selectSlot(slot, characterId)
            _uiState.update { it.copy(pickerOpenForSlot = null) }
        }
    }

    fun onUnlock(characterId: String) {
        viewModelScope.launch { unlockUseCase(characterId) }
    }
}
