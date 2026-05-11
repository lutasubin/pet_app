package com.weappsinc.screenpet.feature.home.presentation

import com.weappsinc.screenpet.feature.home.domain.model.HomeSettings
import com.weappsinc.screenpet.feature.home.domain.model.ShimejiCharacter

/** Trang thai bat bien cho HomeScreen. */
data class HomeUiState(
    val isLoading: Boolean = true,
    val settings: HomeSettings = HomeSettings(),
    val catalog: List<ShimejiCharacter> = emptyList(),
    val unlockedIds: Set<String> = emptySet(),
    val downloadedIds: Set<String> = emptySet(),
    val pickerOpenForSlot: Int? = null,
) {
    val readyIds: Set<String>
        get() = unlockedIds intersect downloadedIds

    val slots: List<HomeSlotUiModel>
        get() {
            val byId = catalog.associateBy { it.id }
            return settings.selectedSlotIds.mapIndexed { index, id ->
                if (index >= settings.unlockedSlotCount) {
                    return@mapIndexed HomeSlotUiModel.SlotLocked(index)
                }
                when {
                    id == null -> HomeSlotUiModel.Empty
                    id !in readyIds -> {
                        val ch = byId[id]
                        if (ch == null) HomeSlotUiModel.CharacterLocked(id, null)
                        else HomeSlotUiModel.CharacterLocked(id, ch.thumbnailAssetPath)
                    }
                    else -> {
                        val ch = byId[id] ?: return@mapIndexed HomeSlotUiModel.Empty
                        HomeSlotUiModel.Picked(id, ch.displayName, ch.thumbnailAssetPath)
                    }
                }
            }
        }
}

sealed class HomeSlotUiModel {
    data object Empty : HomeSlotUiModel()
    data class Picked(val characterId: String, val name: String, val thumbnailAssetPath: String) : HomeSlotUiModel()
    data class CharacterLocked(val characterId: String, val thumbnailAssetPath: String?) : HomeSlotUiModel()
    data class SlotLocked(val slot: Int) : HomeSlotUiModel()
}
